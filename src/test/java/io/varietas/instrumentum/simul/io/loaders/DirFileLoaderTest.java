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
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * <h2>DirFileLoaderTest</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 07/19/2018
 */
@Slf4j
public class DirFileLoaderTest {

    private final DataSource dataSource;

    public DirFileLoaderTest() {
        this.dataSource = DataSource.DIR(0, "", "/home/micha", "README.md");
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
        Assertions.assertThat(result.mappedValue()).isNotNull();
    }

}
