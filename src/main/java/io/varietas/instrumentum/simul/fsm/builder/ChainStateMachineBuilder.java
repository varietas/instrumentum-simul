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

import io.varietas.instrumentum.simul.fsm.StateMachine;
import io.varietas.instrumentum.simul.fsm.annotation.StateMachineConfiguration;
import io.varietas.instrumentum.simul.fsm.annotation.TransitionChain;
import io.varietas.instrumentum.simul.fsm.annotation.TransitionChains;
import io.varietas.instrumentum.simul.fsm.configuration.CFSMConfigurationImpl;
import io.varietas.instrumentum.simul.fsm.container.ChainContainer;
import io.varietas.instrumentum.simul.fsm.container.TransitionContainer;
import io.varietas.instrumentum.simul.fsm.error.TransitionChainCreationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>ChainStateMachineBuilder</h2>
 *
 * @author Michael Rhöse
 * @version 1.0.0, 10/27/2017
 */
@Slf4j
public class ChainStateMachineBuilder extends StateMachineBuilder {

    protected Class<? extends Enum> chainType;

    private final List<ChainContainer> chains = new ArrayList<>();

    /**
     * Extracts the configuration from a given {@link StateMachine}. This process should do only once per state machine type and shared between the instances because the collection of information is a
     * big process and can take a while.
     *
     * @param machineType State machine type where the configuration is present.
     * @return The instance of the builder for a fluent like API.
     */
    @Override
    public ChainStateMachineBuilder extractConfiguration(final Class<? extends StateMachine> machineType) {
        this.machineType = machineType;

        StateMachineConfiguration machineConfiguration = this.machineType.getAnnotation(StateMachineConfiguration.class);

        this.stateType = machineConfiguration.stateType();
        this.eventType = machineConfiguration.eventType();
        this.chainType = machineConfiguration.chainType();

        this.transitions.addAll(this.collectTransitions());
        this.chains.addAll(this.createChains());

        this.configuration = new CFSMConfigurationImpl(
            this.transitions,
            this.chains,
            this.stateType,
            this.eventType,
            this.chainType);

        LOGGER.debug("Configuration for '{}' created:\n"
            + "-> {} transitions collected\n"
            + "-> {} chains collected\n"
            + "-> {} used for state type\n"
            + "-> {} used for event type\n"
            + "-> {} used for chain type.",
            this.machineType.getSimpleName(),
            this.transitions.size(),
            this.chains.size(),
            this.stateType.getSimpleName(),
            this.eventType.getSimpleName(),
            this.chainType.getSimpleName()
        );

        return this;
    }
    
    /**
     * The varietas.io transition implementation supports transition chains. These chains allows the definition of transition on programming time. That makes execution of chained transitions with a
     * single command possible. This method creates all available transition chains.
     *
     * @return List of all available transition chains.
     */
    private List<ChainContainer> createChains() {

        if (this.machineType.isAnnotationPresent(TransitionChains.class)) {
            return Stream.of(this.machineType.getAnnotation(TransitionChains.class
            ).value())
                .map(chain -> {
                    Enum from = Enum.valueOf(this.stateType, chain.from());
                    Enum to = Enum.valueOf(stateType, chain.to());
                    Enum on = Enum.valueOf(this.chainType, chain.on());
                    List<TransitionContainer> chainParts = this.collectTransitionsForChain(from, to, on);
                    LOGGER.debug("Chain from '{}' to '{}' on '{}' will be created. {} chain parts collected.", from.name(), to.name(), on.name(), chainParts.size());
                    return new ChainContainer<>(
                        from,
                        to,
                        on,
                        chainParts
                    );
                })
                .distinct()
                .collect(Collectors.toList());
        }

        TransitionChain chain = this.machineType.getAnnotation(TransitionChain.class);
        Enum from = Enum.valueOf(this.stateType, chain.from());
        Enum to = Enum.valueOf(stateType, chain.to());
        Enum on = Enum.valueOf(this.chainType, chain.on());
        List<TransitionContainer> chainParts = this.collectTransitionsForChain(from, to, on);
        LOGGER.debug("Chain from '{}' to '{}' on '{}' will be created. {} chain parts collected.", from.name(), to.name(), on.name(), chainParts.size());
        return Arrays.asList(new ChainContainer<>(from, to, on, chainParts));
    }

