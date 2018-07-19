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

/**
 * <h2>StringUtil</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 12/21/2017
 */
public class StringUtil {

    public static final boolean isNullOrEmpty(final String string) {
        if (Objects.isNull(string)) {
            return true;
        }

        return string.isEmpty();
    }

    public static final boolean isNonNullOrEmpty(final String string) {
        return !StringUtil.isNullOrEmpty(string);
    }
}
