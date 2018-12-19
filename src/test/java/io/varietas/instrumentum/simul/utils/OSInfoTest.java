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
package io.varietas.instrumentum.simul.utils;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author Michael Rhöse
 */
@RunWith(JUnit4.class)
public class OSInfoTest {

    private void assertThatAllNamesLeadsToOS(final OSInfo.OS expected, final String... names) {
        Stream.of(names).forEach(name -> assertThatNameLeadsToOs(() -> {
            try {
                return OSInfo.takeRightOS(name).equals(expected);
            } catch (IOException ex) {
                throw new RuntimeException("No OS found for '{}'.", ex);
            }
        }));
    }

    private void assertThatNameLeadsToOs(final Supplier<Boolean> function) {
        Assertions.assertThat(function.get()).isTrue();
    }

    /**
     * Test of getOs method, of class OSInfo.
     */
    @Test
    public void testGetOs() {

        assertThatNameLeadsToOs(() -> OSInfo.getOs().equals(OSInfo.OS.WINDOWS));
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOS() throws Exception {

        assertThatAllNamesLeadsToOS(OSInfo.OS.WINDOWS, "windows");
        assertThatAllNamesLeadsToOS(OSInfo.OS.UNIX, "linux", "mpe/ix", "freebsd", "irix", "digital unix", "unix");
        assertThatAllNamesLeadsToOS(OSInfo.OS.MAC, "mac os");
        assertThatAllNamesLeadsToOS(OSInfo.OS.POSIX_UNIX, "sun os", "sunos", "solaris", "hp-ux", "aix");
        assertThatAllNamesLeadsToOS(OSInfo.OS.OTHER, "any thing else");
    }

    @Test
    public void testExceptionForEmptyName() {
        Assertions.assertThatThrownBy(() -> OSInfo.takeRightOS(null))
                .isInstanceOf(IOException.class)
                .hasMessage("'os.name' not found.");
    }
}
