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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
@SuppressWarnings("rawtypes")
public class TupleBuilderTest {

    /**
     * Test of of method, of class TupleBuilder.
     */
    @Test
    public void testOf_tuple2_oneParam() {
        Integer val1 = 1;
        Tuple2 result = TupleBuilder.of(val1);
        Assertions.assertThat(result.getV1()).isEqualTo(1);
        Assertions.assertThat(result.getV2()).isNull();
    }

    /**
     * Test of of method, of class TupleBuilder.
     */
    @Test
    public void testOf_tuple2() {
        Integer val1 = 1;
        Integer val2 = 2;
        Tuple2 result = TupleBuilder.of(val1, val2);
        Assertions.assertThat(result.getV1()).isEqualTo(1);
        Assertions.assertThat(result.getV2()).isEqualTo(2);
    }

    /**
     * Test of of method, of class TupleBuilder.
     */
    @Test
    public void testOf_tuple3() {
        Integer val1 = 1;
        Integer val2 = 2;
        Integer val3 = 3;
        Tuple3 result = TupleBuilder.of(val1, val2, val3);
        Assertions.assertThat(result.getV1()).isEqualTo(1);
        Assertions.assertThat(result.getV2()).isEqualTo(2);
        Assertions.assertThat(result.getV3()).isEqualTo(3);
    }

    /**
     * Test of of method, of class TupleBuilder.
     */
    @Test
    public void testOf_tuple4() {
        Integer val1 = 1;
        Integer val2 = 2;
        Integer val3 = 3;
        Integer val4 = 4;
        Tuple4 result = TupleBuilder.of(val1, val2, val3, val4);
        Assertions.assertThat(result.getV1()).isEqualTo(1);
        Assertions.assertThat(result.getV2()).isEqualTo(2);
        Assertions.assertThat(result.getV3()).isEqualTo(3);
        Assertions.assertThat(result.getV4()).isEqualTo(4);
    }
}
