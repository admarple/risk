package com.github.admarple.risk.model;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class DiePool {
    private final List<Die> dice = new LinkedList<>();

    public List<Die> getDice(int numberOfDice) {
        IntStream.range(dice.size(), numberOfDice).forEach(i -> dice.add(generateDie()));

        return dice.stream().limit(numberOfDice).collect(toList());
    }

    /**
     * Generate a new die.  The default is a 6-sided die
     *
     * @return
     */
    private Die generateDie() {
        return new Die(1, 6);
    }
}
