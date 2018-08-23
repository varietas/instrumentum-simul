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
package io.varietas.instrumentum.simul.io.container;

import lombok.NoArgsConstructor;

/**
 * <h2>TupleBuilder</h2>
 * <p>
 * {description}
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/23/2018
 */
@NoArgsConstructor
public class TupleBuilder {

    public static <VALUE1, VALUE2> Tuple2<VALUE1, VALUE2> of(final VALUE1 v1) {
        return new Tuple2<>(v1, null);
    }

    public static <VALUE1, VALUE2> Tuple2<VALUE1, VALUE2> of(final VALUE1 v1, final VALUE2 v2) {
        return new Tuple2<>(v1, v2);
    }
}
