package com.github.admarple.risk.gameplay;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.github.admarple.risk.gameplay.core.Command;
import com.github.admarple.risk.gameplay.core.SingleActorGame;
import com.github.admarple.risk.gameplay.order.DetermineOrderStage;
import com.github.admarple.risk.gameplay.order.Roll;
import com.github.admarple.risk.gameplay.setup.AcquireTerritorySetupStage;
import com.github.admarple.risk.model.Board;
import com.github.admarple.risk.model.Card;
import com.github.admarple.risk.model.CardSet;
import com.github.admarple.risk.model.Die;
import com.github.admarple.risk.model.Player;
import com.google.common.collect.Iterators;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

/**
 * A single game of Risk.
 *
 * By convention, the hierarchy of {@link com.github.admarple.risk.gameplay.core.Playable}s used is:
 * <ul>
 *   <li>{@link Risk} - an entire game of Risk</li>
 *   <ul>
 *     <li>{@link Stage} - a game is composed of multiple Stages.  Each Stage has distinct mechanics.  The three Stages
 *     of traditional Risk are (1) roll for order, (2) setup, and (3) conquest.</li>
 *     <ul>
 *       <li>{@link Turn} - everything a single player chooses to do before yielding the action to the next player.</li>
 *       <ul>
 *         <li>{@link Phase} - a turn is composed of multiple phases.  Each Phase has distinct mechanics.  For example,
 *         the Phases of the conquest Stage are (1) reinforce, (2) attack, and (3) fortify.</li>
 *         <ul>
 *           <li>{@link Step} - a phase is composed of Steps.  Each step has distinct mechanics.  For example, the Steps
 *           of the reinforcement Phase are (1) exchange cards, and (2) reinforce.</li>
 *         </ul>
 *       </ul>
 *     </ul>
 *   </ul>
 * </ul>
 *
 * The outline of the game is:
 *
 *   <ul>
 *     <li>DetermineOrderStage</li>
 *     <ul>
 *       <li>Turn (cycle)</li>
 *       <ul>
 *         <li>RollPhase</li>
 *         <ul>
 *           <li>RollStep</li>
 *         </ul>
 *       </ul>
 *     </ul>
 *     <li>AcquireTerritorySetupStage</li>
 *     <ul>
 *       <li>Turn (cycle)</li>
 *       <ul>
 *         <li>AcquireTerritoryPhase</li>
 *         <ul>
 *           <li>AcquireTerritoryStep</li>
 *         </ul>
 *       </ul>
 *     </ul>
 *     <li>ReinforcementSetupStage</li>
 *     <ul>
 *       <li>Turn (cycle)</li>
 *       <ul>
 *         <li>SingleReinforcePhase</li>
 *         <ul>
 *           <li>SingleUnitReinforceStep</li>
 *         </ul>
 *       </ul>
 *     </ul>
 *     <li>ConquestStage</li>
 *     <ul>
 *       <li>Turn (cycle)</li>
 *       <ul>
 *         <li>ReinforcePhase</li>
 *         <ul>
 *           <li>CardExchangeStep (multiple)</li>
 *           <li>ReinforceStep (multiple)</li>
 *         </ul>
 *         <li>AttackPhase</li>
 *         <ul>
 *           <li>AttackStep (multiple)</li>
 *         </ul>
 *         <li>FortifyPhase</li>
 *         <ul>
 *           <li>FortifyStep (optional)</li>
 *         </ul>
 *       </ul>
 *     </ul>
 *   </ul>
 */
@Slf4j
@Data
@Setter(AccessLevel.PACKAGE)
public class Risk implements SingleActorGame<Stage> {
    @NonNull
    private final Board board;
    private final List<Player> players = new LinkedList<>();
    private final List<Card> drawPile = new LinkedList<>();
    private final List<CardSet> claimedSets = new LinkedList<>();

    private final List<Stage> stages;
    private Stage currentStage;
    private Iterator<Stage> stageIterator;

    // TODO: move to Stage ?
    private Player currentPlayer;
    private Iterator<Player> playerIterator;

    // TODO: Remove after completing the other stages
    private boolean forcedGameOver = false;

    public Risk(Board board, List<Player> players) {
        this.board = board;
        Validate.notEmpty(players);
        this.players.addAll(players);

        stages = Arrays.asList(
            new DetermineOrderStage(this)
            , new AcquireTerritorySetupStage(this)
            //, new ReinforceSetupStage()
            //, new ConquestStage()
            , new Stage() {
                @Override
                public Command getCommand() {
                    return new Command() { };
                }

                @Override
                public void perform(Command command) {
                    // Game Over
                    forcedGameOver = true;
                }
            }
        );
        stageIterator = stages.iterator();

        includeAllPlayers();
    }

    @Override
    public Stage nextPlayable() {
        currentStage = stageIterator.next();
        return currentStage;
    }

    public boolean gameOver() {
        return isForcedGameOver() || getRemainingPlayers().size() <= 1;
    }

    private List<Player> getRemainingPlayers() {
        return players.stream()
            .filter(p -> !p.isEliminated())
            .collect(toList());
    }

    public Player nextPlayer() {
        currentPlayer = playerIterator.next();
        return currentPlayer;
    }

    // TODO: move to Stage ?
    public void skipToPlayer(Player player) {
        Validate.isTrue(players.contains(player));

        while (!getCurrentPlayer().equals(player)) {
            nextPlayer();
        }
    }

    // TODO: move to Stage ?
    void includeAllPlayers() {
        playerIterator = Iterators.cycle(players);
        nextPlayer();
    }
}
