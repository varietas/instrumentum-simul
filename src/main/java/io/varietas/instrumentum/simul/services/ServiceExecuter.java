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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>ServiceExecuter</h2>
 * <p>
 * The service executer is the central handler of services. The instance takes 1...n Services and schedules them. There are two factory methods that manipulate what kind of
 * {@link ScheduledExecutorService} has to be used. The default executer service is initialized with a pool size equals to services count.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/21/2018
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceExecuter {

    private static ScheduledExecutorService scheduledExecutorService;
    private final List<Container> services;

    /**
     *
     * @param services
     * @return
     */
    public static ServiceExecuter of(final List<Service> services) {
        return ServiceExecuter.of(Executors.newScheduledThreadPool(services.size()), services);
    }

    /**
     *
     * @param scheduledExecutorService
     * @param services
     * @return
     */
    public static ServiceExecuter of(final ScheduledExecutorService scheduledExecutorService, final List<Service> services) {

        ServiceExecuter.scheduledExecutorService = scheduledExecutorService;

        final List<Container> mapped = services.stream()
                .map(service -> Container.of(service, null))
                .collect(Collectors.toList());

        return new ServiceExecuter(mapped);
    }

    /**
     * Starts the service. This method blocks until the service has completely started.
     *
     * @param service Service that has to be started.
     */
    public void startService(final Service service) {
        final Container container = this.findServiceContainer(service, "START");

        if (Objects.nonNull(container.scheduledFuture)) {
            LOGGER.debug("Service '{}' already running.", service.configuration().serviceName);
            return;
        }

        container.scheduledFuture = ServiceExecuter.scheduledExecutorService.scheduleAtFixedRate(service, 0, service.configuration().perios, service.configuration().unit);
        LOGGER.debug("Starting service '{}'.", container.service.configuration().serviceName);
    }

    /**
     * Stops the service. This method blocks until the service has completely shut down.
     *
     * @param service Service that has to be stopped.
     */
    public void stopService(final Service service) {
        final Container container = this.findServiceContainer(service, "STOP");

        if (Objects.isNull(container.scheduledFuture)) {
            LOGGER.debug("Service '{}' already stopped.", container.service.configuration().serviceName);
            return;
        }
        LOGGER.debug("Stopping service '{}'.", container.service.configuration().serviceName);
        container.scheduledFuture.cancel(container.service.configuration().shutdownNow);
    }

    private Container findServiceContainer(final Service service, final String mode) {
        final String name = service.getClass().getName();
        return this.services.stream()
                .filter(serv -> serv.getClass().getName().equals(name))
                .findFirst().orElseThrow(() -> new NullPointerException("Service " + name + " not found for " + mode + "."));
    }

    @AllArgsConstructor(staticName = "of")
    private static class Container {

        Service service;
        ScheduledFuture scheduledFuture;
    }
}
