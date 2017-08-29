package com.github.admarple.risk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction implements Plantable {
    RIGHT("right"),
    UP("up"),
    ARBITRARY(""),
    DOWN("down"),
    LEFT("left");

    private String plant;

    /**
     * Return the opposite direction.
     * @return
     */
    public Direction getOpposite() {
        /*
         * A little hacky, relies on the order in which the enum values are defined.
         * First and last are opposites, second and second-to-last are opposites, etc.
         */
        return values()[values().length - 1 - ordinal()];
    }

    public String asPlantUML() {
        return plant;
    }
}
