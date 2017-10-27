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

import io.varietas.instrumentum.simul.fsm.configuration.CFSMConfigurationImpl;
import io.varietas.instrumentum.simul.fsm.configuration.FSMConfigurationImpl;
import io.varietas.instrumentum.simul.fsm.container.ChainContainer;
import io.varietas.instrumentum.simul.fsm.container.TransitionContainer;
import io.varietas.instrumentum.simul.fsm.error.InvalidTransitionError;
import io.varietas.instrumentum.simul.fsm.error.TransitionInvocationException;
import java.util.Optional;

/**
 * <h2>AbstractChainStateMachine</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/27/2017
 */
public class AbstractChainStateMachine extends AbstractStateMachine implements ChainStateMachine {

    public AbstractChainStateMachine(FSMConfigurationImpl configuration) {
        super(configuration);
    }

    /**
     * This method searches the container which contains all information required to performing transition operations.
     *
     * @param transitionChain Next transition chain kind.
     * @param startState Start state of the current transition target for identification of the right chain.
     * @return Expected container for the transition chain, otherwise an empty Optional.
     */
    protected Optional<ChainContainer> findChainContainer(final Enum transitionChain, final Enum startState) {
        return ((CFSMConfigurationImpl) this.configuration).getChains().stream()
            .filter(chain -> chain.getOn().equals(transitionChain))
            .filter(chain -> chain.getFrom().equals(startState))
            .findFirst();
    }

    @Override
    public void fireChain(Enum transitionChain, StatedObject target) throws TransitionInvocationException, InvalidTransitionError {
        final Optional<ChainContainer> chainContainer = this.findChainContainer(transitionChain, target.state());

        if (!chainContainer.isPresent()) {
            throw new InvalidTransitionError(transitionChain, "Couldn't find chain.");
        }

        if (!target.state().equals(chainContainer.get().getFrom())) {
            throw new InvalidTransitionError(transitionChain, "Current state " + target.state().name() + " doesn't match required state " + chainContainer.get().getFrom().name() + ".");
        }

        chainContainer.get().getChainParts().forEach(chainPart -> this.fire((TransitionContainer) chainPart, target));
    }
}
