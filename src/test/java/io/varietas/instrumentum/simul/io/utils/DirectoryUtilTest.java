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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author Michael Rhöse
 */
@RunWith(JUnit4.class)
public class DirectoryUtilTest {

    public static final String FOLDER_PATH = System.getProperty("java.io.tmpdir") + File.separatorChar + "directory-utils-folder";

    @ClassRule
    public static final TemporaryFolder FOLDER = new TemporaryFolder();
    private static Path PATH;

    @BeforeClass
    public static void setUp() throws IOException {
        PATH = FOLDER.newFolder().toPath();
    }

    /**
     * Test of createFolderInformation method, of class DirectoryUtil.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFolderInformationWithWatchService() throws Exception {
        WatchEvent.Kind[] events = {StandardWatchEventKinds.ENTRY_CREATE};
        FolderInformation result = DirectoryUtil.createFolderInformation(FOLDER_PATH, events);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getDirectory()).isTrue();
        Assertions.assertThat(result.getExist()).isFalse();
        Assertions.assertThat(result.getFolderUrl()).isEqualTo(Paths.get(FOLDER_PATH).toString());
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

        if (Files.notExists(Paths.get(PATH.toFile().getPath()))) {
            Files.createDirectory(Paths.get(PATH.toFile().getPath()));
        }

        Path failPath = Paths.get(PATH.toFile().getPath(), "test.txt");

        if (Files.notExists(failPath)) {
            Files.createFile(failPath);
        }

        Assertions.assertThatThrownBy(() -> DirectoryUtil.createFolderInformation(failPath.toString()))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("is no directory.");

        Files.delete(failPath);
    }

    /**
     * Test of createFolderInformation method, of class DirectoryUtil.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFolderInformationWithoutWatchServiceNullArray() throws Exception {
        FolderInformation result = DirectoryUtil.createFolderInformation(FOLDER_PATH);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getDirectory()).isTrue();
        Assertions.assertThat(result.getExist()).isFalse();
        Assertions.assertThat(result.getFolderUrl()).isEqualTo(Paths.get(FOLDER_PATH).toString());
        Assertions.assertThat(result.getWatchService()).isNull();
        Assertions.assertThat(result.getWatchEventKindes()).isEmpty();
    }

    @Test
    public void testCreateFolderInformationWithoutWatchServiceEmptyArray() throws Exception {
        WatchEvent.Kind[] events = {};
        FolderInformation result = DirectoryUtil.createFolderInformation(PATH.toFile().getPath(), events);

        Assertions.assertThat(result.getWatchService()).isNull();
        Assertions.assertThat(result.getWatchEventKindes()).isEmpty();
    }

    @Before
    @After
    public void prepareAndCleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(FOLDER_PATH));
    }
}
