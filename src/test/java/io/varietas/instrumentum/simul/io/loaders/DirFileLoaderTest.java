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

import io.varietas.instrumentum.simul.TestConstants;
import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.io.containers.FileLoadResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * <h2>DirFileLoaderTest</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 07/19/2018
 */
@RunWith(JUnit4.class)
public class DirFileLoaderTest {

    private static Path testFilePath;
    private final DataSource dataSource;

    public DirFileLoaderTest() {

        this.dataSource = DataSource.DIR(0, "", TestConstants.TEST_FOLDER_PATH, "dirLoadertest.txt");
    }

    @BeforeClass
    public static void setUp() throws IOException {
        DirFileLoaderTest.testFilePath = Paths.get(TestConstants.TEST_FOLDER_PATH, "dirLoadertest.txt");

        if (Files.notExists(Paths.get(TestConstants.TEST_FOLDER_PATH))) {
            Files.createDirectory(Paths.get(TestConstants.TEST_FOLDER_PATH));
        }

        if (Files.notExists(DirFileLoaderTest.testFilePath)) {
            Files.createFile(DirFileLoaderTest.testFilePath);
        }
    }

    /**
     * Test of processedType method, of class DirFileLoader.
     */
    @Test
    public void testProcessedType() {
        DirFileLoader instance = (DirFileLoader) DirFileLoader.of(this.dataSource);
        DataSource.Types expResult = DataSource.Types.DIR;
        DataSource.Types result = instance.processedType();
        Assertions.assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of performLoading method, of class DirFileLoader.
     */
    @Test
    public void testPerformLoading() {
        DirFileLoader instance = (DirFileLoader) DirFileLoader.of(this.dataSource);

        FileLoadResult result = instance.performLoading();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(200);
        Assertions.assertThat(result.mappedValue()).isPresent();
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        Files.deleteIfExists(DirFileLoaderTest.testFilePath);
        Files.deleteIfExists(Paths.get(TestConstants.TEST_FOLDER_PATH));
    }
}
