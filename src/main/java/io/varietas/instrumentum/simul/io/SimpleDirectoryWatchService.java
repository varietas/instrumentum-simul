/*
 * Copyright 2019 Michael Rhöse.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.varietas.instrumentum.simul.io;

import io.varietas.instrumentum.simul.io.containers.FolderInformation;
import io.varietas.instrumentum.simul.io.containers.Tuple2;
import io.varietas.instrumentum.simul.io.containers.Tuple3;
import io.varietas.instrumentum.simul.io.containers.TupleBuilder;
import io.varietas.instrumentum.simul.io.errors.ServiceCreationException;
import io.varietas.instrumentum.simul.io.errors.ServiceExecutionException;
import io.varietas.instrumentum.simul.io.errors.ServiceRegistrationException;
import io.varietas.instrumentum.simul.io.handlers.FileCreateHandler;
import io.varietas.instrumentum.simul.io.handlers.FileEventHandler;
import io.varietas.instrumentum.simul.io.handlers.FileModifyHandler;
import io.varietas.instrumentum.simul.io.utils.FileUtil;
import io.varietas.instrumentum.simul.storages.SimpleSortedStorage;
import io.varietas.instrumentum.simul.storages.SortedStorage;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
 * @version 1.0.0.0, 9/4/2015
 */
@Slf4j
public class SimpleDirectoryWatchService implements DirectoryWatchService {

    private final WatchService watchService;
    private final SortedStorage<String, Tuple3<WatchKey, Path, Set<Tuple2<FileEventHandler, Set<PathMatcher>>>>> storage;

    public SimpleDirectoryWatchService() throws ServiceCreationException {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            this.storage = SimpleSortedStorage.of(
                    StandardWatchEventKinds.ENTRY_CREATE.name(),
                    StandardWatchEventKinds.ENTRY_MODIFY.name(),
                    StandardWatchEventKinds.ENTRY_DELETE.name());
        } catch (IOException ex) {
            throw new ServiceCreationException(this.getClass(), ex);
        }
    }

    public static DirectoryWatchService of() throws ServiceCreationException {
        return new SimpleDirectoryWatchService();
    }

    @Override
    public DirectoryWatchService register(final FileEventHandler eventHandler, final String dirPath, final String... globPatterns) throws ServiceRegistrationException {
        return this.register(eventHandler, Paths.get(dirPath), globPatterns);
    }

    @Override
    public DirectoryWatchService register(final FileEventHandler eventHandler, final Path dirPath, final String... globPatterns) throws ServiceRegistrationException {

        try {
            final WatchEvent.Kind<Path> eventType = convertTypeToEventType(eventHandler);

            if (!Files.isDirectory(dirPath)) {
                throw new IllegalArgumentException(dirPath + " is not a directory.");
            }

            var existing = getExistingEntry(eventType, dirPath);

            if (existing.isPresent()) {
                existing.get().getV3().add(TupleBuilder.of(eventHandler, generatePathMatchers(globPatterns)));
                return this;
            }

            final WatchKey key = dirPath.register(watchService, eventType);

            final Set<Tuple2<FileEventHandler, Set<PathMatcher>>> handlers = newConcurrentSet();
            handlers.add(TupleBuilder.of(eventHandler, generatePathMatchers(globPatterns)));

            var pathConfig = TupleBuilder.of(key, dirPath, handlers);
            this.storage.store(pathConfig, eventType.name());

            return this;
        } catch (IOException ex) {
            throw new ServiceRegistrationException(this.getClass(), ex);
        }
    }

    @Override
    public DirectoryWatchService register(final FileEventHandler eventHandler, final FolderInformation folderInformation, final String... globPatterns) throws ServiceRegistrationException {
        return this.register(eventHandler, folderInformation.getFolderPath(), globPatterns);
    }

    @Override
    public ServiceConfiguration configuration() {
        return ServiceConfiguration.of("DirectoryWatchService", 1, TimeUnit.MILLISECONDS, false);
    }

    @Override
    public void execute() throws ServiceExecutionException {

        try {
            final WatchKey key = watchService.take();

            if (Objects.isNull(key) && LOGGER.isDebugEnabled()) {
                LOGGER.debug("Watch key not recognized.");
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                final WatchEvent.Kind<?> eventKind = event.kind();

                // Overflow occurs when the watch event queue is overflown
                // with events.
                if (eventKind.equals(StandardWatchEventKinds.OVERFLOW)) {
                    // TODO: Notify all listeners.
                    return;
                }

                @SuppressWarnings("unchecked")
                final var pathEvent = (WatchEvent<Path>) event;

                final var file = pathEvent.context();

                final var tuple = this.storage.getStorage().get(eventKind.name()).stream()
                        .filter(entry -> Objects.equals(entry.getV2(), file))
                        .findFirst();

                if (tuple.isEmpty()) {
                    return;
                }

                tuple.get().getV3().stream()
                        .filter(entry -> FileUtil.matchesAny(file, entry.getV2()))
                        .forEach(entry -> entry.getV1().handle(file.toString()));
            }

            // Reset key to allow further events for this key to be processed.
            boolean valid = key.reset();
            if (!valid) {

                final var keysToDelete = this.storage.getStorage().values().stream()
                        .flatMap(List::stream)
                        .filter(entry -> Objects.equals(entry.getV1(), key))
                        .collect(Collectors.toList());

                this.storage.getStorage().entrySet().forEach(entry -> entry.getValue().removeAll(keysToDelete));
            }
        } catch (final InterruptedException ex) {
            throw new ServiceExecutionException(this.getClass(), "Service interrupted", ex);
        }
    }

    private static <T> Set<T> newConcurrentSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    private Set<PathMatcher> generatePathMatchers(final String[] globPatterns) {
        final Set<PathMatcher> patterns = newConcurrentSet();

        for (String globPattern : globPatterns) {
            patterns.add(FileUtil.matcherForGlobExpression(globPattern));
        }

        if (patterns.isEmpty()) {
            patterns.add(FileUtil.matcherForGlobExpression("*")); // Match everything if no filter is found
        }

        return patterns;
    }

    private WatchEvent.Kind<Path> convertTypeToEventType(final FileEventHandler handler) {

        if (FileCreateHandler.class.isInstance(handler.getClass())) {
            return StandardWatchEventKinds.ENTRY_CREATE;
        }
        if (FileModifyHandler.class.isInstance(handler.getClass())) {
            return StandardWatchEventKinds.ENTRY_MODIFY;
        }

        return StandardWatchEventKinds.ENTRY_DELETE;
    }

    private Optional<Tuple3<WatchKey, Path, Set<Tuple2<FileEventHandler, Set<PathMatcher>>>>> getExistingEntry(final WatchEvent.Kind<Path> eventType, final Path dirPath) {
        return this.storage.getStorage().get(eventType.name()).stream()
                .filter(entry -> entry.getV2().equals(dirPath))
                .findFirst();
    }
}
