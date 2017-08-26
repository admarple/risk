package com.github.admarple.risk.gameplay;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.IteratorUtils;

import com.github.admarple.risk.model.Board;
import com.github.admarple.risk.model.Card;
import com.github.admarple.risk.model.CardSet;
import com.github.admarple.risk.model.Player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Setter(AccessLevel.PACKAGE)
public class Game {
    @NonNull private final Board board;
    @NonNull private final List<Player> players;
    private final List<Card> drawPile = new LinkedList<>();
    private final List<CardSet> claimedSets = new LinkedList<>();

    private Player currentPlayer;
    private Iterator<Player> playerIterator;
    private Stage stage = Stage.ROLL;
    private Map<Player, Integer> rolls = new HashMap<>();

    public Game(Board board, List<Player> players) {
        this.board = board;
        this.players = players;

        playerIterator = IteratorUtils.loopingIterator(players);
    }

    public void play() {
        while(!gameOver()) {
            playTurn();
            nextPlayer();
        }
    }

    public boolean gameOver() {
        return getRemainingPlayers().size() <= 1;
    }

    private List<Player> getRemainingPlayers() {
        return players.stream().filter(p -> !p.isEliminated()).collect(toList());
    }

    private void playTurn() {
        switch (stage) {
            case ROLL:
                // player must roll
                break;
            case SETUP:
                // player repeatedly chooses new territories to acquire
                // player repeatedly reinforces existing territories
                break;
            case CONQUEST:
                // player repeatedly does any of:
                //   * reinforce existing territories
                //   * exchange cards
                //   * has no options; must advance
                // player repeatedly does any of:
                //   * invade
                //   * exchange cards
                //   * pass
                //   * has no options; must advance
                // player once does any of:
                //   * fortify
                //   * pass
                break;
            default:
                log.error("Bad game state: {}", this);
                throw new RuntimeException("Bad game state");
        }
    }

    private void nextPlayer() {
        currentPlayer = playerIterator.next();
    }

    enum Stage {
        ROLL,
        SETUP,
        CONQUEST
    }
}
