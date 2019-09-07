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
package io.varietas.instrumentum.simul.io.containers;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;

/**
 * <h2>DataSource</h2>
 * <p>
 * The data source is a container for data repositories located elsewhere. It contains all necessary information to connect to and load files from a repository. The required values depend on the repository type and its settings.
 *
 * @see io.varietas.instrumentum.simul.io.ResourceLoader
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 11/17/2017
 */
@Value(staticConstructor = "of")
public class DataSource {

    /**
     * Repository ID that requires these set of information. It is the used to load a file with the ResourceLoader facade.
     */
    int id;
    /**
     * Repository name as text for display propose.
     */
    String name;
    /**
     * Full path to the data e.g. - http://localhost:8080/static/files - /home/usr - ftp://127.0.0.1/downloads/newest
     */
    String path;
    /**
     * File name with type extension e.g. - cv.pfd - stats.csv
     */
    String target;
    /**
     * Source type for loader instantiation.
     */
    Types type;
    /**
     * Username for required authentication.
     */
    @NonFinal
    @Setter
    @Accessors(chain = true)
    String username;
    /**
     * Password for required authentication.
     */
    @NonFinal
    @Setter
    @Accessors(chain = true)
    String password;

    /**
     * <h2>Types</h2>
     * <p>
     * Source type for loader instantiation.
     *
     * @see io.varietas.instrumentum.simul.io.ResourceLoader#of(io.varietas.instrumentum.simul.io.containers.DataSource)
     *
     * @author Michael Rhöse
     * @version 1.0.0.0, 11/17/2017
     */
    public static enum Types {
        FTP,
        HTTP,
        DIR
    }

    public static DataSource DIR(final int id, final String name, final String path, final String target) {
        return DataSource.of(id, name, path, target, DataSource.Types.DIR, null, null);
    }

    public static DataSource HTTP(final int id, final String name, final String path, final String target) {
        return DataSource.of(id, name, path, target, DataSource.Types.HTTP, null, null);
    }

    public static DataSource SECURED_HTTP(final int id, final String name, final String path, final String target, final String username, final String password) {
        return DataSource.of(id, name, path, target, DataSource.Types.HTTP, username, password);
    }

    public static DataSource FTP(final int id, final String name, final String path, final String target) {
        return DataSource.of(id, name, path, target, DataSource.Types.FTP, null, null);
    }

    public static DataSource SECURED_FTP(final int id, final String name, final String path, final String target, final String username, final String password) {
        return DataSource.of(id, name, path, target, DataSource.Types.FTP, username, password);
    }
}
