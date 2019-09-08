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
    public void testGetOs() {

        OSInfo.OS result = OSInfo.getOs();
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSResultsInWindows() {

        OSInfo.OS expected = OSInfo.OS.WINDOWS;
        OSInfo.OS result = OSInfo.takeRightOS("windows 10");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSResultsInUnix() {

        OSInfo.OS expected = OSInfo.OS.UNIX;
        OSInfo.OS result = OSInfo.takeRightOS("linux");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSResultsInMac() {

        OSInfo.OS expected = OSInfo.OS.MAC;
        OSInfo.OS result = OSInfo.takeRightOS("mac os");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSResultsInPosix1() {

        OSInfo.OS expected = OSInfo.OS.POSIX_UNIX;
        OSInfo.OS result = OSInfo.takeRightOS("solaris");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSResultsInPosix2() {

        OSInfo.OS expected = OSInfo.OS.POSIX_UNIX;
        OSInfo.OS result = OSInfo.takeRightOS("aix");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSResultsInOther() {

        OSInfo.OS expected = OSInfo.OS.OTHER;
        OSInfo.OS result = OSInfo.takeRightOS("what ever");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSWithNullResultsOther() {

        OSInfo.OS expected = OSInfo.OS.OTHER;
        OSInfo.OS result = OSInfo.takeRightOS(null);
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }

    /**
     * Test of takeRightOS method, of class OSInfo.
     */
    @Test
    public void testTakeRightOSWithEmptyStringResultsOther() {

        OSInfo.OS expected = OSInfo.OS.OTHER;
        OSInfo.OS result = OSInfo.takeRightOS("");
        Assertions.assertThat(result).isNotNull().isEqualTo(expected);
    }
}
