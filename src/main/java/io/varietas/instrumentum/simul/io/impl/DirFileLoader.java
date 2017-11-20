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
package io.varietas.instrumentum.simul.io.impl;

import io.varietas.instrumentum.simul.io.container.DataSource;
import io.varietas.instrumentum.simul.io.container.FileLoadResult;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>DirFileLoader</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0, 11/19/2017
 */
@Slf4j
public class DirFileLoader extends AbstractLoader {

    public DirFileLoader(DataSource source) {
        super(source);
    }

    @Override
    public DataSource.Types processedType() {
        return DataSource.Types.DIR;
    }

    @Override
    protected FileLoadResult performLoading() {
        ///< TODO: Implement this.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
