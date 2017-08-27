package com.github.admarple.risk.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Data
@EqualsAndHashCode(exclude = {"armies", "holdingPool"})
public class Player {
    @NonNull private String name;
    private final List<Card> hand = new LinkedList<>();
    @Getter(AccessLevel.PRIVATE)
    private final Map<Territory, Army> armies = new HashMap<>();
    private boolean eliminated = false;

    private final Territory poolLocation = new Territory("Holding Pool");
    private final Army holdingPool = new Army(poolLocation, this);

    /**
     * Get the Player's army at a particular Territory.  If the player does not have an army in that territory, then
     * return null.  If that army has been destroyed, this method cleans it from the Player's list of active armies
     * and also returns null.
     *
     * @param territory
     * @return the Army at that location, or null
     */
    public Army getArmy(Territory territory) {
        return armies.computeIfPresent(territory, (t, a) -> a.isDestroyed() ? null : a);
    }
}
