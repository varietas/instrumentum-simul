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
package io.varietas.instrumentum.simul.services;

import io.varietas.instrumentum.simul.io.SimpleDirectoryWatchService;
import io.varietas.instrumentum.simul.io.errors.ServiceCreationException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
public class ServiceExecutorTest {

    private final List<Service> services;

    public ServiceExecutorTest() throws ServiceCreationException {
        this.services = Collections.singletonList(new SimpleDirectoryWatchService());
    }

    /**
     * Test of of method, of class ServiceExecutor.
     */
    @Test
    public void testOf_List() throws IOException {

        ServiceExecutor result = ServiceExecutor.of(this.services);
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test of of method, of class ServiceExecutor.
     */
    @Test
    public void testOf_ScheduledExecutorService_List() throws IOException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
        ServiceExecutor result = ServiceExecutor.of(executor, this.services);
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test of of method, of class ServiceExecutor.
     */
    @Test
    public void testOf_ScheduledExecutorService_ListFailsNullList() throws IOException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
        Assertions.assertThatThrownBy(() -> ServiceExecutor.of(executor, null))
                .isInstanceOf(InstantiationException.class)
                .hasMessage("A service executor requires 1...N services.");
    }

    /**
     * Test of of method, of class ServiceExecutor.
     */
    @Test
    public void testOf_ScheduledExecutorService_ListFailsNullExecutorService() throws IOException {

        Assertions.assertThatThrownBy(() -> ServiceExecutor.of(null, this.services))
                .isInstanceOf(InstantiationException.class)
                .hasMessage("A service executor requires a executor service instance.");
    }

    /**
     * Test of startService method, of class ServiceExecutor. TODO: Think about tests
     */
//    @Test
    public void testStartService() {
        Assertions.fail("The test case is a prototype.");
    }

    /**
     * Test of stopService method, of class ServiceExecutor. TODO: Think about tests
     */
//    @Test
    public void testStopService() {
        Assertions.fail("The test case is a prototype.");
    }

}
