package com.github.admarple.risk.gameplay.common;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Step;

import lombok.Data;
import lombok.NonNull;

@Data
public class SingleStepPhase<T extends Step> implements Phase<SingleStepPhaseCommand> {

    @NonNull
    final T step;

    @Override
    public SingleStepPhaseCommand getCommand() {
        return new SingleStepPhaseCommand();
    }

    @Override
    public void perform(SingleStepPhaseCommand command) {
        step.play();
    }
}
