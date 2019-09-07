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
package io.varietas.instrumentum.simul.io.containers;

import lombok.NoArgsConstructor;

/**
 * <h2>TupleBuilder</h2>
 * <p>
 * The tuple builder represents a facade-like mechanism for creating n-tuple containers.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/23/2018
 */
@NoArgsConstructor
public class TupleBuilder {

    /**
     * Factory method for creation of a two-tuple container with one parameter. The second parameter will be null for lazy initializing.
     *
     * @param <VALUE1> Generic type of value one
     * @param <VALUE2> Generic type of value two
     * @param v1       Given value one for the tuple
     *
     * @return A valid {@link Tuple2} container with one value
     */
    public static <VALUE1, VALUE2> Tuple2<VALUE1, VALUE2> of(final VALUE1 v1) {
        return new Tuple2<>(v1, null);
    }

    /**
     * Factory method for creation of a two-tuple container with two parameters.
     *
     * @param <VALUE1> Generic type of value one
     * @param <VALUE2> Generic type of value two
     * @param v1       Given value one for the tuple
     * @param v2       Given value two for the tuple
     *
     * @return A valid {@link Tuple2} container with two values
     */
    public static <VALUE1, VALUE2> Tuple2<VALUE1, VALUE2> of(final VALUE1 v1, final VALUE2 v2) {
        return new Tuple2<>(v1, v2);
    }
}
