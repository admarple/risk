package com.github.admarple.risk.gameplay.common;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Step;

import lombok.Data;

@Data
public class SingleStepPhase<T extends Step> extends SingleNestedPlayable<Phase, SingleStepPhaseCommand, T> {
    public SingleStepPhase(T step) {
        super(step, SingleStepPhaseCommand::new);
    }
}
