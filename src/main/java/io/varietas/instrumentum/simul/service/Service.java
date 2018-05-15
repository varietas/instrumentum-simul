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
package io.varietas.instrumentum.simul.service;

/**
 * <h2>Service</h2>
 * <p>
 * Interface definition for services. This services are tasks that are manageable by {@link java.util.concurrent.ExecutorService} and give you a basic structure for implementing start and stop
 * routines.
 *
 * @author Hindol Adhya
 * @author Michael Rhöse
 * @version 1.0.0, 9/4/2015
 */
public interface Service {

    /**
     * Starts the service. This method blocks until the service has completely started.
     */
    void start();

    /**
     * Stops the service. This method blocks until the service has completely shut down.
     */
    void stop();
}
