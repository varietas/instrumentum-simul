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

import io.varietas.instrumentum.simul.io.containers.DataSource;
import io.varietas.instrumentum.simul.io.containers.FileLoadResult;
import io.varietas.instrumentum.simul.loaders.AbstractLoader;
import io.varietas.instrumentum.simul.loaders.Loader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * <h2>DirFileLoader</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/19/2017
 */
@Slf4j
final class DirFileLoader extends AbstractLoader<FileLoadResult<?>> {

    private DirFileLoader(final DataSource source) {
        super(source);
    }

    public static Loader<FileLoadResult<?>> of(final DataSource source) {
        return new DirFileLoader(source);
    }

    @Override
    public DataSource.Types processedType() {
        return DataSource.Types.DIR;
    }

    @Override
    protected FileLoadResult<?> performLoading() {

        FileLoadResult.FileLoadResultBuilder<byte[]> resultBuilder = FileLoadResult.of();
        InputStream stream = null;
        try {
            Optional<Path> target = Files.list(Paths.get(this.source.getPath()))
                    .filter(path -> path.getFileName().endsWith(this.source.getTarget()))
                    .findFirst();

            if (!target.isPresent()) {
                throw new NullPointerException("Couldn't find file '" + this.source.getTarget() + "' on repository '" + this.source.getId() + "'.");
            }

            stream = Files.newInputStream(target.get());

            byte[] file = IOUtils.toByteArray(stream);

            resultBuilder
                    .name(this.source.getTarget())
                    .statusCode(200)
                    .message("OK")
                    .value(file);

        } catch (IOException | NullPointerException ex) {
            resultBuilder
                    .name(this.source.getTarget())
                    .statusCode(500)
                    .message("FAILED: " + ex.getLocalizedMessage());
        } finally {
            try {
                if (Objects.nonNull(stream)) {
                    stream.close();
                }
            } catch (IOException ex) {
                resultBuilder
                        .name(this.source.getTarget())
                        .statusCode(500)
                        .message("FAILED: Couldn't close connection to ftp server. " + ex.getLocalizedMessage());
            }
        }

        return resultBuilder.build();
    }
}
