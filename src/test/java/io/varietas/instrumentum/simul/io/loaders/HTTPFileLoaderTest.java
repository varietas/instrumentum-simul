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

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.io.containers.FileLoadResult;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Rule;
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

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig()
            .dynamicPort()
            .dynamicHttpsPort());

    private final DataSource dataSource;

    private static final String URL_AS_STRING = "http://speedtest.tele2.net";
    private static final int STATUS_CODE_OK = 200;
    private static byte[] RESPONSE_BODY;

    @BeforeClass
    public static void setupBeforeClass() throws IOException {
        RESPONSE_BODY = IOUtils.toByteArray(HTTPFileLoaderTest.class.getResourceAsStream("/binaries/100KB.zip"));
    }

    public HTTPFileLoaderTest() {
        this.dataSource = DataSource.HTTP(0, "", URL_AS_STRING, "100KB.zip");
    }

    /**
     * Test of processedType method, of class HTTPFileLoader.
     */
    @Test
    public void testProcessedType() {

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(URL_AS_STRING))
                .willReturn(WireMock.aResponse()
                        .withBody(RESPONSE_BODY)
                        .withStatus(STATUS_CODE_OK)));

        HTTPFileLoader instance = (HTTPFileLoader) HTTPFileLoader.of(dataSource);
        DataSource.Types expResult = DataSource.Types.HTTP;
        DataSource.Types result = instance.processedType();
        Assertions.assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of performLoading method, of class HTTPFileLoader.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testPerformLoading() throws IOException {

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(URL_AS_STRING))
                .willReturn(WireMock.aResponse()
                        .withBody(RESPONSE_BODY)
                        .withStatus(STATUS_CODE_OK)));

        HTTPFileLoader instance = (HTTPFileLoader) HTTPFileLoader.of(this.dataSource);
        FileLoadResult result = instance.performLoading();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(STATUS_CODE_OK);
        Assertions.assertThat(result.getMessage()).isEqualTo("OK");
        Assertions.assertThat(result.mappedValue()).isPresent();
    }

}
