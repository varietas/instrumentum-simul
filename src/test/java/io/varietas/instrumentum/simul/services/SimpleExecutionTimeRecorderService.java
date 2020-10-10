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
package io.varietas.instrumentum.simul.services;

import io.varietas.instrumentum.simul.io.errors.ServiceExecutionException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;

/**
 * <h2>SimpleConsoleOutputService</h2>
 * <p>
 * Simple service implementation of a varietas service used in tests only.
 *
 * @author Michael Rhöse
 * @version 1.0.0.0, 08/25/2020
 */
@NoArgsConstructor(staticName = "of")
public class SimpleExecutionTimeRecorderService implements Service {

    private final List<String> executionTimes = new ArrayList<>();

    @Override
    public ServiceConfiguration configuration() {
        return ServiceConfiguration.of("TestOutputService", 25, TimeUnit.MILLISECONDS, true);
    }

    @Override
    public void execute() throws ServiceExecutionException {
        executionTimes.add(Calendar.getInstance().getTime().toString());
    }

    public List<String> getExecutionTimes() {
        return this.executionTimes;
    }
}
