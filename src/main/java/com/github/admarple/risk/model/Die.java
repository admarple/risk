package com.github.admarple.risk.model;

import java.util.Comparator;

import org.apache.commons.lang3.RandomUtils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Die implements Comparable<Die> {
    private final int min;
    private final int max;
    @Setter(AccessLevel.PRIVATE)
    private int currentFace;

    public Die(int min, int max) {
        this.min = min;
        this.max = max;
        roll();
    }

    public void roll() {
        setCurrentFace(RandomUtils.nextInt(min, max + 1));
    }

    @Override
    public int compareTo(Die o) {
        return Comparator.comparingInt(Die::getCurrentFace).compare(this, o);
    }
}
