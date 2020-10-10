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
package io.varietas.instrumentum.simul.io;

import io.varietas.instrumentum.simul.io.handlers.FileCreateHandler;
import io.varietas.instrumentum.simul.services.Service;
import io.varietas.instrumentum.simul.services.ServiceExecutor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author Michael Rhöse
 */
@Slf4j
public class SimpleDirectoryWatchServiceTest {

    @TempDir
    static Path FOLDER;

    /**
     * Test of register method, of class SimpleDirectoryWatchService.
     */
    @Test
    public void testRegister_1() throws Exception {

        final AtomicReference<String> result = new AtomicReference<>("empty");

        final DirectoryWatchService service = SimpleDirectoryWatchService.newInstance();

        final FileCreateHandler createHandler = (dirPath) -> {
            result.set("Change from " + result.get() + " to created. " + dirPath);
            LOGGER.info("Is in the handler. " + dirPath);
        };

        service.register(createHandler, FOLDER.toString());

        final List<Service> services = new ArrayList<>();
        services.add(service);

        final ServiceExecutor executor = ServiceExecutor.of(services);

        executor.startService(service);

        Path testFilePath = FOLDER.resolve("dirLoadertest.txt");

        if (!Files.exists(testFilePath)) {
            Files.createFile(testFilePath);
        }

        executor.stopService(service);

        LOGGER.info(result.get());
    }

    /**
     * Test of register method, of class SimpleDirectoryWatchService.
     */
    @Test
    public void testRegister_2() throws Exception {
//        System.out.println("register");
//        FileEventHandler eventHandler = null;
//        Path dirPath = null;
//        String[] globPatterns = null;
//        SimpleDirectoryWatchService instance = null;
//        DirectoryWatchService expResult = null;
//        DirectoryWatchService result = instance.register(eventHandler, dirPath, globPatterns);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of register method, of class SimpleDirectoryWatchService.
     */
    @Test
    public void testRegister_3() throws Exception {
//        System.out.println("register");
//        FileEventHandler eventHandler = null;
//        FolderInformation folderInformation = null;
//        String[] globPatterns = null;
//        SimpleDirectoryWatchService instance = null;
//        DirectoryWatchService expResult = null;
//        DirectoryWatchService result = instance.register(eventHandler, folderInformation, globPatterns);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of configuration method, of class SimpleDirectoryWatchService.
     */
    @Test
    public void testConfiguration() {
//        System.out.println("configuration");
//        SimpleDirectoryWatchService instance = null;
//        Service.ServiceConfiguration expResult = null;
//        Service.ServiceConfiguration result = instance.configuration();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of execute method, of class SimpleDirectoryWatchService.
     */
    @Test
    public void testExecute() throws Exception {
//        System.out.println("execute");
//        SimpleDirectoryWatchService instance = null;
//        instance.execute();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
