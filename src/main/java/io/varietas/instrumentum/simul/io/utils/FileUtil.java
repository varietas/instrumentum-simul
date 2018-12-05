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
package io.varietas.instrumentum.simul.io.utils;

import io.varietas.instrumentum.simul.utils.OSInfo;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Set;
import lombok.experimental.UtilityClass;

/**
 * <h2>FileUtil</h2>
 * <p>
 * A collection of useful methods that make the work with paths easier.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/23/2018
 */
@UtilityClass
public class FileUtil {

    /**
     * Utility method to generate a global matcher for a given expression. The matcher is used to find files/directory that match.
     *
     * @param globPattern Pattern which has to be used for generating a path matcher.
     *
     * @return Global path matcher for a given expression.
     */
    public static PathMatcher matcherForGlobExpression(final String globPattern) {

        String used = globPattern;
        if (OSInfo.getOs().equals(OSInfo.OS.WINDOWS)) {
            used = used.replace("\\", "\\\\");
        }
        return FileSystems.getDefault().getPathMatcher("glob:" + used);
    }

    /**
     * Simply checks if a given path matches a given pattern.
     *
     * @param input   Given path to check.
     * @param pattern Pattern that is used to check a given path.
     *
     * @return True if the path matches the pattern, otherwise false.
     */
    public static boolean matches(final Path input, final PathMatcher pattern) {
        return pattern.matches(input);
    }

    /**
     * Simply checks if a given path matches any given pattern.
     *
     * @param input    Given path to check.
     * @param patterns Patterns that is used to check a given path.
     *
     * @return True if the path matches any pattern, otherwise false.
     */
    public static boolean matchesAny(final Path input, final Set<PathMatcher> patterns) {
        return patterns.stream().anyMatch((pattern) -> (matches(input, pattern)));
    }
}
