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

import io.varietas.instrumentum.simul.loaders.containers.LoadResult;

/**
 * <h2>Loader</h2>
 *
 * The loader API is used to abstract the loading of anything from anywhere. Simul uses the loader API for handling the resources from different locations. But it is also possible to use it for
 * explicit objects.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/17/2017
 * @param <RESULT_TYPE> Generic type of the loaded result.
 */
public interface Loader<RESULT_TYPE extends LoadResult> {

    /**
     * Performs the loading process and returns them.
     *
     * @return Returns a loading result object that contains all informations.
     */
    RESULT_TYPE load();
}
