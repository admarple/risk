package com.github.admarple.risk.model;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NonNull;

@Data
public class Board {
    @NonNull private GameMap map;
    final Set<Army> armies = new HashSet<>();
    @NonNull DiePool diePool;

    /**
     * Given a player's holdings, generate their reinforcements
     * @param player
     * @return
     */
    Army generateReinforcements(Player player) {
        return null;
    }

    public boolean isSetupDone() {
        Set<Territory> occupied = armies.stream()
            .map(army -> army.getLocation())
            .collect(toSet());

        return occupied.equals(map.getTerritories());
    }
}
