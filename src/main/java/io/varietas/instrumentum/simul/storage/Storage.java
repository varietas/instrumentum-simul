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
package io.varietas.instrumentum.simul.storage;

import java.util.Optional;

/**
 * <h2>Storage</h2>
 *
 * @param <Type> Generic type for the storage.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 7/1/2016
 */
public interface Storage<Type> {

    /**
     * Loads the next entry from the storage. Important is that this entry will be removed from the storage.
     *
     * @return Next entry from the storage.
     */
    public Optional<Type> next();

    /**
     *
     * @return
     */
    public Boolean isEmpty();
}
