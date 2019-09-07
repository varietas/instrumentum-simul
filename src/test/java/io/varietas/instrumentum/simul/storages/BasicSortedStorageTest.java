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

import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Michael Rhöse
 */
public class BasicSortedStorageTest {

    private static final Integer[] CODES = {1, 2, 3, 4};

    private SortedStorage<Integer, Integer> instance;

    @Before
    public void setUp() {
        this.instance = BasicSortedStorage.of(CODES);
    }

    @Test
    public void testFactoryMethodFails() {

        Assertions.assertThatThrownBy(() -> BasicSortedStorage.of(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Sorted storages requires codes for sorting entities.");

        Assertions.assertThatThrownBy(() -> {
            Integer[] args = {};
            BasicSortedStorage.of(args);
        })
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Sorted storages requires codes for sorting entities.");
    }

    /**
     * Test of next method, of class BasicSortedStorage.
     */
    @Test
    public void testNext_0args() {

        this.instance.store(1, 1);

        Assertions.assertThat(instance.next()).isPresent().hasValue(1);
        Assertions.assertThat(instance.next()).isNotPresent();
    }

    /**
     * Test of next method, of class BasicSortedStorage.
     */
    @Test
    public void testNext_GenericType() {

        this.instance.store(2, 1);
        this.instance.store(3, 2);

        Assertions.assertThat(instance.next(1)).isPresent().hasValue(2);
        Assertions.assertThat(instance.next(1)).isNotPresent();
        Assertions.assertThat(instance.next(2)).isPresent().hasValue(3);
        Assertions.assertThat(instance.next(2)).isNotPresent();
    }

    /**
     * Test of store method, of class BasicSortedStorage.
     */
    @Test
    public void testStore() {

        Assertions.assertThat(this.instance.store(1, 1)).isEqualTo(1);
        Assertions.assertThat(this.instance.store(2, 1)).isEqualTo(2);
        Assertions.assertThat(this.instance.store(3, 1)).isEqualTo(3);
        Assertions.assertThat(this.instance.store(1, 2)).isEqualTo(1);
    }

    /**
     * Test of store method, of class BasicSortedStorage.
     */
    @Test
    public void testStore_Fails() {

        Assertions.assertThatThrownBy(() -> this.instance.store(1, 5))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Value(s) cannot be stored for key 5.");
    }

    /**
     * Test of storeAll method, of class BasicSortedStorage.
     */
    @Test
    public void testStoreAll() {

        Assertions.assertThat(this.instance.storeAll(Arrays.asList(CODES), 1)).isEqualTo(CODES.length);
    }

    /**
     * Test of storeAll method, of class BasicSortedStorage.
     */
    @Test
    public void testStoreAll_Fails() {

        Assertions.assertThatThrownBy(() -> this.instance.storeAll(Arrays.asList(CODES), 5))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Value(s) cannot be stored for key 5.");
    }

    /**
     * Test of getStorage method, of class BasicSortedStorage.
     */
    @Test
    public void testGetStorage() {

        Assertions.assertThat(this.instance.getStorage())
                .hasSize(4)
                .containsKeys(CODES);
    }

    /**
     * Test of isEmpty method, of class BasicSortedStorage.
     */
    @Test
    public void testIsEmpty_GenericType() {

        Assertions.assertThat(this.instance.isEmpty(1)).isTrue();
        Assertions.assertThat(this.instance.isEmpty(2)).isTrue();
        Assertions.assertThat(this.instance.isEmpty(3)).isTrue();
        Assertions.assertThat(this.instance.isEmpty(4)).isTrue();
        this.instance.store(2, 1);
        Assertions.assertThat(this.instance.isEmpty(1)).isFalse();
        Assertions.assertThat(this.instance.isEmpty(2)).isTrue();
        Assertions.assertThat(this.instance.isEmpty(3)).isTrue();
        Assertions.assertThat(this.instance.isEmpty(4)).isTrue();
    }

    /**
     * Test of isEmpty method, of class BasicSortedStorage.
     */
    @Test
    public void testIsEmpty_0args() {

        Assertions.assertThat(this.instance.isEmpty()).isTrue();
        this.instance.store(2, 1);
        Assertions.assertThat(this.instance.isEmpty()).isFalse();
    }

    /**
     * Test of addExclusion method, of class BasicSortedStorage.
     */
    @Test
    public void testAddExclusion() {

        this.instance.addExclusion((entry) -> entry == 0);
        Assertions.assertThat(this.instance.store(0, 1)).isEqualTo(-1);
        Assertions.assertThat(this.instance.store(1, 1)).isEqualTo(1);

        Assertions.assertThat(this.instance.storeAll(Arrays.asList(0, 1), 1)).isEqualTo(-1);

        this.instance.addExclusion((entry) -> entry < 3);
        this.instance.addExclusion((entry) -> (entry % 2) != 0);
        Assertions.assertThat(this.instance.store(3, 1)).isEqualTo(-1);
        Assertions.assertThat(this.instance.store(2, 1)).isEqualTo(-1);
        Assertions.assertThat(this.instance.store(4, 1)).isEqualTo(2);
    }

    @Test
    public void testStoreNullEntryFails() {

        Assertions.assertThatThrownBy(() -> this.instance.store(null, 0))
                .hasMessage("Value(s) cannot be stored for key 0.")
                .isInstanceOf(NullPointerException.class);
    }
}
