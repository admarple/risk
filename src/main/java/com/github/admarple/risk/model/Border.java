package com.github.admarple.risk.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Border {
    @NonNull private Territory first;
    @NonNull private Territory second;

    public Border opposite() {
        return new Border(second, first);
    }
}
