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
package io.varietas.instrumentum.simul.fsm.machines.transition;

import io.varietas.instrumentum.simul.fsm.AbstractStateMachine;
import io.varietas.instrumentum.simul.fsm.annotation.StateMachineConfiguration;
import io.varietas.instrumentum.simul.fsm.annotation.Transition;
import io.varietas.instrumentum.simul.fsm.annotation.TransitionListener;
import io.varietas.instrumentum.simul.fsm.configuration.FSMConfiguration;
import io.varietas.instrumentum.simul.fsm.model.Chain;
import io.varietas.instrumentum.simul.fsm.model.Event;
import io.varietas.instrumentum.simul.fsm.model.State;
import io.varietas.instrumentum.simul.fsm.model.TestEntity;
import io.varietas.instrumentum.simul.fsm.listener.SimpleTransitionAfterListener;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>StateMachineWithTransitionAfterListener</h2>
 */
@Slf4j
@StateMachineConfiguration(stateType = State.class, eventType = Event.class, chainType = Chain.class)
public class StateMachineWithTransitionAfterListener extends AbstractStateMachine {

    public StateMachineWithTransitionAfterListener(FSMConfiguration configuration) {
        super(configuration);
    }

    @TransitionListener(SimpleTransitionAfterListener.class)
    @Transition(from = "AVAILABLE", on = "REGISTER", to = "REGISTERED")
    public void fromAvailableToRegistered(final TestEntity context) {
        context.setValue(context.getValue() + 1);
    }

    @TransitionListener(SimpleTransitionAfterListener.class)
    @Transition(from = "REGISTERED", on = "ACTIVATE", to = "ACTIVATED")
    @Transition(from = "PARKED", on = "ACTIVATE", to = "ACTIVATED")
    public void fromAnyToActivated(final TestEntity context) {
        context.setValue(context.getValue() + 2);
    }

    @TransitionListener(SimpleTransitionAfterListener.class)
    @Transition(from = "REGISTERED", on = "DELETE", to = "DELETED")
    @Transition(from = "UNREGISTERED", on = "DELETE", to = "DELETED")
    public void fromAnyToDeleted(final TestEntity context) {
        context.setValue(context.getValue() - 7);
    }

    @TransitionListener(SimpleTransitionAfterListener.class)
    @Transition(from = "ACTIVATED", on = "DEACTIVATE", to = "DEACTIVATED")
    public void fromActivatedToDeactivated(final TestEntity context) {
        context.setValue(context.getValue() - 2);
    }

    @TransitionListener(SimpleTransitionAfterListener.class)
    @Transition(from = "DEACTIVATED", on = "UNREGISTER", to = "UNREGISTERED")
    @Transition(from = "PARKED", on = "UNREGISTER", to = "UNREGISTERED")
    public void fromAnyToUnregistered(final TestEntity context) {
        context.setValue(context.getValue() - 1);
    }

    @TransitionListener(SimpleTransitionAfterListener.class)
    @Transition(from = "DEACTIVATED", on = "PARK", to = "PARKED")
    public void fromDeactivatedToParked(final TestEntity context) {
        context.setValue(context.getValue() - 5);
    }
}