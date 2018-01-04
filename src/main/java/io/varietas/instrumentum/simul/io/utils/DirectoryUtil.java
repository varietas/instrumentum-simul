/*
 * Copyright 2017 Michael Rhöse.
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
package io.varietas.instrumentum.simul.io.utils;

import io.varietas.instrumentum.simul.io.container.FolderInformation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.Objects;

/**
 * <h2>DirectoryUtil</h2>
 *
 * Contains useful methods to work with the IO capability of varietas.io.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/1/2017
 */
public class DirectoryUtil {

    /**
     * Creates a {@link FolderInformation} for a given folder path. This functionality should be available everywhere it is needed so a static method is the means of choice.
     *
     * @param folderPath The path of the folder the folder information is for.
     * @param events One or more events registered for this folder.
     * @return An optional object. The optional contains a plugin folder object or an "is empty" object.
     * @throws IOException Thrown for all possible input/output failures.
     */
    public static FolderInformation createFolderInformation(String folderPath, WatchEvent.Kind<?>... events) throws IOException {

        Path folder = Paths.get(folderPath);
        FolderInformation folderInformation = new FolderInformation(folder, Boolean.FALSE, Boolean.FALSE);

        folderInformation.setExist(Files.exists(folder));

        if (!folderInformation.getExist()) {
            folder = Files.createDirectory(folder);
        }

        folderInformation.setDirectory(Files.isDirectory(folder));

        if (!folderInformation.getDirectory()) {
            throw new IOException("Target of path '" + folder.toString() + "' is no directory.");
        }

        if (Objects.nonNull(events) && events.length != 0) {

            folderInformation.setWatchService(folder.getFileSystem().newWatchService());
            folderInformation.setWatchEventKindes(events);

            folderInformation.getFolderPath().register(folderInformation.getWatchService(), events);
        }

        return folderInformation;
    }
}
