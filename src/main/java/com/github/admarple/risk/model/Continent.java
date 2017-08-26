package com.github.admarple.risk.model;

import java.util.Set;

import lombok.Data;

@Data
public class Continent {
    Set<Territory> territories;
    int reinforcementBonusSize;
}
