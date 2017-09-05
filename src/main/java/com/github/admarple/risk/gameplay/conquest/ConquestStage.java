package com.github.admarple.risk.gameplay.conquest;

import java.util.Arrays;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Stage;
import com.github.admarple.risk.gameplay.common.MultiPhaseTurn;
import com.github.admarple.risk.gameplay.common.SingleStepPhase;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ConquestStage implements Stage<ConquestStageCommand> {

    @NonNull private Risk risk;

    @Override
    public ConquestStageCommand getCommand() {
        return new ConquestStageCommand();
    }

    @Override
    public void perform(ConquestStageCommand command) {
        log.info("performing {}", getClass().getSimpleName());
        while (!risk.gameOver()) { // TODO: this condition already exists in SingleActorGame ...
            Player player = risk.getCurrentPlayer();
            new MultiPhaseTurn(player, Arrays.asList(
                new ReinforcePhase(player, risk),
                new AttackPhase(player, risk),
                new SingleStepPhase<>(new FortifyStep()))
            ).play();
            risk.nextPlayer();
        }
    }
}
