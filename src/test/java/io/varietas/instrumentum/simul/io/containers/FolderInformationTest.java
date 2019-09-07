/*
 * Copyright 2019 Michael Rhöse.
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
package io.varietas.instrumentum.simul.io.containers;

import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
public class FolderInformationTest {

    /**
     * Test of getFolderUrl method, of class FolderInformation.
     */
    @Test
    public void testGetFolderUrl() {
        String expResult = "c:\\temp";
        FolderInformation instance = FolderInformation.builder().folderPath(Path.of(expResult)).build();

        String result = instance.getFolderUrl();
        Assertions.assertThat(result).isNotBlank().isEqualTo(expResult);
    }

    /**
     * Test of getFolderPath method, of class FolderInformation.
     */
    @Test
    public void testGetFolderPath() {
        Path expResult = Path.of("c:\\temp");
        FolderInformation instance = FolderInformation.builder().folderPath(expResult).build();

        Path result = instance.getFolderPath();
        Assertions.assertThat(result).isNotNull().isEqualTo(expResult);
    }

    /**
     * Test of getWatchService method, of class FolderInformation.
     */
//    @Test
//    public void testGetWatchService() {
//        
//        FolderInformation instance = FolderInformation.builder().watchService().build();
//        WatchService expResult = null;
//        WatchService result = instance.getWatchService();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    /**
//     * Test of getWatchEventKindes method, of class FolderInformation.
//     */
//    @Test
//    public void testGetWatchEventKindes() {
//        FolderInformation instance = null;
//        List<WatchEvent.Kind<?>> expResult = null;
//        List<WatchEvent.Kind<?>> result = instance.getWatchEventKindes();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDirectory method, of class FolderInformation.
//     */
//    @Test
//    public void testGetDirectory() {
//        FolderInformation instance = null;
//        Boolean expResult = null;
//        Boolean result = instance.getDirectory();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getExist method, of class FolderInformation.
//     */
//    @Test
//    public void testGetExist() {
//        FolderInformation instance = null;
//        Boolean expResult = null;
//        Boolean result = instance.getExist();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of builder method, of class FolderInformation.
//     */
//    @Test
//    public void testBuilder() {
//        FolderInformation.FolderInformationBuilder expResult = null;
//        FolderInformation.FolderInformationBuilder result = FolderInformation.builder();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class FolderInformation.
//     */
//    @Test
//    public void testEquals() {
//        Object o = null;
//        FolderInformation instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of hashCode method, of class FolderInformation.
//     */
//    @Test
//    public void testHashCode() {
//        FolderInformation instance = null;
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toString method, of class FolderInformation.
//     */
//    @Test
//    public void testToString() {
//        FolderInformation instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
