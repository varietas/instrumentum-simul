/*
 * The MIT License (MIT)
 * Copyright (c) 2015, Hindol Adhya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.varietas.instrumentum.simul.services;

import io.varietas.instrumentum.simul.io.errors.ServiceExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>Service</h2>
 * <p>
 * Interface definition for services. This services are tasks that are manageable by {@link java.util.concurrent.ExecutorService} and give you a basic structure for implementing start and stop routines.
 *
 * @author Hindol Adhya
 * @author Michael Rhöse
 * @version 1.0.0.0, 9/4/2015
 */
public interface Service extends Runnable {

    /**
     * Returns the configuration of the service instance. The method must be implemented by each service type.
     *
     * @return The service configuration.
     */
    ServiceConfiguration configuration();

    /**
     * <h2>ServiceConfiguration</h2>
     * <p>
     * This class is a container to store and access the service configuration.
     *
     * @author Michael Rhöse
     * @version 1.0.0.0, 10/22/2017
     */
    @AllArgsConstructor(staticName = "of")
    public static class ServiceConfiguration {

        final String serviceName;
        final int periods;
        final TimeUnit unit;
        final boolean shutdownNow;
    }

    @Override
    default public void run() {

        final Logger logger = LoggerFactory.getLogger(this.getClass());

        if (logger.isDebugEnabled()) {
            logger.debug("Execute service.");
        }

        this.execute();

        if (logger.isDebugEnabled()) {
            logger.debug("Execute service finished.");
        }
    }

    /**
     * When an object implementing interface <code>Service</code> is used to create a thread, starting the thread causes the object's <code>execute</code> method to be called in that separately executing thread.
     * <p>
     * The general contract of the method <code>execute</code> is that it may take any action whatsoever.
     *
     * @throws ServiceExecutionException
     */
    void execute() throws ServiceExecutionException;
}
