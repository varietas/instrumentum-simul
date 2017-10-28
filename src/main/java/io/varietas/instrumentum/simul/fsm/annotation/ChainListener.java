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
package io.varietas.instrumentum.simul.fsm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h2>ChainListener</h2>
 *
 * Transition chain listeners allow the execution of methods before and/or after a transition. Attention: There is no interface available. The methods have to be written as shown below:
 * <pre>
 * <code>
 *
 * public void before(Enum chain, Object target){
 *     // Do something.
 * }
 *
 * public void after(Enum chain, Object target){
 *     // Do something.
 * }
 * </code>
 * </pre> Additional to the chain listeners, each transition listener will be executed too.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/27/2017
 */
@Repeatable(ChainListeners.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ChainListener {

    Class<?> value();
}
