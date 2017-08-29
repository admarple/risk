package com.github.admarple.risk.gameplay.common;

import com.github.admarple.risk.gameplay.Step;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ReinforceStep implements Step<ReinforceStepCommand> {

    @NonNull private final Player player;

    @Override
    public ReinforceStepCommand getCommand() {
        return new ReinforceStepCommand(player);
    }

    @Override
    public void perform(ReinforceStepCommand command) {
        log.info("Reinforce territory for player {}", player.getName());
        while (true) {
            try {
                player.getArmy(command.getTerritory()).accept(player.getHoldingPool().split(1));
                return;
            } catch (IllegalArgumentException e) {
                log.warn("Error reinforcing territory.  Please try again.", e);
            }
        }
    }
}
