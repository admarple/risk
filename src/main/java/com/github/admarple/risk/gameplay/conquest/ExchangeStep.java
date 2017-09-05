package com.github.admarple.risk.gameplay.conquest;

import java.util.Optional;
import java.util.Set;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Step;
import com.github.admarple.risk.gameplay.core.Yieldable;
import com.github.admarple.risk.model.Card;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ExchangeStep implements Step<ExchangeStepCommand>, Yieldable<ExchangeStepCommand> {

    @NonNull private final Risk risk;
    @NonNull private final Player player;
    private boolean yielded = false;

    @Override
    public ExchangeStepCommand getCommand() {
        return new ExchangeStepCommand(player);
    }

    @Override
    public void perform(ExchangeStepCommand command) {
        log.info("Exchange cards for player {}", player.getName());
        while (true) {
            try {
                Optional<Set<Card>> selection = command.getTradeIn();
                if (selection.isPresent()) {
                    try {
                        player.getHand().removeAll(selection.get());
                        risk.getBoard().tradeIn(player, selection.get());
                        return;
                    } catch (IllegalArgumentException e) {
                        log.warn("Error exchanging cards.  Please try again", e);
                        player.getHand().addAll(selection.get());
                    }
                }
                else {
                    log.info("User elected not to exchange cards.");
                    yielded = true;
                    return;
                }
            } catch (IllegalArgumentException e) {
                log.warn("Error exchanging cards.  Please try again", e);
            }
        }
    }
}
