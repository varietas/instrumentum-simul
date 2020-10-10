/*
 * Copyright 2018 Michael Rhöse.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy newInstance the License at
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

import io.varietas.instrumentum.simul.io.errors.ServiceCreationException;
import io.varietas.instrumentum.simul.io.errors.ServiceExecutionException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 * @author Michael Rhöse
 */
public class ServiceExecutorTest {

    private final List<Service> services;

    public ServiceExecutorTest() throws ServiceCreationException {
        this.services = Collections.singletonList(SimpleExecutionTimeRecorderService.of());
    }

    /**
     * Test newInstance newInstance method, newInstance class ServiceExecutor.
     */
    @Test
    public void test_list() throws IOException {

        final ServiceExecutor result = ServiceExecutor.of(this.services);
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test newInstance newInstance method, newInstance class ServiceExecutor.
     */
    @Test
    public void test_scheduledExecutorService_List() throws IOException {

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
        final ServiceExecutor result = ServiceExecutor.of(executor, this.services);
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test newInstance newInstance method, newInstance class ServiceExecutor.
     */
    @Test
    public void test_scheduledExecutorService_ListFailsNullList() throws IOException {

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
        Assertions.assertThatThrownBy(() -> ServiceExecutor.of(executor, null))
                .isInstanceOf(InstantiationException.class)
                .hasMessage("A service executor requires 1...N services.");
    }

    /**
     * Test newInstance newInstance method, newInstance class ServiceExecutor.
     */
    @Test
    public void test_scheduledExecutorService_ListFailsNullExecutorService() throws IOException {

        Assertions.assertThatThrownBy(() -> ServiceExecutor.of(null, this.services))
                .isInstanceOf(InstantiationException.class)
                .hasMessage("A service executor requires a executor service instance.");
    }

    /**
     * Test of startService and stopService method, of class ServiceExecutor.
     */
    @Test
    public void test_startAndStopService() throws InterruptedException {

        final ServiceExecutor executor = ServiceExecutor.of(this.services);
        final SimpleExecutionTimeRecorderService service = SimpleExecutionTimeRecorderService.class.cast(this.services.get(0));

        executor.startService(service);

        Thread.sleep(99);

        executor.stopService(service);

        final int resultSize = service.getExecutionTimes().size();
        Assertions.assertThat(resultSize).isGreaterThanOrEqualTo(4);
    }

    /**
     * Test of startService and stopService method when service is already running, of class ServiceExecutor.
     */
    @Test
    public void test_startAndStopService_noErrorWhenAlreadyRunning() throws InterruptedException {

        final ServiceExecutor executor = ServiceExecutor.of(this.services);
        final SimpleExecutionTimeRecorderService service = SimpleExecutionTimeRecorderService.class.cast(this.services.get(0));

        executor.startService(service);
        executor.startService(service);

        Thread.sleep(50);

        executor.stopService(service);
        executor.stopService(service);

        final int resultSize = service.getExecutionTimes().size();
        Assertions.assertThat(resultSize).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void test_run_anyErrorThrowsRSpecificRuntimeException() {

        final SimpleExecutionTimeRecorderService service = Mockito.mock(SimpleExecutionTimeRecorderService.class);
        Mockito.doCallRealMethod().when(service).run();
        Mockito.when(service.configuration()).thenReturn(SimpleExecutionTimeRecorderService.of().configuration());
        Mockito.doThrow(new ServiceExecutionException(SimpleExecutionTimeRecorderService.class)).when(service).execute();

        Throwable result = Assertions.catchThrowable(() -> service.run());

        Assertions.assertThat(result).isInstanceOf(ServiceExecutionException.class);
    }
}
