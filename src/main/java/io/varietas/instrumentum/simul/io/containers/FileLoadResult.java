/*
 * Copyright 2017 Michael Rhöse.
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

import io.varietas.instrumentum.simul.loaders.containers.impl.AbstractLoadResult;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <h2>FileLoadResult</h2>
 *
 * The file load result is used for file loader from different locations.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 11/17/2017
 * @param <TYPE> Generic type of the loading result.
 */
public class FileLoadResult<TYPE> extends AbstractLoadResult<TYPE> {

    @Setter
    @Accessors(fluent = true)
    private String name;

    public String getName() {
        return name;
    }
}