    /**
     * Collects all transitions required by transition chain from the already collected transitions.
     *
     * @param from Start state of the transition chain.
     * @param to End state of the transition chain.
     * @param on Event of the transition chain. Represents a simple identifier.
     * @return List of all required transitions as containers.
     */
    private List<TransitionContainer> collectTransitionsForChain(final Enum from, final Enum to, final Enum on) {
        final List<TransitionContainer> res = new ArrayList<>();

        List<TransitionContainer> nexts = this.transitions.stream().filter(transition -> transition.getFrom().equals(from)).collect(Collectors.toList());

        if (nexts.isEmpty()) {
            throw new TransitionChainCreationException(true, from.name(), to.name(), on.name());
        }

        if (nexts.size() == 1) {
            res.add(nexts.get(0));
            LOGGER.debug("State transition from '{}' to '{}' on '{}' added to chain.", nexts.get(0).getFrom().name(), nexts.get(0).getTo().name(), nexts.get(0).getOn().name());
            this.collectTransitionRecursively(nexts.get(0), on, to, res, 1);
            return res;
        }

        boolean isEnd = false;
        for (TransitionContainer next : nexts) {
            List<TransitionContainer> tempRes = new ArrayList<>();
            tempRes.add(next);
            LOGGER.debug("Subchain is started.");
            LOGGER.debug("State transition from '{}' to '{}' on '{}' added to chain.", nexts.get(0).getFrom().name(), nexts.get(0).getTo().name(), nexts.get(0).getOn().name());
            isEnd = this.collectTransitionRecursively(next, on, to, tempRes, 1);

            if (isEnd) {
                res.addAll(tempRes);
                LOGGER.debug("Subchain started on '{}' passes and is added to chain result.", from.name());
                break;
            }
            LOGGER.debug("Subchain on '{}' not passes.", from.name());
        }

        if (!isEnd) {
            throw new TransitionChainCreationException(true, from.name(), to.name(), on.name());
        }

        return res;
    }

    /**
     * Collects all transitions required by transition chain from the already collected transitions in a recursively way. This method searches a way from the start state to the end state.
     *
     * @param entry Currently used start transition.
     * @param chain Event of the chain.
     * @param abourt End state of the chain.
     * @param res List of all collected transitions.
     * @param fallback Abort criteria. This is simply a counter which is increased each recursive step. If the counter greater than the current number of available transitions, the algorithm detects
     * no possible way from the start to the end.
     * @return True if the end state is located, otherwise false.
     */
    private boolean collectTransitionRecursively(final TransitionContainer entry, final Enum chain, final Enum abourt, final List<TransitionContainer> res, final int fallback) {
        List<TransitionContainer> nexts = this.transitions.stream().filter(transition -> transition.getFrom().equals(entry.getTo())).collect(Collectors.toList());

        if (entry.getTo().equals(abourt)) {
            return true;
        }

        if (nexts.isEmpty()) {
            LOGGER.debug("There is no transition available from '{}' to '{}'.", entry.getFrom().name(), abourt.name());
            return false;
        }

        if (fallback == this.transitions.size()) {
            LOGGER.debug("All states visited without location of target state.");
            return false;
        }

        if (nexts.size() == 1) {
            res.add(nexts.get(0));
            LOGGER.debug("State transition from '{}' to '{}' on '{}' added to chain.", nexts.get(0).getFrom().name(), nexts.get(0).getTo().name(), nexts.get(0).getOn().name());
            return this.collectTransitionRecursively(nexts.get(0), chain, abourt, res, fallback + 1);
        }

        for (TransitionContainer next : nexts) {
            List<TransitionContainer> tempRes = new ArrayList<>();
            tempRes.add(next);
            LOGGER.debug("Subchain is started.");
            LOGGER.debug("State transition from '{}' to '{}' on '{}' added to chain.", next.getFrom().name(), next.getTo().name(), next.getOn().name());
            boolean isEnd = this.collectTransitionRecursively(next, chain, abourt, tempRes, fallback + 1);

            if (isEnd) {
                res.addAll(tempRes);
                LOGGER.debug("Subchain started on '{}' passes and is added to chain result.", entry.getFrom().name());
                return true;
            }
            LOGGER.debug("Subchain started on '{}' not passes.", entry.getFrom().name());
        }

        return false;
    }
}
