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
package io.varietas.instrumentum.simul.io.container;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * <h2>FolderInformation</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/1/2017
 */
public class FolderInformation {

    @Setter
    @Getter
    Path folderPath;
    @Setter
    @Getter
    WatchService watchService;
    @Getter
    List<WatchEvent.Kind<?>> watchEventKindes;
    @Setter
    @Getter
    Boolean directory;
    @Setter
    @Getter
    Boolean exist;

    public FolderInformation() {
        this.watchEventKindes = new ArrayList<>();
    }

    public FolderInformation(final Path folderPath, final Boolean isDirectory, final Boolean isExist) {
        this.folderPath = folderPath;
        this.directory = isDirectory;
        this.exist = isExist;
        this.watchEventKindes = new ArrayList<>();
    }

    public FolderInformation(final Path folderPath, final WatchService watchService, final WatchEvent.Kind<?>... watchEventKindes) {
        this.folderPath = folderPath;
        this.watchService = watchService;
        this.watchEventKindes = Arrays.asList(watchEventKindes);
    }

    public FolderInformation(
        final Path folderPath,
        final WatchService watchService,
        final Boolean isDirectory,
        final Boolean isExist,
        final WatchEvent.Kind<?>... watchEventKindes
    ) {
        this.folderPath = folderPath;
        this.watchService = watchService;
        this.watchEventKindes = Arrays.asList(watchEventKindes);
        this.directory = isDirectory;
        this.exist = isExist;
    }

    public void setWatchEventKindes(final WatchEvent.Kind<?>... watchEventKindes) {
        this.watchEventKindes.addAll(Arrays.asList(watchEventKindes));
    }

    public String getFolderUrl() {
        return this.folderPath.toString();
    }
}
