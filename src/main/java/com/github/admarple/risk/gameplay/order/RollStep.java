package com.github.admarple.risk.gameplay.order;

import com.github.admarple.risk.gameplay.Step;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("performing {}", getClass().getSimpleName());
        if (roll.isRolling()) {
            log.info("Rolling like Rick");
            roll.roll();
        }
    }
}
