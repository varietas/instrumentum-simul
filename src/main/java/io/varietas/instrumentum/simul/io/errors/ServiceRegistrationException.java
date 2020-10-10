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
 * <h2>ServiceRegistrationException</h2>
 * <p>
 * Signals an error while registration of a service by the executor. The reasons can be each custom error.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 09/25/2019
 */
public class ServiceRegistrationException extends CommonServiceException {

    private static final long serialVersionUID = 5941172734762639270L;

    public ServiceRegistrationException(final Class<? extends Service> serviceType) {
        super("Registration", serviceType);
    }

    public ServiceRegistrationException(final Class<? extends Service> serviceType, final String message) {
        super("Registration", serviceType, message);
    }

    public ServiceRegistrationException(final Class<? extends Service> serviceType, final String message, final Throwable cause) {
        super("Registration", serviceType, message, cause);
    }
}
