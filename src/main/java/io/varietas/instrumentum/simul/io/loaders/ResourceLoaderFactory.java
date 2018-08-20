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
package io.varietas.instrumentum.simul.io.loaders;

import io.varietas.instrumentum.simul.loaders.Loader;
import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.io.containers.FileLoadResult;
import io.varietas.instrumentum.simul.loaders.LoaderFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

/**
 * <h2>ResourceLoaderFactory</h2>
 *
 * The resource loader factory is the loader factory implementation for resource loading. It is used to mask the instantiation of a specific file loader.
 *
 * @see LoaderFactory
 * @see FileLoadResult
 * @see io.varietas.instrumentum.simul.io.ResourceLoader
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/17/2017
 */
@RequiredArgsConstructor(staticName = "of")
public class ResourceLoaderFactory implements LoaderFactory<FileLoadResult> {

    private final DataSource source;

    @Override
    public FileLoadResult load() {

        if (Objects.isNull(this.source)) {
            throw new NullPointerException("Source is required and must be configured before calling #load().");
        }

        Loader<FileLoadResult> loader;

        switch (source.getType()) {
            case FTP:
                loader = FTPFileLoader.of(this.source);
                break;
            case HTTP:
                loader = HTTPFileLoader.of(this.source);
                break;
            default:
                ///< DataSource.Types.DIR is default.
                loader = DirFileLoader.of(this.source);
                break;
        }

        return loader.load();
    }
}
