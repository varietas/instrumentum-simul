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
import org.junit.Test;

/**
 * <h2>FTPFileLoaderTest</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 07/19/2018
 */
public class FTPFileLoaderTest {

    public FTPFileLoaderTest() {
    }

    /**
     * Test of processedType method, of class FTPFileLoader.
     */
    @Test
    public void testProcessedType() {
        System.out.println("processedType");
        FTPFileLoader instance = null;
        DataSource.Types expResult = null;
        DataSource.Types result = instance.processedType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of performLoading method, of class FTPFileLoader.
     */
    @Test
    public void testPerformLoading() {
        System.out.println("performLoading");
        FTPFileLoader instance = null;
        FileLoadResult expResult = null;
        FileLoadResult result = instance.performLoading();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
