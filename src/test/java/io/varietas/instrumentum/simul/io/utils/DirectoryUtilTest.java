/*
 * Copyright 2018 Michael Rhöse.
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
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author Michael Rhöse
 */
public class DirectoryUtilTest {

    @TempDir
    static Path FOLDER;

    /**
     * Test of createFolderInformation method, of class DirectoryUtil.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFolderInformationWithWatchService() throws Exception {

        String notExists = Path.of(FOLDER.toString(), "notExists1").toString();

        WatchEvent.Kind[] events = {StandardWatchEventKinds.ENTRY_CREATE};
        FolderInformation result = DirectoryUtil.createFolderInformation(notExists, events);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getDirectory()).isTrue();
        Assertions.assertThat(result.getExist()).isFalse();
        Assertions.assertThat(result.getFolderUrl()).isEqualTo(notExists);
        Assertions.assertThat(result.getWatchService()).isNotNull();
        Assertions.assertThat(result.getWatchEventKindes()).hasSize(1);
    }

    /**
     * Test of createFolderInformation method, of class DirectoryUtil.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFolderInformationFails() throws Exception {

        Path failPath = FOLDER.resolve("test.txt");

        if (!Files.exists(failPath)) {
            Files.createFile(failPath);
        }

        Assertions.assertThatThrownBy(() -> DirectoryUtil.createFolderInformation(failPath.toString()))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("is no directory.");
    }

    /**
     * Test of createFolderInformation method, of class DirectoryUtil.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFolderInformationWithoutWatchServiceNullArray() throws Exception {

        String notExisting = Path.of(FOLDER.toString(), "notExists2").toString();

        FolderInformation result = DirectoryUtil.createFolderInformation(notExisting);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getDirectory()).isTrue();
        Assertions.assertThat(result.getExist()).isFalse();
        Assertions.assertThat(result.getFolderUrl()).isEqualTo(notExisting);
        Assertions.assertThat(result.getWatchService()).isNull();
        Assertions.assertThat(result.getWatchEventKindes()).isNull();
    }

    @Test
    public void testCreateFolderInformationWithoutWatchServiceEmptyArray() throws Exception {
        WatchEvent.Kind[] events = {};
        FolderInformation result = DirectoryUtil.createFolderInformation(FOLDER.toString(), events);

        Assertions.assertThat(result.getWatchService()).isNull();
        Assertions.assertThat(result.getWatchEventKindes()).isNull();
    }
}
