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
package io.varietas.instrumentum.simul.io.containers;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

/**
 * <h2>FolderInformation</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 10/1/2017
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderInformation {

    Path folderPath;

    WatchService watchService;

    @Singular
    List<WatchEvent.Kind<?>> watchEventKindes;

    /**
     * This flag signals the reason for a possible failing. Folder information can only be created for paths that pointed to folders.
     */
    Boolean directory;

    /**
     * This flag signals the existing for a given path before creation of this folder information.
     */
    Boolean exist;

    public String getFolderUrl() {
        return this.folderPath.toString();
    }
}
