package com.github.admarple.risk.gameplay.order;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.Stage;
import com.github.admarple.risk.gameplay.common.SinglePhaseTurn;
import com.github.admarple.risk.gameplay.common.SingleStepPhase;
import com.github.admarple.risk.model.Die;
import com.github.admarple.risk.model.Player;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Delegate;

@Data
public class DetermineOrderStage implements Stage<DetermineOrderStageCommand> {
    @NonNull
    @Delegate
    final Risk risk;

    private final Map<Player, Roll> rolls = new HashMap<>();
    private Player first;

    public DetermineOrderStage(Risk risk) {
        this.risk = risk;

        Iterator<Die> dice = getBoard().getDiePool().getDice(getPlayers().size()).iterator();
        getPlayers().forEach(p -> rolls.put(p, new Roll(dice.next())));
    }

    @Override
    public DetermineOrderStageCommand getCommand() {
        return new DetermineOrderStageCommand();
    }


    @Override
    public void perform(DetermineOrderStageCommand command) {
        // TODO: Look at de-duping the "continue until done" logic with SingleActorGame#play()
        // TODO: Look at abstracting the "cycle through player turns" logic to be re-used by other Stages
        while (!isOrderDetermined()) {
            Player player = risk.getCurrentPlayer();
            new SinglePhaseTurn<>(player,
                new SingleStepPhase<>(
                    new RollStep(rolls.get(player)))).play();
        }

        Validate.notNull(first);
        risk.skipToPlayer(first);
    }

    private boolean allPlayersRolled() {
        return rolls.values().stream().noneMatch(Roll::isRolling);
    }

    /**
     * NOTE: this "is..." method has side-effects
     *
     * @return
     */
    private boolean isOrderDetermined() {
        if (!allPlayersRolled()) {
            return false;
        }

        List<Player> winners = rolls.entrySet().stream()
            .collect(toMap(
                e -> e.getValue(),
                e -> Lists.newLinkedList(Arrays.asList(e.getKey())),
                (existing, added) -> { existing.addAll(added); return existing; }
            )).entrySet().stream()
            .sorted(Comparator.comparing(e -> e.getKey().getDie()))
            .findFirst()
            .map(Map.Entry::getValue)
            .filter(players -> players.size() > 0)
            .orElseThrow(() -> new RuntimeException("No player rolls found"));


        if (winners.size() > 1) {
            // reset the rolls and try again
            winners.forEach(player -> rolls.get(player).startRolling());
            return false;
        } else {
            first = winners.get(0);
            return true;
        }
    }
}
