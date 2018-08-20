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
package io.varietas.instrumentum.simul.loaders.containers.impl;

import io.varietas.instrumentum.simul.loaders.containers.LoadResult;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * <h2>AbstractLoadResult</h2>
 *
 * The abstract load result contains the basic properties and functionality of load results.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 05/16/2018
 * @param <TYPE> Generic type of the loading result.
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(makeFinal = true)
public abstract class AbstractLoadResult<TYPE> implements LoadResult<TYPE> {

    @Getter
    protected int statusCode;

    @Getter
    @NonNull
    protected String message;

    private Object value;

    @Override
    public Optional<TYPE> mappedValue() {

        if (Objects.isNull(this.value)) {
            return Optional.empty();
        }

        return Optional.of((TYPE) this.value);
    }
}
