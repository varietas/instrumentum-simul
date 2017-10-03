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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * <h2>DirectoryWatchService</h2>
 *
 * Interface definition of a simple directory watch service.
 *
 * Implementations of this interface allow interested parties to <em>listen</em>
 * to file system events coming from a specific directory.
 *
 * @author Hindol Adhya
 * @author Michael Rhöse
 * @version 1.0.0, 9/4/2015
 */
public interface DirectoryWatchService extends Service {

    @Override
    void start();

    /* Suppress Exception */
    /**
     * Notifies the implementation of <em>this</em> interface that <code>dirPath</code> should be monitored for file system events. If the changed file matches any of the <code>globPatterns</code>,
     * <code>listener</code> should be notified.
     *
     * @param listener The listener.
     * @param dirPath The directory path.
     * @param globPatterns Zero or more file patterns to be matched against file names. If none provided, matches <em>any</em> file.
     * @throws IOException If <code>dirPath</code> is not a directory.
     */
    void register(OnFileChangeListener listener, String dirPath, String... globPatterns) throws IOException;

    /* Suppress Exception */
    /**
     * Notifies the implementation of <em>this</em> interface that <code>dirPath</code> should be monitored for file system events. If the changed file matches any of the <code>globPatterns</code>,
     * <code>listener</code> should be notified.
     *
     * Possible patterns:
     * <ul>
     * <li>E.g. "*.log"</li>
     * <li>E.g. "input-?.txt"</li>
     * <li>E.g. "config.ini"</li>
     * <li>As many patterns as you like</li>
     * </ul>
     *
     * @param listener The listener.
     * @param dirPath The directory path.
     * @param events The events which are registered for the directory.
     * @param globPatterns Zero or more file patterns to be matched against file names. If none provided, matches <em>any</em> file.
     * @throws IOException If <code>dirPath</code> is not a directory.
     */
    void register(OnFileChangeListener listener, Path dirPath, WatchEvent.Kind[] events, String... globPatterns) throws IOException;

    /* Suppress Exception */
    /**
     * Notifies the implementation of <em>this</em> interface that <code>dirPath</code> should be monitored for file system events. If the changed file matches any of the <code>globPatterns</code>,
     * <code>listener</code> should be notified.
     *
     * Possible patterns:
     * <ul>
     * <li>E.g. "*.log"</li>
     * <li>E.g. "input-?.txt"</li>
     * <li>E.g. "config.ini"</li>
     * <li>As many patterns as you like</li>
     * </ul>
     *
     * @param listener The listener.
     * @param folderInformation Container which holds all information for a single folder.
     * @param globPatterns Zero or more file patterns to be matched against file names. If none provided, matches <em>any</em> file.
     * @throws IOException If <code>dirPath</code> is not a directory.
     */
    void register(OnFileChangeListener listener, FolderInformation folderInformation, String... globPatterns) throws IOException;
}
