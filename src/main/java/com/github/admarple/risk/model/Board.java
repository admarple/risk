package com.github.admarple.risk.model;

import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
public class Board implements Plantable {
    @NonNull private GameMap map;
    private final Set<Army> armies = new HashSet<>();
    @NonNull private DiePool diePool;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final LinkedList<Card> drawPile = new LinkedList<>();
    private final List<Set<Card>> tradeIns = new LinkedList<>();

    public Board(GameMap map, DiePool diePool) {
        this.map = map;
        this.diePool = diePool;

        Iterator<Card.CardType> types = Iterators.cycle(Card.CardType.values());
        this.map.getTerritories().forEach(territory -> drawPile.add(new Card(territory, types.next())));
        Collections.shuffle(this.drawPile);
    }

    /**
     * Given a player's holdings, generate their reinforcements
     * @param player
     * @return
     */
    public void generateReinforcements(Player player) {
        // TODO: calculate size based on player's holdings
        player.getHoldingPool().accept(new Army(0, Territory.NOWHERE, player));
    }

    public void tradeIn(Player player, Set<Card> set) {
        Validate.isTrue(set.size() == 3);

        long numTypes = set.stream().map(Card::getType).distinct().count();
        Validate.isTrue(numTypes == 1 || numTypes == 3);

        int armySize;
        if (tradeIns.size() < 5) {
            armySize = 4 + 2 * tradeIns.size();
        } else {
            armySize = 15 + 5 * tradeIns.size() - 5;
        }
        player.getHoldingPool().accept(new Army(armySize, Territory.NOWHERE, player));
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
        army.getOwner().acquire(army);
    }

    public Card draw() {
        return drawPile.pop();
    }

    @Override
    public String asPlantUML() {
        StringBuilder sb = new StringBuilder(map.asPlantUML());
        armies.forEach(army -> sb.append(army.asPlantUML()));
        return sb.toString();
    }
}
