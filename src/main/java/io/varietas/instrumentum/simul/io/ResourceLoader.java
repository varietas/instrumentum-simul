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
package io.varietas.instrumentum.simul.io;

import io.varietas.instrumentum.simul.loaders.Loader;
import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.io.containers.FileLoadResult;
import io.varietas.instrumentum.simul.io.loaders.ResourceLoaderFactory;
import io.varietas.instrumentum.simul.loaders.LoaderFactory;

/**
 * <h2>ResourceLoader</h2>
 * <p>
 * The resource loader allows an easy access to the resource loading subsystem of simul. The usage is quite simple:
 * <p>
 * <
 * pre>
 * <code>
 *   DataSource source;
 *   FileLoadResult result = ResourceLoader.of(source).load();
 * </code>
 * </pre>
 *
 * @see FileLoadResult
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/17/2017
 */
public interface ResourceLoader extends Loader<FileLoadResult> {

    static LoaderFactory of(final DataSource source) {
        return ResourceLoaderFactory.of(source);
    }
}
