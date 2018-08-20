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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author Michael Rhöse
 */
@RunWith(JUnit4.class)
public class StringUtilTest {

    /**
     * Test of isNullOrEmpty method, of class StringUtil.
     */
    @Test
    public void testIsNullOrEmpty() {
        Assertions.assertThat(StringUtil.isNullOrEmpty("")).isTrue();
        Assertions.assertThat(StringUtil.isNullOrEmpty(null)).isTrue();
        Assertions.assertThat(StringUtil.isNullOrEmpty("valid")).isFalse();
    }

    /**
     * Test of isNonNullOrEmpty method, of class StringUtil.
     */
    @Test
    public void testIsNonNullOrEmpty() {
        Assertions.assertThat(StringUtil.isNonNullOrEmpty("")).isFalse();
        Assertions.assertThat(StringUtil.isNonNullOrEmpty(null)).isFalse();
        Assertions.assertThat(StringUtil.isNonNullOrEmpty("valid")).isTrue();
    }

}
