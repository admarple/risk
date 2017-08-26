package com.github.admarple.risk.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class DiePool {
    Set<Die> dice;

    public List<Die> getDice(int numberOfDice) {
        IntStream.range(dice.size(), numberOfDice).forEach(i -> dice.add(generateDie()));

        return dice.stream().limit(numberOfDice).collect(toList());
    }

    public Die generateDie() {
        return new Die(1,6);
    }
}
