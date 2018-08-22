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
 * <h2>ResourceLoaderFactoryTest</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/20/2018
 */
@RunWith(JUnit4.class)
public class ResourceLoaderFactoryTest {

    private static Path testFilePath;
    private final DataSource ftpDataSource;
    private final DataSource httpDataSource;
    private final DataSource dirDataSource;

    public ResourceLoaderFactoryTest() {
        this.ftpDataSource = DataSource.FTP(0, "", "speedtest.tele2.net", "100KB.zip");
        this.httpDataSource = DataSource.HTTP(0, "", "http://speedtest.tele2.net", "100KB.zip");;
        this.dirDataSource = DataSource.DIR(0, "", TestConstants.TEST_FOLDER_PATH, "dirLoadertest.txt");
    }

    @BeforeClass
    public static void setUp() throws IOException {
        ResourceLoaderFactoryTest.testFilePath = Paths.get(TestConstants.TEST_FOLDER_PATH, "dirLoadertest.txt");

        if (Files.notExists(Paths.get(TestConstants.TEST_FOLDER_PATH))) {
            Files.createDirectory(Paths.get(TestConstants.TEST_FOLDER_PATH));
        }

        if (Files.notExists(ResourceLoaderFactoryTest.testFilePath)) {
            Files.createFile(ResourceLoaderFactoryTest.testFilePath);
        }
    }

    /**
     * Test of load method, of class ResourceLoaderFactory.
     */
    @Test
    public void testLoadFtp() {
        ResourceLoaderFactory instance = ResourceLoaderFactory.of(this.ftpDataSource);
        FileLoadResult result = instance.load();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(200);
    }

    /**
     * Test of load method, of class ResourceLoaderFactory.
     */
    @Test
    public void testLoadHttp() {
        ResourceLoaderFactory instance = ResourceLoaderFactory.of(this.httpDataSource);
        FileLoadResult result = instance.load();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(200);
    }

    /**
     * Test of load method, of class ResourceLoaderFactory.
     */
    @Test
    public void testLoadDir() {
        ResourceLoaderFactory instance = ResourceLoaderFactory.of(this.dirDataSource);
        FileLoadResult result = instance.load();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(200);
    }

    /**
     * Test of of method, of class ResourceLoaderFactory.
     */
    @Test
    public void testOf() {
        Assertions.assertThat(ResourceLoaderFactory.of(this.ftpDataSource)).isNotNull();
        Assertions.assertThat(ResourceLoaderFactory.of(this.httpDataSource)).isNotNull();
        Assertions.assertThat(ResourceLoaderFactory.of(this.dirDataSource)).isNotNull();
    }

    /**
     * Test of of method, of class ResourceLoaderFactory.
     */
    @Test
    public void testOfFailsForNullSource() {
        Assertions.assertThatThrownBy(() -> ResourceLoaderFactory.of(null).load())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Source is required and must be configured before calling #load().");
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        Files.deleteIfExists(ResourceLoaderFactoryTest.testFilePath);
        Files.deleteIfExists(Paths.get(TestConstants.TEST_FOLDER_PATH));
    }
}
