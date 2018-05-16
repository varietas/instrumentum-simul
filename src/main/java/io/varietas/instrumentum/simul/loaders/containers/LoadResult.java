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
package io.varietas.instrumentum.simul.loaders.containers;

import java.util.Optional;

/**
 * <h2>LoadResult</h2>
 *
 * The load result is a container to store all important and required information of a loading process and its result.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 05/16/2018
 * @param <TYPE> Generic type of the loading result.
 */
public interface LoadResult<TYPE> {

    /**
     * The status code signals the status of the loading at the end. The codes are inspired by the HTTP status codes and should be used in the same way.
     *
     * @return Status code about the loading process.
     */
    int getStatusCode();

    /**
     * A clear message about the loading process. The messages are inspired by the HTTP status messages.
     *
     * @return Short and clear message about the loading process.
     */
    String getMessage();

    /**
     * The mapped response of the loading process. This method prevents null pointer errors by returning of an optional object.
     *
     * @return The result of the loading process or an Optional#emty;
     */
    Optional<TYPE> mappedValue();
}
