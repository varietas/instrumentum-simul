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
package io.varietas.instrumentum.simul.fsm;

import io.varietas.instrumentum.simul.fsm.configuration.FSMConfiguration;
import io.varietas.instrumentum.simul.fsm.container.TransitionContainer;
import io.varietas.instrumentum.simul.fsm.error.InvalidTransitionError;
import io.varietas.instrumentum.simul.fsm.error.TransitionInvocationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>AbstractStateMachine</h2>
 *
 * This class represents an abstract implementation of the {@link StateMachine} interface. The default implementation contains the firing of single transitions.
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/7/2017
 */
@Slf4j
public abstract class AbstractStateMachine implements StateMachine {

    protected final FSMConfiguration configuration;

    public AbstractStateMachine(final FSMConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * This method searches the container which contains all information required to performing the transition operation.
     *
     * @param transition Next transition kind.
     * @return Expected container for the transition, otherwise an empty Optional.
     */
    protected Optional<TransitionContainer> findTransitionContainer(final Enum transition) {
        return this.configuration.getTransitions().stream()
            .filter(transit -> transit.getOn().equals(transition))
            .findFirst();
    }

    /**
     * Validates the current state of a transition target. If the current state of the target isn't equals the expected FROM state of the transition, the transition isn't possible.
     *
     * @param currentState Current state of the transition target.
     * @param expectedTransition Transition information which contains the expected start state.
     * @return True if the states matches and the transition is possible, otherwise false.
     */
    protected boolean isTransitionPossible(final Enum currentState, final TransitionContainer expectedTransition) {
        return currentState.equals(expectedTransition.getFrom());
    }

    @Override
    public void fire(final Enum transition, final StatedObject target) throws TransitionInvocationException, InvalidTransitionError {

        final Optional<TransitionContainer> transitionContainer = this.findTransitionContainer(transition);

        if (!transitionContainer.isPresent()) {
            throw new InvalidTransitionError(transition, "Couldn't find transition.");
        }

        this.fire(transitionContainer.get(), target);
    }

    /**
     * Performs the execution of a single transition.
     *
     * @param transition Container of transition which has to be performed.
     * @param target Transition target.
     */
    protected void fire(TransitionContainer transition, StatedObject target) {
        if (!this.isTransitionPossible(target.state(), transition)) {
            throw new InvalidTransitionError(transition.getOn(), "Current state " + target.state().name() + " doesn't match required state " + transition.getFrom().name() + ".");
        }

        try {
            transition.getCalledMethod().invoke(this, target);
            target.state(transition.getTo());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new TransitionInvocationException(transition.getOn(), transition.getCalledMethod().getName(), ex.getLocalizedMessage());
        }
    }
}
