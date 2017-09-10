package com.github.admarple.risk.gameplay.conquest;

import java.util.Optional;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Step;
import com.github.admarple.risk.gameplay.core.Yieldable;
import com.github.admarple.risk.model.Invasion;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AttackStep implements Step<AttackStepCommand>, Yieldable<AttackStepCommand> {

    @NonNull private final Risk risk;
    @NonNull private final Player player;
    private boolean yielded = false;
    private boolean victory = false;

    @Override
    public AttackStepCommand getCommand() {
        return null;
    }

    @Override
    public void perform(AttackStepCommand command) {
        log.info("Attacking for player {}", player.getName());
        while (true) {
            try {
                Optional<AttackStepCommand.Choice> choice = command.getChoice();

                if (choice.isPresent()) {
                    try {
                        new Invasion(choice.get().getSource(), choice.get().getDestination(), risk).play();
                        return;
                    } catch (IllegalArgumentException e) {
                        log.warn("Error attacking.  Please try again", e);
                    }
                }
                else {
                    log.info("User elected not to attack.");
                    yielded = true;
                    return;
                }
            } catch (IllegalArgumentException e) {
                log.warn("Error attacking.  Please try again", e);
            }
        }
    }
}
