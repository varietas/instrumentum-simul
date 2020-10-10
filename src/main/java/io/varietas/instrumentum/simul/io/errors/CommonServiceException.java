/*
 * Copyright 2020 Michael Rhöse.
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
import java.util.Objects;

/**
 * <h2>CommonServiceException</h2>
 * <p>
 * {description}
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/25/2020
 */
public abstract class CommonServiceException extends RuntimeException {

    private static final long serialVersionUID = -8912277587428798770L;

    private final String action;
    private final Class<? extends Service> serviceType;

    public CommonServiceException(final String action, final Class<? extends Service> serviceType) {
        this.action = action;
        this.serviceType = serviceType;
    }

    public CommonServiceException(final String action, final Class<? extends Service> serviceType, final String message) {
        super(message);
        this.action = action;
        this.serviceType = serviceType;
    }

    public CommonServiceException(final String action, final Class<? extends Service> serviceType, final String message, final Throwable cause) {
        super(message, cause);
        this.action = action;
        this.serviceType = serviceType;
    }

    @Override
    public String getLocalizedMessage() {
        StringBuilder builder = new StringBuilder(this.action)
                .append(" of service type '")
                .append(serviceType.getSimpleName());
        if (Objects.nonNull(this.getMessage())) {
            builder
                    .append("' isn't possible: ")
                    .append(this.getMessage())
                    .append('.');
        } else {
            builder
                    .append("' isn't possible.");
        }
        if (Objects.nonNull(super.getCause())) {
            builder
                    .append(' ')
                    .append(super.getCause().getClass().getSimpleName())
                    .append(": ")
                    .append(super.getCause().getLocalizedMessage())
                    .append('.');
        }

        return builder.toString();
    }

    public String getAction() {
        return action;
    }
}
