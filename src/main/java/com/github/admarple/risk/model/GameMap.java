package com.github.admarple.risk.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.collections4.set.UnmodifiableSet;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
public class GameMap {
    private final Set<Territory> territories = new HashSet<>();
    private final Set<Border> borders = new HashSet<>();
    private final Set<Continent> continents = new HashSet<>();
    @NonNull private ReinforcementRule reinforcementRule;

    public Set<Border> getBorders() {
        return ImmutableSet.copyOf(borders);
    }

    /**
     * Add a border.
     *
     * Note: all borders are symmetrical
     *
     * @param border
     */
    public void addBorder(Border border) {
        borders.addAll(Arrays.asList(border, border.opposite()));
    }

    /**
     * Remove a border
     *
     * Note: all borders are symmetrical
     *
     * @param border
     */
    public void removeBorder(Border border) {
        borders.removeAll(Arrays.asList(border, border.opposite()));
    }
}
