/*
 * Copyright 2019 Michael Rhöse.
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
package io.varietas.instrumentum.simul.io.handlers;

/**
 * <h2>FileCreateHandler</h2>
 * <p>
 * {description}
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 09/25/2019
 */
@FunctionalInterface
public interface FileCreateHandler extends FileEventHandler {

    @Override
    public void handle(String path);
}
