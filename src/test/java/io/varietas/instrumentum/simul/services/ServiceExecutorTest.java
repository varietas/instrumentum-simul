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
import java.util.concurrent.ScheduledExecutorService;
import org.junit.Test;

/**
 *
 * @author Michael Rhöse
 */
public class ServiceExecutorTest {

    public ServiceExecutorTest() {
    }

    /**
     * Test of of method, of class ServiceExecutor.
     */
    @Test
    public void testOf_List() {
        System.out.println("of");
        List<Service> services = null;
        ServiceExecutor expResult = null;
        ServiceExecutor result = ServiceExecutor.of(services);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of of method, of class ServiceExecutor.
     */
    @Test
    public void testOf_ScheduledExecutorService_List() {
        System.out.println("of");
        ScheduledExecutorService scheduledExecutorService = null;
        List<Service> services = null;
        ServiceExecutor expResult = null;
        ServiceExecutor result = ServiceExecutor.of(scheduledExecutorService, services);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startService method, of class ServiceExecutor.
     */
    @Test
    public void testStartService() {
        System.out.println("startService");
        Service service = null;
        ServiceExecutor instance = null;
        instance.startService(service);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopService method, of class ServiceExecutor.
     */
    @Test
    public void testStopService() {
        System.out.println("stopService");
        Service service = null;
        ServiceExecutor instance = null;
        instance.stopService(service);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
