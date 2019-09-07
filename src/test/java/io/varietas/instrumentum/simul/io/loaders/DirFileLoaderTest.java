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
package io.varietas.instrumentum.simul.io.loaders;

import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.io.containers.FileLoadResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * <h2>DirFileLoaderTest</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 07/19/2018
 */
public class DirFileLoaderTest {

    private DataSource DATA_SOURCE;

    @TempDir
    static Path FOLDER;

    @BeforeEach
    public void setUp() throws IOException {
        Path testFilePath = FOLDER.resolve("dirLoadertest.txt");

        if (!Files.exists(testFilePath)) {
            Files.createFile(testFilePath);
        }

        DATA_SOURCE = DataSource.DIR(0, "", testFilePath.getParent().toString(), "dirLoadertest.txt");
    }

    /**
     * Test of processedType method, of class DirFileLoader.
     */
    @Test
    public void testProcessedType() {
        DirFileLoader instance = (DirFileLoader) DirFileLoader.of(DATA_SOURCE);
        DataSource.Types expResult = DataSource.Types.DIR;
        DataSource.Types result = instance.processedType();
        Assertions.assertThat(result).isEqualTo(expResult);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testPerformLoading() {
        DirFileLoader instance = (DirFileLoader) DirFileLoader.of(DATA_SOURCE);

        FileLoadResult result = instance.performLoading();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(200);
        Assertions.assertThat(result.mappedValue()).isPresent();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testPerformLoadingFailsForNotExistingFolder() {
        DirFileLoader instance = (DirFileLoader) DirFileLoader.of(DataSource.DIR(0, "", "notExisting", "dirLoadertest.txt"));

        FileLoadResult result = instance.performLoading();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(500);
        Assertions.assertThat(result.getMessage()).startsWith("FAILED");
        Assertions.assertThat(result.mappedValue()).isNotPresent();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testPerformLoadingFailsForNotExistingFile() {
        DirFileLoader instance = (DirFileLoader) DirFileLoader.of(DataSource.DIR(0, "", System.getProperty("user.home"), "dirLoadertest.txt"));

        FileLoadResult result = instance.performLoading();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(500);
        Assertions.assertThat(result.getMessage()).startsWith("FAILED: Couldn't find file");
        Assertions.assertThat(result.mappedValue()).isNotPresent();
    }
}
