package com.github.admarple.risk.model;

import org.apache.commons.lang3.Validate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(exclude = {"owner"})
@AllArgsConstructor
@RequiredArgsConstructor
public class Army implements Plantable {
    @Setter(AccessLevel.PRIVATE)
    private int size = 1;
    @NonNull private Territory location;
    @NonNull private Player owner;

    public Army split(int splitSize) {
        Validate.isTrue(splitSize < getSize(), "Split size must be less than army size");

        Army split = new Army(getLocation(), getOwner());
        getOwner().acquire(split);
        split.size = splitSize;
        this.size -= splitSize;
        return split;
    }

    public void accept(Army other) {
        Validate.isTrue(this.getOwner().equals(other.getOwner()), "Cannot merge different Players' armies");

        this.size += other.getSize();
    }

    public void join(Army other) {
        other.accept(this);
    }

    public void damage(int damage) {
        if (damage > getSize()) {
            log.warn("Trying to inflict more damage ({}) than size of army {}", damage, this);
            damage = getSize();
        }

        this.size -= damage;
        if (isDefeated()) {
            log.info("Army defeated: {}", this);
            this.location = Territory.NOWHERE;
        }
    }

    public boolean canSplit() {
        return size > 1;
    }

    public boolean isDefeated() {
        return size < 1;
    }

    @Override
    public String asPlantUML() {
        String territoryName = location.getName();
        return "" + territoryName + " : owner = " + owner.getName() + System.lineSeparator()
            + territoryName + " : size = " + size + System.lineSeparator();
    }
}
