package com.github.admarple.risk.gameplay.order;

import com.github.admarple.risk.gameplay.Step;

import lombok.Data;
import lombok.NonNull;

@Data
public class RollStep implements Step<RollStepCommand> {
    @NonNull final Roll roll;

    /**
     * The player has no option but to roll.
     *
     * @return
     */
    @Override
    public RollStepCommand getCommand() {
        return new RollStepCommand();
    }

    @Override
    public void perform(RollStepCommand command) {
        if (roll.isRolling()) {
            roll.roll();
        }
    }
}
