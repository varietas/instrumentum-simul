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
package io.varietas.instrumentum.simul.io.containers;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * <h2>Tuple4</h2>
 * <p>
 * {The tuple is a useful container to map an object or value to others.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/23/2018
 * @param <VALUE1> Generic type of value 1.
 * @param <VALUE2> Generic type of value 2.
 * @param <VALUE3> Generic type of value 3.
 * @param <VALUE4> Generic type of value 4.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 09/26/2019
 */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tuple4<VALUE1, VALUE2, VALUE3, VALUE4> extends Tuple3<VALUE1, VALUE2, VALUE3> {

    public Tuple4(final VALUE1 v1, final VALUE2 v2, final VALUE3 v3, final VALUE4 v4) {
        super(v1, v2, v3);
        this.v4 = v4;
    }

    VALUE4 v4;
}
