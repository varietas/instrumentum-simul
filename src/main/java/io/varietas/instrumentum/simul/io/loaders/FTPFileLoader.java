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
import io.varietas.instrumentum.simul.utils.StringUtil;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * <h2>FTPFileLoader</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/17/2017
 */
final class FTPFileLoader extends AbstractLoader<FileLoadResult<?>> {

    private FTPFileLoader(final DataSource source) {
        super(source);
    }

    public static Loader<FileLoadResult<?>> of(final DataSource source) {
        return new FTPFileLoader(source);
    }

    @Override
    public DataSource.Types processedType() {
        return DataSource.Types.FTP;
    }

    @Override
    protected FileLoadResult<?> performLoading() {
        FileLoadResult.FileLoadResultBuilder<byte[]> resultBuilder = FileLoadResult.of();

        final FTPClient client = new FTPClient();

        try {
            client.connect(this.source.getPath());

            if (StringUtil.isNonNullOrEmpty(this.source.getUsername())) {
                client.login(this.source.getUsername(), this.source.getPassword());
            } else {
                client.login("anonymous", "");
            }

            client.enterLocalPassiveMode();

            Optional<FTPFile> remoteFile = Stream.of(client.listFiles()).filter(file -> file.getName().equals(this.source.getTarget())).findFirst();

            if (!remoteFile.isPresent()) {
                throw new NullPointerException("Couldn't find file '" + this.source.getTarget() + "' on repository '" + this.source.getId() + "'.");
            }

            final byte[] data = IOUtils.toByteArray(client.retrieveFileStream(this.source.getTarget()));

            resultBuilder
                    .name(this.source.getName())
                    .statusCode(200)
                    .value(data)
                    .message(client.getReplyString());

        } catch (IOException | NullPointerException | NumberFormatException ex) {

            resultBuilder
                    .name(this.source.getName())
                    .statusCode(500)
                    .message("FAILED: " + ex.getLocalizedMessage());

        } finally {
            try {
                client.disconnect();
            } catch (IOException ex) {
                resultBuilder
                        .name(this.source.getName())
                        .statusCode(500)
                        .message("FAILED: Couldn't close connection to ftp server. " + ex.getLocalizedMessage());
            }
        }

        return resultBuilder.build();
    }
}
