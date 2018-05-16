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
import java.util.Objects;
import lombok.Setter;
import lombok.experimental.Accessors;
import io.varietas.instrumentum.simul.io.ResourceLoader;

/**
 * <h2>ResourceLoaderImpl</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0, 11/17/2017
 */
public class ResourceLoaderImpl implements ResourceLoader {

    @Setter
    @Accessors(fluent = true)
    private DataSource source;

    @Override
    public FileLoadResult load() {
        Loader<FileLoadResult> loader;

        switch (source.getType()) {
            case FTP:
                loader = new FTPFileLoader(this.source);
                break;
            case HTTP:
                loader = new HTTPFileLoader(this.source);
                break;
            default:
                ///< DataSource.Types.DIR is default.
                loader = new DirFileLoader(this.source);
                break;
        }

        if (Objects.isNull(loader)) {
            throw new NullPointerException("Couldn't find actual loader for source type " + this.source.getType() + ".");
        }

        return loader.load();
    }
}
