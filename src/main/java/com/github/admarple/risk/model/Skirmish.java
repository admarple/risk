package com.github.admarple.risk.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

/**
 * A Skirmish is a single roll of invading dice against defending dice during an {@link Invasion}.
 */
@Data
public class Skirmish {
    public static final int MAX_INVADER_DICE = 3;
    public static final int MAX_DEFENDER_DICE = 2;

    @NonNull Army invader;
    @NonNull Army defender;
    List<Die> invaderDice = new LinkedList<>();
    List<Die> defenderDice = new LinkedList<>();

    public Skirmish(Army invader, Army defender, DiePool diePool) {
        this(invader,
            Math.min(invader.getSize() - 1, MAX_INVADER_DICE),
            defender,
            diePool);
    }

    public Skirmish(Army invader, int numInvaders, Army defender, DiePool diePool) {
        this(invader,
            numInvaders,
            defender,
            Math.min(defender.getSize(), MAX_DEFENDER_DICE),
            diePool);
    }

    public Skirmish(Army invader, int numInvaders, Army defender, int numDefenders, DiePool diePool) {
        this.invader = invader;
        this.defender = defender;

        List<Die> dice = diePool.getDice(numDefenders + numInvaders);
        invaderDice = dice.subList(0,numInvaders);
        defenderDice = dice.subList(numInvaders, dice.size());
    }

    public void execute() {
        rollDice();
        invader.damage(invaderCasualties());
        defender.damage(defenderCasualties());
    }

    private void rollDice() {
        invaderDice.forEach(Die::roll);
        invaderDice.sort(Die::compareTo);
        defenderDice.forEach(Die::roll);
        defenderDice.sort(Die::compareTo);
    }

    int invaderCasualties() {
        int invaderCasualties = 0;
        for (int i = 0; i < defenderDice.size(); i++) {
            if (defenderDice.get(i).compareTo(invaderDice.get(i)) >= 0) {
                invaderCasualties += 1;
            }
        }
        return invaderCasualties;
    }

    int defenderCasualties() {
        return defenderDice.size() - invaderCasualties();
    }

    public int getNumInvaders() {
        return invaderDice.size();
    }

    public int getNumDefenders() {
        return defenderDice.size();
    }
}
