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

import io.varietas.instrumentum.simul.io.containers.FolderInformation;
import io.varietas.instrumentum.simul.io.errors.ServiceRegistrationException;
import io.varietas.instrumentum.simul.io.handlers.FileEventHandler;
import io.varietas.instrumentum.simul.services.Service;
import java.nio.file.Path;

/**
 * <h2>DirectoryWatchService</h2>
 * <p>
 * Interface definition of a simple directory watch service.
 * <p>
 * Implementations of this interface allow interested parties to <em>listen</em>
 * to file system events coming from a specific directory.
 *
 * @author Hindol Adhya
 * @author Michael Rhöse
 * @version 1.0.0.0, 9/4/2015
 */
public interface DirectoryWatchService extends Service {

    /**
     * Notifies the implementation of <em>this</em> interface that <code>dirPath</code> should be monitored for file system events. If the changed file matches any of the <code>globPatterns</code>, <code>listener</code> should be notified.
     *
     * @param eventHandler The event handler.
     * @param dirPath      The directory path.
     * @param globPatterns Zero or more file patterns to be matched against file names. If none provided, matches <em>any</em> file.
     *
     * @return Instance of this service for a fluent like usage
     *
     * @throws ServiceRegistrationException If <code>dirPath</code> is not a directory.
     */
    DirectoryWatchService register(FileEventHandler eventHandler, String dirPath, String... globPatterns) throws ServiceRegistrationException;

    /*
     * Suppress Exception
     */
    /**
     * Notifies the implementation of <em>this</em> interface that <code>dirPath</code> should be monitored for file system events. If the changed file matches any of the <code>globPatterns</code>, <code>listener</code> should be notified.
     * <p>
     * Possible patterns:
     * <ul>
     * <li>E.g. "*.log"</li>
     * <li>E.g. "input-?.txt"</li>
     * <li>E.g. "config.ini"</li>
     * <li>As many patterns as you like</li>
     * </ul>
     *
     * @param eventHandler The event handler.
     * @param dirPath      The directory path.
     * @param globPatterns Zero or more file patterns to be matched against file names. If none provided, matches <em>any</em> file.
     *
     * @return Instance of this service for a fluent like usage
     *
     * @throws ServiceRegistrationException If <code>dirPath</code> is not a directory.
     */
    DirectoryWatchService register(FileEventHandler eventHandler, Path dirPath, String... globPatterns) throws ServiceRegistrationException;

    /*
     * Suppress Exception
     */
    /**
     * Notifies the implementation of <em>this</em> interface that <code>dirPath</code> should be monitored for file system events. If the changed file matches any of the <code>globPatterns</code>, <code>listener</code> should be notified.
     * <p>
     * Possible patterns:
     * <ul>
     * <li>E.g. "*.log"</li>
     * <li>E.g. "input-?.txt"</li>
     * <li>E.g. "config.ini"</li>
     * <li>As many patterns as you like</li>
     * </ul>
     *
     * @param eventHandler      The event handler.
     * @param folderInformation Container which holds all information for a single folder.
     * @param globPatterns      Zero or more file patterns to be matched against file names. If none provided, matches <em>any</em> file.
     *
     * @return Instance of this service for a fluent like usage
     *
     * @throws ServiceRegistrationException If <code>dirPath</code> is not a directory.
     */
    DirectoryWatchService register(FileEventHandler eventHandler, FolderInformation folderInformation, String... globPatterns) throws ServiceRegistrationException;
}
