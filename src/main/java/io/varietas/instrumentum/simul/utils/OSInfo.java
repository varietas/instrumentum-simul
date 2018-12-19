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
@UtilityClass
public class OSInfo {

    public enum OS {
        WINDOWS,
        UNIX,
        POSIX_UNIX,
        MAC,
        OTHER;

        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    private static OS INSTANCE;

    static {
        try {
            INSTANCE = takeRightOS(System.getProperty("os.name"));
        } catch (IOException ex) {
            INSTANCE = OS.OTHER;
        }
    }

    /**
     * Gets the current OS information as an object which allows the easier access.
     *
     * @return
     */
    public static OS getOs() {
        return INSTANCE;
    }

    protected static OS takeRightOS(final String name) throws IOException {
        if (StringUtil.isNullOrEmpty(name)) {
            throw new IOException("'os.name' not found.");
        }

        final String osName = name.toLowerCase(Locale.ENGLISH);

        switch (getOsByName(osName)) {
            case 1:
                return OS.WINDOWS;
            case 2:
                return OS.UNIX;
            case 3:
                return OS.MAC;
            case 4:
            case 5:
                return OS.POSIX_UNIX;
            case 0:
            default:
                return OS.OTHER;
        }
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
}
