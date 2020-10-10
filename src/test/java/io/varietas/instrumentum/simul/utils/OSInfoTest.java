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
package io.varietas.instrumentum.simul.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
public class OSInfoTest {

    /**
     * Test of getOs method, of class OSInfo.
     */
    @Test
    public void test_getOs() {

        final OSInfo.OS result = OSInfo.getOs();
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSResultsInWindows() {

        final OSInfo.OS expected = OSInfo.OS.WINDOWS;
        final OSInfo.OS result = OSInfo.takeRightOS("windows 10");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSResultsInUnix() {

        final OSInfo.OS expected = OSInfo.OS.UNIX;
        final OSInfo.OS result = OSInfo.takeRightOS("linux");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSResultsInMac() {

        final OSInfo.OS expected = OSInfo.OS.MAC;
        final OSInfo.OS result = OSInfo.takeRightOS("mac os");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSResultsInPosix1() {

        final OSInfo.OS expected = OSInfo.OS.POSIX_UNIX;
        final OSInfo.OS result = OSInfo.takeRightOS("solaris");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSResultsInPosix2() {

        final OSInfo.OS expected = OSInfo.OS.POSIX_UNIX;
        final OSInfo.OS result = OSInfo.takeRightOS("aix");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSResultsInOther() {

        final OSInfo.OS expected = OSInfo.OS.OTHER;
        final OSInfo.OS result = OSInfo.takeRightOS("what ever");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSWithNullResultsOther() {

        final OSInfo.OS expected = OSInfo.OS.OTHER;
        final OSInfo.OS result = OSInfo.takeRightOS(null);
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void test_takeRightOSWithEmptyStringResultsOther() {

        final OSInfo.OS expected = OSInfo.OS.OTHER;
        final OSInfo.OS result = OSInfo.takeRightOS("");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }
}
