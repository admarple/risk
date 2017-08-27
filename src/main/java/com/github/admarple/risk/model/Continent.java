package com.github.admarple.risk.model;

import java.util.Set;

import lombok.Data;

@Data
public class Continent {
    private Set<Territory> territories;
    private int reinforcementBonusSize;
}
