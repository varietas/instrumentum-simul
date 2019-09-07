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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * <h2>HTTPFileLoader</h2>
 * <p>
 * The HTTP file loader is inspired by the post <a href="http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url">here</a>.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/19/2017
 */
@Slf4j
final class HTTPFileLoader extends AbstractLoader<FileLoadResult<?>> {

    private HTTPFileLoader(final DataSource source) {
        super(source);
    }

    public static Loader<FileLoadResult<?>> of(final DataSource source) {
        return new HTTPFileLoader(source);
    }

    @Override
    public DataSource.Types processedType() {
        return DataSource.Types.HTTP;
    }

    @Override
    protected FileLoadResult<?> performLoading() {

        final FileLoadResult.FileLoadResultBuilder<byte[]> resultBuilder = FileLoadResult.of();
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(this.source.getPath());

            httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");

                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    fileName = this.source.getName();
                }

                byte[] file = IOUtils.toByteArray(httpConn);

                resultBuilder
                        .statusCode(200)
                        .name(fileName)
                        .message("OK")
                        .value(file);
            } else {
                resultBuilder
                        .statusCode(400)
                        .name(this.source.getTarget())
                        .message("No file to download. Server replied HTTP code: " + responseCode);
            }

        } catch (IOException ex) {
            resultBuilder
                    .statusCode(500)
                    .name(this.source.getTarget())
                    .message("Error while closing client connection: " + ex.getLocalizedMessage());
        } finally {
            if (Objects.nonNull(httpConn)) {
                httpConn.disconnect();
            }
        }

        return resultBuilder.build();
    }
}
