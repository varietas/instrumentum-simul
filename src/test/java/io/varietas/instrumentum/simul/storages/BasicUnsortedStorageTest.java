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
package io.varietas.instrumentum.simul.storages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BasicUnsortedStorageTest {

    /**
     * Test of of method, of class BasicUnsortedStorage.
     */
    @Test
    public void testOf_0args() {
        final UnsortedStorage result = BasicUnsortedStorage.of();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStorage()).isEmpty();
        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    /**
     * Test of of method, of class BasicUnsortedStorage.
     */
    @Test
    public void testOf_int() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStorage()).isEmpty();
        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    /**
     * Test of next method, of class BasicUnsortedStorage.
     */
    @Test
    public void testNext() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);

        Assertions.assertThat(result.next().isPresent()).isFalse();
        result.store(1);
        Assertions.assertThat(result.next().isPresent()).isTrue();
        Assertions.assertThat(result.next().isPresent()).isFalse();
    }

    /**
     * Test of store method, of class BasicUnsortedStorage.
     */
    @Test
    public void testStore() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);

        Assertions.assertThat(result.next().isPresent()).isFalse();
        result.store(1);
        Assertions.assertThat(result.next().isPresent()).isTrue();
    }

    /**
     * Test of store method, of class BasicUnsortedStorage.
     */
    @Test
    public void testStoreNullFails() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);
        Assertions.assertThatThrownBy(() -> result.store(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Storage cannot handle null objetcs.");
    }

    /**
     * Test of storeAll method, of class BasicUnsortedStorage.
     */
    @Test
    public void testStoreAll() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);

        Assertions.assertThat(result.next().isPresent()).isFalse();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        result.storeAll(integers);

        Assertions.assertThat(result.getStorage()).hasSize(3);
    }

    /**
     * Test of storeAll method, of class BasicUnsortedStorage.
     */
    @Test
    public void testStoreAll_OnlyOneTime() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);

        Assertions.assertThat(result.next().isPresent()).isFalse();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(1);
        integers.add(1);
        result.storeAll(integers);

        Assertions.assertThat(result.getStorage()).hasSize(1);
    }

    /**
     * Test of storeAll method, of class BasicUnsortedStorage.
     */
    @Test
    public void testStoreAllFails() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);
        Assertions.assertThatThrownBy(() -> result.storeAll(null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Test of storeAll method, of class BasicUnsortedStorage.
     */
    @Test
    public void testStoreAllFailsForEmpty() {
        final UnsortedStorage result = BasicUnsortedStorage.of(1);
        Assertions.assertThat(result.storeAll(Collections.EMPTY_LIST)).isEqualTo(-1);
    }
}
