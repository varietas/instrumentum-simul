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
package io.varietas.instrumentum.simul.fsm.builder;

import io.varietas.instrumentum.simul.fsm.builder.impl.ChainStateMachineBuilderImpl;
import io.varietas.instrumentum.simul.fsm.StateMachine;
import io.varietas.instrumentum.simul.fsm.configuration.CFSMConfigurationImpl;
import io.varietas.instrumentum.simul.fsm.configuration.FSMConfiguration;
import io.varietas.instrumentum.simul.fsm.container.ChainContainer;
import io.varietas.instrumentum.simul.fsm.container.TransitionContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author Michael Rhöse
 */
@RunWith(JUnit4.class)
public class ChainStateMachineBuilderTest {

    public ChainStateMachineBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of extractConfiguration method, of class ChainStateMachineBuilderImpl.
     */
    @Test
    public void testExtractConfigurationWithoutTransitionListener() {
        Class<? extends StateMachine> machineType = ChainStateMachineWithoutListener.class;
        StateMachineBuilder instance = new ChainStateMachineBuilderImpl();
        FSMConfiguration expResult = new CFSMConfigurationImpl(
            Arrays.asList(
                new TransitionContainer(State.REGISTERED, State.DELETED, Event.DELETE, null, null),
                new TransitionContainer(State.UNREGISTERED, State.DELETED, Event.DELETE, null, null),
                new TransitionContainer(State.AVAILABLE, State.REGISTERED, Event.REGISTER, null, null),
                new TransitionContainer(State.REGISTERED, State.ACTIVATED, Event.ACTIVATE, null, null),
                new TransitionContainer(State.PARKED, State.ACTIVATED, Event.ACTIVATE, null, null),
                new TransitionContainer(State.ACTIVATED, State.DEACTIVATED, Event.DEACTIVATE, null, null),
                new TransitionContainer(State.DEACTIVATED, State.UNREGISTERED, Event.UNREGISTER, null, null),
                new TransitionContainer(State.PARKED, State.UNREGISTERED, Event.UNREGISTER, null, null),
                new TransitionContainer(State.DEACTIVATED, State.PARKED, Event.PARK, null, null)
            ),
            Arrays.asList(
                new ChainContainer(State.AVAILABLE, State.ACTIVATED, Chain.INSTALLING, null, null),
                new ChainContainer(State.ACTIVATED, State.PARKED, Chain.PARKING, null, null),
                new ChainContainer(State.ACTIVATED, State.DELETED, Chain.DELETION, null, null),
                new ChainContainer(State.DELETED, State.DELETED, Chain.DELETION, null, null)
            ),
            State.class,
            Event.class,
            Chain.class
        );

        FSMConfiguration result = instance.extractConfiguration(machineType).configuration();

        Assertions.assertThat(expResult.getStateType()).isEqualTo(result.getStateType());
        Assertions.assertThat(expResult.getEventType()).isEqualTo(result.getEventType());
        Assertions.assertThat(((CFSMConfigurationImpl) expResult).getChainType()).isEqualTo(((CFSMConfigurationImpl) result).getChainType());

        Assertions.assertThat(expResult.getTransitions()).hasSameSizeAs(result.getTransitions());
        Assertions.assertThat(expResult.getTransitions()).hasSameElementsAs(result.getTransitions());
        Assertions.assertThat(((CFSMConfigurationImpl) expResult).getChains()).hasSameSizeAs(((CFSMConfigurationImpl) result).getChains());
    }

    @Test
    public void testExtractConfigurationWithTransitionListener() {
        Class<? extends StateMachine> machineType = ChainStateMachineWithListener.class;
        StateMachineBuilder instance = new ChainStateMachineBuilderImpl();

        List<Class<?>> listeners = new ArrayList<Class<?>>() {
            {
                add(SimpleListener.class);
            }
        };

        FSMConfiguration expResult = new CFSMConfigurationImpl(
            Arrays.asList(
                new TransitionContainer(State.REGISTERED, State.DELETED, Event.DELETE, null, listeners),
                new TransitionContainer(State.UNREGISTERED, State.DELETED, Event.DELETE, null, listeners),
                new TransitionContainer(State.AVAILABLE, State.REGISTERED, Event.REGISTER, null, listeners),
                new TransitionContainer(State.REGISTERED, State.ACTIVATED, Event.ACTIVATE, null, listeners),
                new TransitionContainer(State.PARKED, State.ACTIVATED, Event.ACTIVATE, null, listeners),
                new TransitionContainer(State.ACTIVATED, State.DEACTIVATED, Event.DEACTIVATE, null, listeners),
                new TransitionContainer(State.DEACTIVATED, State.UNREGISTERED, Event.UNREGISTER, null, listeners),
                new TransitionContainer(State.PARKED, State.UNREGISTERED, Event.UNREGISTER, null, listeners),
                new TransitionContainer(State.DEACTIVATED, State.PARKED, Event.PARK, null, listeners)
            ),
            Arrays.asList(
                new ChainContainer(State.AVAILABLE, State.ACTIVATED, Chain.INSTALLING, null, null),
                new ChainContainer(State.ACTIVATED, State.PARKED, Chain.PARKING, null, null),
                new ChainContainer(State.ACTIVATED, State.DELETED, Chain.DELETION, null, null),
                new ChainContainer(State.DELETED, State.DELETED, Chain.DELETION, null, null)
            ),
            State.class,
            Event.class,
            Chain.class
        );

        FSMConfiguration result = instance.extractConfiguration(machineType).configuration();

        Assertions.assertThat(expResult.getStateType()).isEqualTo(result.getStateType());
        Assertions.assertThat(expResult.getEventType()).isEqualTo(result.getEventType());
        Assertions.assertThat(((CFSMConfigurationImpl) expResult).getChainType()).isEqualTo(((CFSMConfigurationImpl) result).getChainType());

        Assertions.assertThat(expResult.getTransitions()).hasSameSizeAs(result.getTransitions());
        Assertions.assertThat(expResult.getTransitions()).hasSameElementsAs(result.getTransitions());
        Assertions.assertThat(((CFSMConfigurationImpl) expResult).getChains()).hasSameSizeAs(((CFSMConfigurationImpl) result).getChains());
    }

}
