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
package io.varietas.instrumentum.simul.io.errors;

import io.varietas.instrumentum.simul.services.Service;

/**
 * <h2>ServiceCreationException</h2>
 * <p>
 * Signals an error while creation of a service. The reasons can be each custom error.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 09/25/2019
 */
public class ServiceCreationException extends CommonServiceException {

    private static final long serialVersionUID = 5941172734762639270L;

    public ServiceCreationException(final Class<? extends Service> serviceType) {
        super("Creation", serviceType);
    }

    public ServiceCreationException(final Class<? extends Service> serviceType, final String message) {
        super("Creation", serviceType, message);
    }

    public ServiceCreationException(final Class<? extends Service> serviceType, final String message, final Throwable cause) {
        super("Creation", serviceType, message, cause);
    }
}
