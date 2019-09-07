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

import io.varietas.instrumentum.simul.io.containers.Tuple2;
import io.varietas.instrumentum.simul.io.containers.TupleBuilder;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>ServiceExecuter</h2>
 * <p>
 * The service executer is the central handler of services. The instance takes 1...n Services and schedules them. There are two factory methods that manipulate what kind of {@link ScheduledExecutorService} has to be used. The default executer service is initialized with a pool size equals to services count.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/21/2018
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceExecutor {

    private static ScheduledExecutorService scheduledExecutorService;
    private final List<Tuple2<Service, ScheduledFuture>> services;

    /**
     * Builder method to create a service executer instance by a list of services. The thread pool size is equals to number of given services.
     *
     * @param services Services that are managed by this executer.
     *
     * @return Instance of the executor service.
     */
    public static ServiceExecutor of(final List<Service> services) {

        return ServiceExecutor.of(Executors.newScheduledThreadPool(services.size()), services);
    }

    /**
     * Builder method to create a service executer instance with a given executor. The thread pool size must be configured in the given executor.
     *
     * @param scheduledExecutorService External created executor service.
     * @param services                 Services that are managed by this executer
     *
     * @return Instance of the executor service.
     */
    @SneakyThrows
    public static ServiceExecutor of(final ScheduledExecutorService scheduledExecutorService, final List<Service> services) {

        if (Objects.isNull(services) || services.isEmpty()) {
            throw new InstantiationException("A service executor requires 1...N services.");
        }

        if (Objects.isNull(scheduledExecutorService)) {
            throw new InstantiationException("A service executor requires a executor service instance.");
        }

        ServiceExecutor.scheduledExecutorService = scheduledExecutorService;

        final List mapped = services.stream()
                .map(service -> TupleBuilder.of(service))
                .collect(Collectors.toList());

        return new ServiceExecutor(mapped);
    }

    /**
     * Starts the service. This method blocks until the service has completely started.
     *
     * @param service Service that has to be started.
     */
    public void startService(final Service service) {
        final Tuple2<Service, ScheduledFuture> container = this.findServiceContainer(service, "START");

        if (Objects.nonNull(container.getV2())) {
            LOGGER.debug("Service '{}' already running.", service.configuration().serviceName);
            return;
        }

        container.withV2(ServiceExecutor.scheduledExecutorService.scheduleAtFixedRate(service, 0, service.configuration().perios, service.configuration().unit));
        LOGGER.debug("Starting service '{}'.", container.getV1().configuration().serviceName);
    }

    /**
     * Stops the service. This method blocks until the service has completely shut down.
     *
     * @param service Service that has to be stopped.
     */
    public void stopService(final Service service) {
        final Tuple2<Service, ScheduledFuture> container = this.findServiceContainer(service, "STOP");

        if (Objects.isNull(container.getV2())) {
            LOGGER.debug("Service '{}' already stopped.", container.getV1().configuration().serviceName);
            return;
        }
        LOGGER.debug("Stopping service '{}'.", container.getV1().configuration().serviceName);
        container.getV2().cancel(container.getV1().configuration().shutdownNow);
    }

    private Tuple2<Service, ScheduledFuture> findServiceContainer(final Service service, final String mode) {
        final String name = service.getClass().getName();
        return this.services.stream()
                .filter(serv -> serv.getClass().getName().equals(name))
                .findFirst().orElseThrow(() -> new NullPointerException("Service " + name + " not found for " + mode + "."));
    }
}
