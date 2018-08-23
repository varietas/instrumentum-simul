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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

/**
 * <h2>Tuple2</h2>
 * <p>
 * The tuple is a useful container to map an object or value to another one.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/23/2018
 * @param <VALUE1> Generic type of value 1.
 * @param <VALUE2> Generic type of value 2.
 */
@Value
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Tuple2<VALUE1, VALUE2> {

    VALUE1 v1;

    @Wither
    VALUE2 v2;
}
