package com.github.admarple.risk.model;

import org.apache.commons.lang3.Validate;

import lombok.Data;
import lombok.NonNull;

@Data
public class Army {
    int size;
    @NonNull Territory location;
    @NonNull Player owner;

    public Army split(int splitSize) {
        Validate.isTrue(splitSize < getSize());

        Army split = new Army(getLocation(), getOwner());
        split.setSize(splitSize);
        setSize(getSize() - splitSize);
        return split;
    }
    public void accept(Army other) {
        Validate.isTrue(this.getOwner().equals(other.getOwner()), "");

        setSize(getSize() + other.size);
    }
    public void join(Army other) {
        other.accept(this);
    }
}
