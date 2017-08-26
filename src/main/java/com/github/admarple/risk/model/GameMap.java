package com.github.admarple.risk.model;

import java.util.Set;

import lombok.Data;

@Data
public class GameMap {
    Set<Territory> territories;
    Set<Border> borders;
    Set<Continent> continents;
    ReinforcementRule reinforcementRule;
}
