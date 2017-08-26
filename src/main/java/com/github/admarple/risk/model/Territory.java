package com.github.admarple.risk.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Territory {
    static final Territory nowhere = new Territory("Nowhere");
    @NonNull String name;
}
