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

import io.varietas.instrumentum.simul.services.SimpleExecutionTimeRecorderService;
import java.lang.reflect.InvocationTargetException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael Rhöse
 */
public abstract class CommonServiceExceptionTest {

    protected abstract Class<? extends CommonServiceException> getExceptionType();

    protected abstract String getAction();

    /**
     * Test of getLocalizedMessage method, of class CommonServiceException.
     */
    @Test
    public void test_getLocalizedMessage() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        CommonServiceException instance = getExceptionType().getConstructor(Class.class).newInstance(SimpleExecutionTimeRecorderService.class);
        String expResult = getAction() + " of service type 'SimpleExecutionTimeRecorderService' isn't possible.";
        String result = instance.getLocalizedMessage();
        Assertions.assertThat(result).isEqualTo(expResult);
        Assertions.assertThat(instance.getAction()).isEqualTo(getAction());
    }

    /**
     * Test of getLocalizedMessage method, of class CommonServiceException.
     */
    @Test
    public void test_getLocalizedMessage_withAdditionalMessage() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        CommonServiceException instance = getExceptionType().getConstructor(Class.class, String.class).newInstance(SimpleExecutionTimeRecorderService.class, "An additional message");
        String expResult = getAction() + " of service type 'SimpleExecutionTimeRecorderService' isn't possible: An additional message.";
        String result = instance.getLocalizedMessage();
        Assertions.assertThat(result).isEqualTo(expResult);
        Assertions.assertThat(instance.getAction()).isEqualTo(getAction());
    }

    /**
     * Test of getLocalizedMessage method, of class CommonServiceException.
     */
    @Test
    public void test_getLocalizedMessage_withAdditionalMessageAndCause() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        CommonServiceException instance = getExceptionType().getConstructor(Class.class, String.class, Throwable.class).newInstance(SimpleExecutionTimeRecorderService.class, "An additional message", new NullPointerException("A NullPointerException"));
        String expResult = getAction() + " of service type 'SimpleExecutionTimeRecorderService' isn't possible: An additional message. NullPointerException: A NullPointerException.";
        String result = instance.getLocalizedMessage();
        Assertions.assertThat(result).isEqualTo(expResult);
        Assertions.assertThat(instance.getAction()).isEqualTo(getAction());
    }
}
