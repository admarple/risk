package com.github.admarple.risk.gameplay.setup;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Stage;
import com.github.admarple.risk.gameplay.common.ReinforceStep;
import com.github.admarple.risk.gameplay.common.SinglePhaseTurn;
import com.github.admarple.risk.gameplay.common.SingleStepPhase;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(exclude = {"risk"})
public class ReinforceSetupStage implements Stage<ReinforceSetupStageCommand> {

    @NonNull final Risk risk;

    @Override
    public ReinforceSetupStageCommand getCommand() {
        return new ReinforceSetupStageCommand();
    }

    @Override
    public void perform(ReinforceSetupStageCommand command) {
        log.info("performing {}", getClass().getSimpleName());
        // TODO: Look at de-duping the "continue until done" logic with SingleActorGame#play()
        // TODO: Look at abstracting the "cycle through player turns" logic to be re-used by other Stages
        while (!isAllReinforcementsDeployed()) {
            Player player = risk.getCurrentPlayer();
            new SinglePhaseTurn<>(player,
                new SingleStepPhase<>(
                    new ReinforceStep(player))).play();
            risk.nextPlayer();
        }
    }

    private boolean isAllReinforcementsDeployed() {
        return risk.getPlayers().stream().noneMatch(player -> player.getHoldingPool().canSplit());
    }
}
