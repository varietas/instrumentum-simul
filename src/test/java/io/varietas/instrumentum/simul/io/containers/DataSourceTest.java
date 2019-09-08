/*
 * Copyright 2019 Michael Rhöse.
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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
public class DataSourceTest {

    /**
     * Test of DIR method, of class DataSource.
     */
    @Test
    public void testDIR() {

        int id = 0;
        String name = "the os name";
        String path = "the source path";
        String target = "target";
        DataSource result = DataSource.DIR(id, name, path, target);

        Assertions.assertThat(result.getType()).isEqualTo(DataSource.Types.DIR);
    }

    /**
     * Test of HTTP method, of class DataSource.
     */
    @Test
    public void testHTTP() {

        int id = 0;
        String name = "the os name";
        String path = "the source path";
        String target = "target";
        DataSource result = DataSource.HTTP(id, name, path, target);

        Assertions.assertThat(result.getType()).isEqualTo(DataSource.Types.HTTP);
    }

    /**
     * Test of SECURED_HTTP method, of class DataSource.
     */
    @Test
    public void testSECURED_HTTP() {

        int id = 0;
        String name = "the os name";
        String path = "the source path";
        String target = "target";
        String username = "user name";
        String password = "password";
        DataSource result = DataSource.SECURED_HTTP(id, name, path, target, username, password);

        Assertions.assertThat(result.getType()).isEqualTo(DataSource.Types.HTTPS);
    }

    /**
     * Test of FTP method, of class DataSource.
     */
    @Test
    public void testFTP() {

        int id = 0;
        String name = "the os name";
        String path = "the source path";
        String target = "target";
        DataSource result = DataSource.FTP(id, name, path, target);

        Assertions.assertThat(result.getType()).isEqualTo(DataSource.Types.FTP);
    }

    /**
     * Test of SECURED_FTP method, of class DataSource.
     */
    @Test
    public void testSECURED_FTP() {

        int id = 0;
        String name = "the os name";
        String path = "the source path";
        String target = "target";
        String username = "user name";
        String password = "password";
        DataSource result = DataSource.SECURED_FTP(id, name, path, target, username, password);

        Assertions.assertThat(result.getType()).isEqualTo(DataSource.Types.SFTP);
    }

    /**
     * Test of of method, of class DataSource.
     */
    @Test
    public void testOf() {

        int id = 0;
        String name = "the os name";
        String path = "the source path";
        String target = "target";
        DataSource.Types type = null;
        String username = "user name";
        String password = "password";
        DataSource result = DataSource.of(id, name, path, target, type, username, password);

        Assertions.assertThat(result.getType()).isNull();
    }
}
