package com.github.admarple.risk.model;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Sets;

import lombok.Data;
import lombok.NonNull;

@Data
public class Board {
    @NonNull private GameMap map;
    private final Set<Army> armies = new HashSet<>();
    @NonNull private DiePool diePool;

    /**
     * Given a player's holdings, generate their reinforcements
     * @param player
     * @return
     */
    Army generateReinforcements(Player player) {
        return null;
    }

    public boolean isFullyOccupied() {
        return getOccupiedTerritories().equals(map.getTerritories());
    }

    public Set<Territory> getUnoccupiedTerritories() {
        return Sets.difference(map.getTerritories(), getOccupiedTerritories());
    }

    public Set<Territory> getOccupiedTerritories() {
        return armies.stream()
            .map(Army::getLocation)
            .collect(toSet());
    }

    public void acquire(Army army, Territory territory) {
        Validate.isTrue(map.getTerritories().contains(territory), "Territory not part of the game");
        Validate.isTrue(!armies.contains(army), "Army is already on the board");
        Validate.isTrue(armies.stream().noneMatch(a -> a.getLocation().equals(territory)), "Location occupied by another army");

        army.setLocation(territory);
        armies.add(army);
    }
}
