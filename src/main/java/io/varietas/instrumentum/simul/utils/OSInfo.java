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
import java.util.Locale;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>OSInfo</h2>
 * <p>
 * This class is container for working with OS information easier.
 * <p>
 * Inspired by <a href="https://memorynotfound.com/detect-os-name-version-java/">Diggs Programming - Loves Java</a>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 12/05/2018
 */
@Slf4j
@UtilityClass
public class OSInfo {

    private static final String NOT_AVAILABLE = "N/A";

    public enum OS {
        WINDOWS,
        UNIX,
        POSIX_UNIX,
        MAC,
        OTHER;

        private String version;

        public String getVersion() {
            return this.version;
        }

        protected void setVersion(final String version) {
            this.version = version;
        }
    }

    private static final OS INSTANCE = takeRightOS(System.getProperty("os.name"));

    /**
     * Gets the current OS information as an object which allows the easier access.
     *
     * @return
     */
    public static OS getOs() {
        return INSTANCE;
    }

    protected static OS takeRightOS(final String name) {
        OS current;

        if (StringUtil.isNullOrEmpty(name)) {
            current = OS.OTHER;
            current.setVersion(NOT_AVAILABLE);

            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("'os.name' not found.");
            }

            return current;
        }

        switch (getOsByName(name.toLowerCase(Locale.ENGLISH))) {
            case 1:
                current = OS.WINDOWS;
                break;
            case 2:
                current = OS.UNIX;
                break;
            case 3:
                current = OS.MAC;
                break;
            case 4:
            case 5:
                current = OS.POSIX_UNIX;
                break;
            case 0:
            default:
                current = OS.OTHER;
                break;
        }

        current.setVersion(getOSVersion(current));
        return current;
    }

    private static short getOsByName(final String name) {

        if (name.contains("windows")) {
            return 1;
        }

        if (StringUtil.containsAny(name, "linux", "mpe/ix", "freebsd", "irix", "digital unix", "unix")) {
            return 2;
        }

        if (name.contains("mac os")) {
            return 3;
        }

        if (StringUtil.containsAny(name, "sun os", "sunos", "solaris")) {
            return 4;
        }

        if (StringUtil.containsAny(name, "hp-ux", "aix")) {
            return 5;
        }

        return 0;
    }

    private static String getOSVersion(final OS current) {
        String version = System.getProperty("os.version");

        if (current.equals(OS.OTHER) || StringUtil.isNullOrEmpty(version)) {
            return NOT_AVAILABLE;
        }

        return version;
    }
}
