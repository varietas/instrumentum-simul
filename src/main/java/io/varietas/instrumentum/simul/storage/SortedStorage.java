/*
 * Copyright 2016 Michael Rhöse.
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
package io.varietas.instrumentum.simul.storage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java8.util.Optional;

/**
 * <h2>SortedStorage</h2>
 *
 * @author Michael Rhöse
 * @since Fr, Jul 1, 2016
 */
public interface SortedStorage<Code extends Serializable, Type> extends Storage<Type> {

    /**
     * Searches for a given entry all available entries. If is no class available an empty list will returned.
     *
     * @param entry Equal entries searched for.
     * @return
     */
    public List<Type> findByTypes(final Type entry);

    /**
     * Searches for a given entry and {@link ClassMetaDataExtractionUtils.AnnotationCodes} all available entries. If is no class available an empty list will returned.
     *
     * @param entry Equal entries searched for.
     * @param code Annotation code.
     * @return
     */
    public List<Type> findByTypesAndAnnotationCode(final Type entry, final Code code);

    /**
     * Stores a class in the storage. Returns -1 if the class is not stored otherwise the current number of stored classes will be returned.
     *
     * @param entry Class to be stored.
     * @param code Annotation type code where the class should be stored for.
     * @return Number of stored classes or -1 for an error.
     */
    public int store(final Type entry, final Code code);

    /**
     * Stores all classes from a given collection in the storage. Returns -1 if the classes are not stored otherwise the current number of stored classes will be returned.
     *
     * @param entries Classes to be stored.
     * @param code Annotation type code where the class should be stored for.
     * @return Number of stored classes or -1 for an error.
     */
    public int storeAll(Collection<Type> entries, final Code code);

    public Optional<Type> next(final Code code);

    /**
     * All stored entries as list.
     *
     * @return
     */
    public Map<Code, List<Type>> getStorage();

    /**
     *
     * @return
     */
    public Boolean isEmpty(Code code);
}
