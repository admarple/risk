package com.github.admarple.risk.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Border implements Plantable {
    @NonNull private Territory first;
    @NonNull private Territory second;
    private Direction direction = Direction.ARBITRARY;

    public Border opposite() {
        Border opposite = new Border(second, first);
        opposite.setDirection(direction.getOpposite());
        return opposite;
    }

    @Override
    public String asPlantUML() {
        return first.getName() + " -" + direction.asPlantUML() + "- " + second.getName() + System.lineSeparator();
    }
}
