/*
 * Copyright 2017 Michael Rhöse.
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
package io.varietas.instrumentum.simul.service;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>AbstractService</h2>
 * <p>
 * This class represents the common of services. It implements the #start() and #stop() method of the {@link Service} interface.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/22/2017
 */
@Slf4j
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class AbstractService implements Service, Runnable {

    private final ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture scheduledFuture = null;

    /**
     * Start this <code>Service</code> instance by spawning a new thread.
     *
     * @see #stop()
     */
    @Override
    public void start() {
        if (Objects.isNull(this.scheduledFuture)) {
            LOGGER.debug("Starting service '{}'.", this.configuration().serviceName);
            this.scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(this, 0, this.configuration().perios, this.configuration().unit);
            return;
        }
        LOGGER.debug("Service '{}' already running.", this.configuration().serviceName);
    }

    /**
     * Stop this <code>Service</code> thread. The killing happens lazily, giving the running thread an opportunity to finish the work at hand.
     *
     * @see #start()
     */
    @Override
    public void stop() {
        if (Objects.nonNull(this.scheduledFuture)) {
            LOGGER.debug("Stopping service '{}'.", this.configuration().serviceName);
            this.scheduledFuture.cancel(this.configuration().shutdownNow);
            return;
        }
        LOGGER.debug("Service '{}' already stopped.", this.configuration().serviceName);
    }

    /**
     * Returns the configuration of the service instance. The method must be implemented by each service type.
     *
     * @return The service configuration.
     */
    protected abstract ServiceConfiguration configuration();

    /**
     * <h2>ServiceConfiguration</h2>
     * <p>
     * This class is a container to store and access the service configuration.
     *
     * @author Michael Rhöse
     * @version 1.0.0, 10/22/2017
     */
    @AllArgsConstructor
    protected static class ServiceConfiguration {

        private final String serviceName;
        private final int perios;
        private final TimeUnit unit;
        private final boolean shutdownNow;
    }
}
