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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
public class StringUtilTest {

    @Test
    public void testIsNullOrEmpty() {
        Assertions.assertThat(StringUtil.isNullOrEmpty("")).isTrue();
        Assertions.assertThat(StringUtil.isNullOrEmpty(null)).isTrue();
        Assertions.assertThat(StringUtil.isNullOrEmpty("valid")).isFalse();
    }

    @Test
    public void testIsNonNullOrEmpty() {
        Assertions.assertThat(StringUtil.isNonNullOrEmpty("")).isFalse();
        Assertions.assertThat(StringUtil.isNonNullOrEmpty(null)).isFalse();
        Assertions.assertThat(StringUtil.isNonNullOrEmpty("valid")).isTrue();
    }

    @Test
    public void testContainsAnyReturnsTrue() {
        final String one = "This is the test string with fancy 123";

        Assertions.assertThat(StringUtil.containsAny(one, "is")).isTrue();
        Assertions.assertThat(StringUtil.containsAny(one, "not", "other not", "is")).isTrue();
    }

    @Test
    public void testContainsAnyReturnsFalse() {
        final String one = "This is the test string with fancy 123";

        Assertions.assertThat(StringUtil.containsAny(one, "not", "other not")).isFalse();
    }

    @Test
    public void testContainsAnyFailsForNullString() {
        Assertions.assertThatThrownBy(() -> StringUtil.containsAny(null, "not null"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("String cannot be null or empty.");
    }

    @Test
    public void testContainsAnyFailsForEmptyString() {
        Assertions.assertThatThrownBy(() -> StringUtil.containsAny("", "not null"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("String cannot be null or empty.");
    }

    @Test
    public void testContainsAnyFailsForNullMatches() {
        String[] nullMatches = null;
        Assertions.assertThatThrownBy(() -> StringUtil.containsAny("not empty", nullMatches))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Matches cannot be null or empty.");
    }

    @Test
    public void testContainsAnyFailsForEmptyMatches() {
        String[] emptyMatches = {};
        Assertions.assertThatThrownBy(() -> StringUtil.containsAny("not empty", emptyMatches))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Matches cannot be null or empty.");
    }
}
