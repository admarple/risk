package com.github.admarple.risk.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Card {
    @NonNull private final Territory territory;
    @NonNull private final CardType type;

    public static enum CardType {
        INFANTRY,
        CAVALRY,
        ARTILLERY
    }
}
