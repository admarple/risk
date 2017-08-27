package com.github.admarple.risk.gameplay.setup;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Step;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AcquireStep implements Step<AcquireStepCommand> {

    @NonNull private final Risk risk;
    @NonNull private final Player player;

    @Override
    public AcquireStepCommand getCommand() {
        return new AcquireStepCommand(risk, player);
    }

    @Override
    public void perform(AcquireStepCommand command) {
        log.info("Acquire territory for player {}", player.getName());
        while (true) {
            try {
                risk.getBoard().acquire(command.getArmy(), command.getTerritory());
                return;
            } catch (IllegalArgumentException e) {
                log.warn("Error acquiring territory.  Please try again.", e);
            }
        }
    }
}
