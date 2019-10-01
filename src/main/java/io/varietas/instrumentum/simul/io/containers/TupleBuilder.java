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

    /**
     * Factory method for creation of a three-tuple container with three parameters.
     *
     * @param <VALUE1> Generic type of value one
     * @param <VALUE2> Generic type of value two
     * @param <VALUE3> Generic type of value three
     * @param v1       Given value one for the tuple
     * @param v2       Given value two for the tuple
     * @param v3       Given value three for the tuple
     *
     * @return A valid {@link Tuple3} container with three values
     */
    public static <VALUE1, VALUE2, VALUE3> Tuple3<VALUE1, VALUE2, VALUE3> of(final VALUE1 v1, final VALUE2 v2, final VALUE3 v3) {
        return new Tuple3<>(v1, v2, v3);
    }

    /**
     * Factory method for creation of a three-tuple container with three parameters.
     *
     * @param <VALUE1> Generic type of value one
     * @param <VALUE2> Generic type of value two
     * @param <VALUE3> Generic type of value three
     * @param <VALUE4> Generic type of value four
     * @param v1       Given value one for the tuple
     * @param v2       Given value two for the tuple
     * @param v3       Given value three for the tuple
     * @param v4       Given value four for the tuple
     *
     * @return A valid {@link Tuple4} container with four values
     */
    public static <VALUE1, VALUE2, VALUE3, VALUE4> Tuple4<VALUE1, VALUE2, VALUE3, VALUE4> of(final VALUE1 v1, final VALUE2 v2, final VALUE3 v3, final VALUE4 v4) {
        return new Tuple4<>(v1, v2, v3, v4);
    }
}
