package com.github.admarple.risk.model;

import java.util.LinkedList;
import java.util.List;

public class Skirmish {
    private final int MAX_INVADER_DICE = 3;
    private final int MAX_DEFENDER_DICE = 2;

    List<Die> invaderDice = new LinkedList<>();
    List<Die> defenderDice = new LinkedList<>();

    public Skirmish(Army invader, Army defender, DiePool diePool) {
        int numInvaders = Math.max(invader.getSize(), MAX_INVADER_DICE);
        int numDefenders = Math.max(defender.getSize(), MAX_DEFENDER_DICE);

        List<Die> dice = diePool.getDice(numDefenders + numInvaders);
        invaderDice = dice.subList(0,numInvaders);
        defenderDice = dice.subList(numInvaders, dice.size());
    }

    public void execute() {
        invaderDice.forEach(Die::roll);
        invaderDice.sort(Die::compareTo);
        defenderDice.forEach(Die::roll);
        defenderDice.sort(Die::compareTo);
    }

    int invaderCasualties() {
        int invaderCasualties = 0;
        for (int i = 0; i < defenderDice.size(); i++) {
            if (defenderDice.get(i).compareTo(invaderDice.get(1)) >= 0) {
                invaderCasualties += 1;
            }
        }
        return invaderCasualties;
    }

    int defenderCasualties() {
        return defenderDice.size() - invaderCasualties();
    }
}
