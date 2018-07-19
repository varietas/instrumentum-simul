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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <h2>SortedStorage</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 7/1/2016
 * @param <CODE> Generic code type.
 * @param <TYPE> Generic type for the value which is stored.
 */
public interface SortedStorage<CODE extends Serializable, TYPE> extends Storage<TYPE> {

    /**
     * Stores a class in the storage. Returns -1 if the class is not stored otherwise the current number of stored classes will be returned.
     *
     * @param entry Class to be stored.
     * @param code  Annotation type code where the class should be stored for.
     *
     * @return Number of stored entries or -1 for an error.
     */
    public int store(final TYPE entry, final CODE code);

    /**
     * Stores all classes from a given collection in the storage. Returns -1 if the classes are not stored otherwise the current number of stored classes will be returned.
     *
     * @param entries Classes to be stored.
     * @param code    Annotation type code where the class should be stored for.
     *
     * @return Number of stored entries or -1 for an error.
     */
    public int storeAll(Collection<TYPE> entries, final CODE code);

    /**
     * Gets the next entry for a code.
     *
     * @param code Code where the next entry has to be loaded.
     *
     * @return Next entry.
     */
    public Optional<TYPE> next(final CODE code);

    /**
     * All stored entries as list.
     *
     * @return
     */
    public Map<CODE, List<TYPE>> getStorage();

    /**
     * Checks if a list of stored entries for a code is empty.
     *
     * @param code Category code.
     *
     * @return True if list is empty, otherwise false.
     */
    public Boolean isEmpty(CODE code);
}
