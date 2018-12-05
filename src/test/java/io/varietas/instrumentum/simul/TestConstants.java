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
package io.varietas.instrumentum.simul;

import java.io.File;
import lombok.experimental.UtilityClass;

/**
 * <h2>TestConstants</h2>
 * <p>
 * Contains a number of constants that are used in various tests.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/22/2018
 */
@UtilityClass
public class TestConstants {

    @Deprecated
    public static final String INVALID_FOLDER_PATH = System.getProperty("java.io.tmpdir") + File.separatorChar + "directory-utils-folder";
}
