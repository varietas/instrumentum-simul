/*
 * Copyright 2017 Michael Rhöse.
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

import java.util.Objects;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

/**
 * <h2>StringUtil</h2>
 * <p>
 * A collection of useful methods to handle strings in a readable way.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 12/21/2017
 */
@UtilityClass
public class StringUtil {

    /**
     * Checks if a given string is NULL or is empty.
     *
     * @param string Given string to check.
     *
     * @return True if the given string is null or empty, otherwise false.
     */
    public static final boolean isBlank(final String string) {
        return Objects.isNull(string) || string.isEmpty();
    }

    public static final boolean nonBlank(final String string) {
        return !Objects.isNull(string) && !string.isEmpty();
    }

    /**
     * Checks if one contains one of the others. The method returns true for the first match, otherwise false.
     *
     * @param one    The string which is tested for matching any other string.
     * @param others List of strings that potentially match the first string.
     *
     * @throws NullPointerException Thrown if one or both parameters are null or empty.
     * @return True if the first element of the list is contained in the first parameter.
     */
    public boolean containsAny(final String one, final String... others) {
        if (isBlank(one)) {
            throw new NullPointerException("String cannot be null or empty.");
        }

        if (Objects.isNull(others) || others.length == 0) {
            throw new NullPointerException("Matches cannot be null or empty.");
        }

        return Stream.of(others).anyMatch(other -> one.contains(other));
    }
}
