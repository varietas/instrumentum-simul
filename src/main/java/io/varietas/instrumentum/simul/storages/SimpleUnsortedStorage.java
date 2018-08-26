/*
 * Copyright 2016 varietas.io
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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * <h2>UnsortedStorageImpl</h2>
 *
 * This entry represents a container to store all located, annotated entries. Additionally there are a number of useful methods.
 *
 * Attention: Duplex values are only stored one time.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 6/28/2016
 * @param <TYPE> Generic type for the value which is stored.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleUnsortedStorage<TYPE> implements UnsortedStorage<TYPE> {

    private final Set<TYPE> storage;

    public static final UnsortedStorage of() {
        return SimpleUnsortedStorage.of(0);
    }

    public static final UnsortedStorage of(int listSize) {
        return new SimpleUnsortedStorage(new HashSet<>(listSize));
    }

    /**
     * Loads the next entry from the storage. Important is that this entry will be removed from the storage.
     *
     * @return Next entry from the storage.
     */
    @Override
    public final Optional<TYPE> next() {
        if (this.storage.isEmpty()) {
            return Optional.empty();
        }

        TYPE res = this.storage.iterator().next();
        this.storage.remove(res);
        return Optional.of(res);
    }

    /**
     * Stores a entry in the storage. Returns -1 if the entry is not stored otherwise the current number of stored entries will be returned.
     *
     * @param entry Entry to be stored.
     * @return Number of stored entries or -1 for an error.
     */
    @Override
    public final int store(final TYPE entry) {
        if (!this.storage.add(entry)) {
            return this.storage.size();
        } else {
            return -1;
        }
    }

    /**
     * Stores all entries from a given collection in the storage. Returns -1 if the entries are not stored otherwise the current number of stored entries will be returned.
     *
     * @param enties Entries to be stored.
     * @return Number of stored entries or -1 for an error.
     */
    @Override
    public final int storeAll(Collection<TYPE> enties) {
        if (!this.storage.addAll(enties)) {
            return this.storage.size();
        } else {
            return -1;
        }
    }

    @Override
    public final Boolean isEmpty() {
        return this.storage.isEmpty();
    }

    /**
     * All stored entries as list.
     *
     * @return
     */
    @Override
    public final List<TYPE> getStorage() {

        return new ArrayList<>(this.storage);
    }
}
