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

import java.util.Collection;
import java.util.List;

/**
 * <h2>UnsortedStorage</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 7/3/2016
 * @param <TYPE> Generic type for the value which is stored.
 */
public interface UnsortedStorage<TYPE> extends Storage<TYPE> {

    /**
     * Stores a class in the storage. Returns -1 if the class is not stored otherwise the current number of stored classes will be returned.
     *
     * @param entry Entry to be stored.
     *
     * @return Number of stored entries or -1 for an error.
     */
    public int store(final TYPE entry);

    /**
     * Stores all classes from a given collection in the storage. Returns -1 if the classes are not stored otherwise the current number of stored classes will be returned.
     *
     * @param entries Entries to be stored.
     *
     * @return Number of stored entries or -1 for an error.
     */
    public int storeAll(Collection<TYPE> entries);

    /**
     * All stored entries as list.
     *
     * @return
     */
    public List<TYPE> getStorage();
}
