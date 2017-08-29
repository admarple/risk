package com.github.admarple.risk.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Border implements Plantable {
    @NonNull private Territory first;
    @NonNull private Territory second;

    public Border opposite() {
        return new Border(second, first);
    }

    @Override
    public String asPlantUML() {
        return first.getName() + " -- " + second.getName() + System.lineSeparator();
    }
}
