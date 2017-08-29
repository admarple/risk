package com.github.admarple.risk.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.Data;
import lombok.NonNull;

@Data
public class GameMap implements Plantable {
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

    @Override
    public String asPlantUML() {
        StringBuilder sb = new StringBuilder();
        territories.forEach(territory -> sb.append(territory.asPlantUML()));
        borders.forEach(border -> sb.append(border.asPlantUML()));
        return sb.toString();
    }
}
