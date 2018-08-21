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
package io.varietas.instrumentum.simul.loaders;

import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.loaders.containers.LoadResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <h2>AbstractLoader</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/17/2017
 * @param <RESULT_TYPE> Generic type of the loaded result.
 */
@RequiredArgsConstructor
public abstract class AbstractLoader<RESULT_TYPE extends LoadResult> implements Loader<RESULT_TYPE> {

    @Getter
    protected final DataSource source;

    public abstract DataSource.Types processedType();

    @Override
    public final RESULT_TYPE load() {
        return this.performLoading();
    }

    protected abstract RESULT_TYPE performLoading();
}
