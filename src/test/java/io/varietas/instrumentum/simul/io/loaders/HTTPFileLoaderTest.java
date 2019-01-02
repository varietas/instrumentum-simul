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
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * <h2>HTTPFileLoaderTest</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 07/19/2018
 */
@Slf4j
@RunWith(JUnit4.class)
public class HTTPFileLoaderTest {

    private final DataSource dataSource;

    public HTTPFileLoaderTest() {
        this.dataSource = DataSource.HTTP(0, "", "http://speedtest.tele2.net", "100KB.zip");
    }

    /**
     * Test of processedType method, of class HTTPFileLoader.
     */
    @Test
    public void testProcessedType() {

        HTTPFileLoader instance = (HTTPFileLoader) HTTPFileLoader.of(dataSource);
        DataSource.Types expResult = DataSource.Types.HTTP;
        DataSource.Types result = instance.processedType();
        Assertions.assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of performLoading method, of class HTTPFileLoader.
     */
    @Test
    public void testPerformLoading() {
        FileLoadResult result = null;
        try {
            HTTPFileLoader instance = (HTTPFileLoader) HTTPFileLoader.of(this.dataSource);

            result = instance.performLoading();
            Assertions.assertThat(result.getStatusCode()).isEqualTo(200);
            Assertions.assertThat(result.getMessage()).isEqualTo("OK");
            Assertions.assertThat(result.mappedValue()).isPresent();
        } catch (ComparisonFailure cf) {
            LOGGER.error(result.toString());
            throw cf;
        }
    }

}
