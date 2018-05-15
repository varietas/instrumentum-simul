/*
 * The MIT License (MIT)
 * Copyright (c) 2015, Hindol Adhya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.varietas.instrumentum.simul.io;

import io.varietas.instrumentum.simul.io.container.FolderInformation;
import io.varietas.instrumentum.simul.io.listener.OnFileChangeListener;
import io.varietas.instrumentum.simul.service.AbstractService;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>SimpleDirectoryWatchService</h2>
 * <p>
 * A simple class which can monitor files and notify interested parties (i.e. listeners) of file changes.</p>
 * <p>
 * This class is kept lean by only keeping methods that are actually being called.</p>
 * <p>
 * <u>Usage</u>
 * <pre>
 * <code>DirectoryWatchService watchService = new SimpleDirectoryWatchService(); // May throw
 *  watchService.register( // May throw
 *      new DirectoryWatchService.OnFileChangeListener() {
 *          {@literal @}Override
 *          public void onFileCreate(String filePath) {
 *              LOGGER.debug("File " + filePath + " created.");
 *          }
 *
 *          {@literal @}Override
 *          public void onFileModify(String filePath) {
 *              LOGGER.debug("File " + filePath + " modified.");
 *          }
 *
 *          {@literal @}Override
 *          public void onFileDelete(String filePath) {
 *              LOGGER.debug("File " + filePath + " deleted.");
 *          }
 *      },
 *      "c:\\plugins",
 *      new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE}
 *  );
 *
 *  watchService.start();
 * </code>
 * </pre>
 *
 * @author Hindol Adhya
 * @author Michael Rhöse
 * @version 1.0.0, 9/4/2015
 */
@Slf4j
public class SimpleDirectoryWatchService extends AbstractService implements DirectoryWatchService {

    private final WatchService watchService;
    private final ConcurrentMap<WatchKey, Path> watchKeyToDirPathMap;
    private final ConcurrentMap<Path, Set<OnFileChangeListener>> dirPathToListenersMap;
    private final ConcurrentMap<OnFileChangeListener, Set<PathMatcher>> listenerToFilePatternsMap;

    /**
     * A simple no argument constructor for creating a <code>SimpleDirectoryWatchService</code>.
     *
     * @param scheduledExecutorService
     *
     * @throws IOException If an I/O error occurs.
     */
    public SimpleDirectoryWatchService(final ScheduledExecutorService scheduledExecutorService) throws IOException {
        super(scheduledExecutorService);
        this.watchService = FileSystems.getDefault().newWatchService();
        this.watchKeyToDirPathMap = newConcurrentMap();
        this.dirPathToListenersMap = newConcurrentMap();
        this.listenerToFilePatternsMap = newConcurrentMap();
    }

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    private static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<>();
    }

    private static <T> Set<T> newConcurrentSet() {
        return Collections.newSetFromMap(newConcurrentMap());
    }

    public static PathMatcher matcherForGlobExpression(final String globPattern) {
        return FileSystems.getDefault().getPathMatcher("glob:" + globPattern);
    }

    public static boolean matches(final Path input, final PathMatcher pattern) {
        return pattern.matches(input);
    }

    public static boolean matchesAny(final Path input, final Set<PathMatcher> patterns) {
        return patterns.stream().anyMatch((pattern) -> (matches(input, pattern)));
    }

    private Path getDirPath(final WatchKey key) {
        return watchKeyToDirPathMap.get(key);
    }

    private Set<OnFileChangeListener> getListeners(final Path dir) {
        return dirPathToListenersMap.get(dir);
    }

    private Set<PathMatcher> getPatterns(final OnFileChangeListener listener) {
        return listenerToFilePatternsMap.get(listener);
    }

    private Set<OnFileChangeListener> matchedListeners(final Path dir, final Path file) {
        return getListeners(dir)
                .stream()
                .filter(listener -> matchesAny(file, getPatterns(listener)))
                .collect(Collectors.toSet());
    }

    private void notifyListeners(final WatchKey key) {
        for (WatchEvent<?> event : key.pollEvents()) {
            WatchEvent.Kind eventKind = event.kind();

            // Overflow occurs when the watch event queue is overflown
            // with events.
            if (eventKind.equals(StandardWatchEventKinds.OVERFLOW)) {
                // TODO: Notify all listeners.
                return;
            }

            WatchEvent<Path> pathEvent = cast(event);
            Path file = pathEvent.context();

            if (eventKind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                matchedListeners(getDirPath(key), file).forEach(listener -> listener.onFileCreate(file.toString()));
                continue;
            }
            if (eventKind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                matchedListeners(getDirPath(key), file).forEach(listener -> listener.onFileModify(file.toString()));
                continue;
            }
            if (eventKind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                matchedListeners(getDirPath(key), file).forEach(listener -> listener.onFileDelete(file.toString()));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(final OnFileChangeListener listener, final String dirPath, final String... globPatterns) throws IOException {
        Path dir = Paths.get(dirPath);
        this.register(
                listener,
                dir,
                new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE},
                globPatterns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(final OnFileChangeListener listener, final Path dirPath, final WatchEvent.Kind[] events, final String... globPatterns) throws IOException {

        if (!Files.isDirectory(dirPath)) {
            throw new IllegalArgumentException(dirPath + " is not a directory.");
        }

        if (!dirPathToListenersMap.containsKey(dirPath)) {
            // May throw
            WatchKey key = dirPath.register(watchService, events);

            watchKeyToDirPathMap.put(key, dirPath);
            dirPathToListenersMap.put(dirPath, newConcurrentSet());
        }

        getListeners(dirPath).add(listener);

        Set<PathMatcher> patterns = newConcurrentSet();

        for (String globPattern : globPatterns) {
            patterns.add(matcherForGlobExpression(globPattern));
        }

        if (patterns.isEmpty()) {
            patterns.add(matcherForGlobExpression("*")); // Match everything if no filter is found
        }

        listenerToFilePatternsMap.put(listener, patterns);

        LOGGER.debug("Watching files matching {} under {} for changes.", Arrays.toString(globPatterns), dirPath);
    }

    @Override
    public void register(OnFileChangeListener listener, FolderInformation folderInformation, final String... globPatterns) throws IOException {
        WatchEvent.Kind[] events = new WatchEvent.Kind[folderInformation.getWatchEventKindes().size()];
        this.register(listener, folderInformation.getFolderPath(), folderInformation.getWatchEventKindes().toArray(events), globPatterns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        LOGGER.debug("Starting file watcher service.");

        WatchKey key;
        try {
            key = watchService.take();
        } catch (InterruptedException ex) {
            LOGGER.debug(DirectoryWatchService.class.getSimpleName() + " service interrupted.");
            return;
        }

        if (null == getDirPath(key)) {
            LOGGER.debug("Watch key not recognized.");
            return;
        }

        notifyListeners(key);

        // Reset key to allow further events for this key to be processed.
        boolean valid = key.reset();
        if (!valid) {
            watchKeyToDirPathMap.remove(key);
            if (watchKeyToDirPathMap.isEmpty()) {
                return;
            }
        }

        LOGGER.debug("Stopping file watcher service.");
    }

    @Override
    protected ServiceConfiguration configuration() {
        return new ServiceConfiguration("DirectoryWatchService", 1, TimeUnit.MILLISECONDS, false);
    }
}
