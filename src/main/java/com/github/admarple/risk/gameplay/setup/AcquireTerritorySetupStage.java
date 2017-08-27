package com.github.admarple.risk.gameplay.setup;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Stage;
import com.github.admarple.risk.gameplay.common.SinglePhaseTurn;
import com.github.admarple.risk.gameplay.common.SingleStepPhase;
import com.github.admarple.risk.model.Army;
import com.github.admarple.risk.model.Player;
import com.github.admarple.risk.model.Territory;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(exclude = {"risk"})
public class AcquireTerritorySetupStage implements Stage<AcquireTerritorySetupStageCommand> {

    @NonNull private final Risk risk;

    @Override
    public AcquireTerritorySetupStageCommand getCommand() {
        return null;
    }

    @Override
    public void perform(AcquireTerritorySetupStageCommand command) {
        log.info("performing {}", getClass().getSimpleName());

        fillHoldingPools();

        // TODO: Look at de-duping the "continue until done" logic with SingleActorGame#play()
        // TODO: Look at abstracting the "cycle through player turns" logic to be re-used by other Stages
        while (!risk.getBoard().isFullyOccupied()) {
            Player player = risk.getCurrentPlayer();
            new SinglePhaseTurn<>(player,
                new SingleStepPhase<>(
                    new AcquireStep(risk, player))).play();
            risk.nextPlayer();
        }
    }

    private void fillHoldingPools() {
        int startingArmySize = 50 - 5 * risk.getPlayers().size();
        risk.getPlayers().forEach(player -> {
            player.getHoldingPool().accept(new Army(startingArmySize, Territory.NOWHERE, player));
        });
    }
}
