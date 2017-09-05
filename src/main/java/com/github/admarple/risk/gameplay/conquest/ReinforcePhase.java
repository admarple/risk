package com.github.admarple.risk.gameplay.conquest;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.common.ReinforceStep;
import com.github.admarple.risk.gameplay.core.Yieldable;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;

@Data
public class ReinforcePhase implements Phase<ReinforcePhaseCommand> {

    @NonNull private Player player;
    @NonNull private Risk risk;
    private boolean doneExchanging = false;

    @Override
    public ReinforcePhaseCommand getCommand() {
        return new ReinforcePhaseCommand();
    }

    @Override
    public void perform(ReinforcePhaseCommand command) {
        while (!isDoneExchanging()) {
            Yieldable nextStep = new ExchangeStep(risk, player);
            nextStep.play();
            if (nextStep.isYielded()) {
                doneExchanging = true;
            }
        }
        risk.getBoard().generateReinforcements(player);
        while (!player.isDoneReinforcing()) {
            new ReinforceStep(player).play();
        }
    }
}
