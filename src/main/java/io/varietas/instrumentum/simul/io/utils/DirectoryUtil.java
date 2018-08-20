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

import io.varietas.instrumentum.simul.io.containers.FolderInformation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Objects;
import lombok.experimental.UtilityClass;

/**
 * <h2>DirectoryUtil</h2>
 * <p>
 * Contains useful methods to work with the IO capability of varietas.io.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 10/1/2017
 */
@UtilityClass
public class DirectoryUtil {

    /**
     * Creates a {@link FolderInformation} for a given folder path. This functionality should be available everywhere it is needed so a static method is the means of choice.
     *
     * @param folderPath The path of the folder where the folder information is for.
     * @param events     One or more events registered for this folder.
     *
     * @return The folder information instance for the given folder path.
     *
     * @throws IOException Thrown for all possible input/output failures.
     */
    public static FolderInformation createFolderInformation(final String folderPath, final WatchEvent.Kind<?>... events) throws IOException {

        final FolderInformation.FolderInformationBuilder builder = FolderInformation.builder();

        Path folder = Paths.get(folderPath);

        final boolean exists = Files.exists(folder);

        if (!exists) {
            folder = Files.createDirectory(folder);
        }

        final boolean directory = Files.isDirectory(folder);

        builder.exist(exists);
        builder.directory(directory);

        if (!directory) {
            throw new IOException("Target of path '" + folder.toString() + "' is no directory.");
        }

        if (Objects.nonNull(events) && events.length != 0) {

            final WatchService watchService = folder.getFileSystem().newWatchService();

            builder
                    .watchService(watchService)
                    .watchEventKindes(Arrays.asList(events));

            folder.register(watchService, events);
        }

        return builder
                .folderPath(folder)
                .build();
    }
}
