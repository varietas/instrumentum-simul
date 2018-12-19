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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * <h2>SortedStorageImpl</h2>
 * <p>
 * This entry represents a container to store all entries sorted by codes. Additionally there are a number of useful methods.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 6/30/2016
 * @param <CODE> Generic code type.
 * @param <TYPE> Generic type which is stored.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicSortedStorage<CODE extends Comparable, TYPE> implements SortedStorage<CODE, TYPE> {

    protected final Map<CODE, List<TYPE>> storage;
    protected final List<Function<TYPE, Boolean>> exclusionPredictions = new ArrayList<>();

    @Override
    public final Optional<TYPE> next() {

        final Optional<List<TYPE>> nextList = this.storage.values()
                .stream()
                .filter(list -> !list.isEmpty())
                .findFirst();

        if (!nextList.isPresent()) {
            return Optional.empty();
        }

        TYPE res = nextList.get().get(nextList.get().size() - 1);
        nextList.get().remove(res);

        return Optional.of(res);
    }

    @Override
    public final Optional<TYPE> next(final CODE code) {

        final List<TYPE> nextList = this.storage.get(code);

        if (nextList.isEmpty()) {
            return Optional.empty();
        }

        TYPE res = nextList.get(nextList.size() - 1);
        nextList.remove(res);

        return Optional.of(res);
    }

    /**
     * Stores a entry in the storage. Returns -1 if the entry is not stored otherwise the current number of stored entries will be returned.
     *
     * @param entry Entry to be stored.
     * @param code  Annotation type code where the entry should be stored for.
     *
     * @return Number of stored entries or -1 if an exclusion prediction prevents the storing.
     */
    @Override
    public final int store(final TYPE entry, final CODE code) {

        if (!this.storage.containsKey(code)) {
            throw new NullPointerException("Value(s) cannot be stored for key " + String.valueOf(code) + ".");
        }

        if (this.exclusionPredictions.stream().anyMatch(predicate -> predicate.apply(entry))) {
            return -1;
        }

        this.storage.get(code).add(entry);

        return this.storage.get(code).size();
    }

    /**
     * Stores all entries from a given collection in the storage. Returns -1 if the entries are not stored otherwise the current number of stored entries will be returned.
     *
     * @param entries Entries to be stored.
     * @param code    Annotation type code where the entry should be stored for.
     *
     * @return Number of stored entries or -1 if an exclusion prediction prevents the storing
     */
    @Override
    public final int storeAll(Collection<TYPE> entries, final CODE code) {

        for (TYPE entry : entries) {
            if (this.store(entry, code) == -1) {
                return -1;
            }
        }

        return this.storage.get(code).size();
    }

    @Override
    public final Map<CODE, List<TYPE>> getStorage() {
        return this.storage;
    }

    @Override
    public final Boolean isEmpty(CODE code) {
        return this.storage.get(code).isEmpty();
    }

    @Override
    public final Boolean isEmpty() {

        return !this.storage.entrySet().stream()
                .filter(entry -> (!entry.getValue().isEmpty()))
                .findFirst()
                .isPresent();
    }

    @Override
    public final SortedStorage addExclusion(final Function<TYPE, Boolean> exclusion) {
        this.exclusionPredictions.add(exclusion);
        return this;
    }

    /**
     * Creates an instance of a simple sorted storage by a given set of key. Predictions for exclusions can be added by the #addExclusion(...) method.
     *
     * @param <CODE>
     * @param codes
     *
     * @return
     */
    public static <CODE extends Comparable> SortedStorage of(final CODE... codes) {

        if (Objects.isNull(codes) || (codes.length == 0)) {
            throw new NullPointerException("Sorted storages requires codes for sorting entities.");
        }

        final Map storage = Arrays.asList(codes)
                .stream()
                .collect(Collectors.toMap(code -> code, code -> new ArrayList<>()));

        return new BasicSortedStorage(storage);
    }
}
