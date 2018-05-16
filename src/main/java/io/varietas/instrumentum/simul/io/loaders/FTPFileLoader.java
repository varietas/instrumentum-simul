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
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * <h2>FTPFileLoader</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0, 11/17/2017
 */
@Slf4j
class FTPFileLoader extends AbstractLoader {

    public FTPFileLoader(final DataSource source) {
        super(source);
    }

    @Override
    public DataSource.Types processedType() {
        return DataSource.Types.FTP;
    }

    @Override
    protected FileLoadResult performLoading() {
        final FileLoadResult res = new FileLoadResult();

        final FTPClient client = new FTPClient();
        try {
            client.connect(this.source.getPath());
            if (!this.source.getUsername().isEmpty()) {
                client.login(this.source.getUsername(), String.valueOf(this.source.getPassword()));
            }

            Optional<FTPFile> remoteFile = Stream.of(client.listFiles()).filter(file -> file.getName().equals(this.source.getName())).findFirst();

            if (!remoteFile.isPresent()) {
                throw new NullPointerException("Couldn't find file '" + this.source.getName() + "' on repository '" + this.source.getId() + "'.");
            }

            final byte[] data = new byte[(int) remoteFile.get().getSize()];
            // Download file from FTP server.
            client.retrieveFileStream(this.source.getName()).read(data);

            res
                    .name(this.source.getName())
                    .value(data)
                    .message(client.getReplyString())
                    .statusCode(Integer.valueOf(client.getStatus()));

        } catch (IOException | NullPointerException | NumberFormatException ex) {

            res
                    .name(this.source.getName())
                    .value(null)
                    .message(ex.getLocalizedMessage())
                    .statusCode(500);

        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                LOGGER.error("Couldn't close connection to ftp server.");
            }
        }

        return res;
    }
}
