package com.github.admarple.risk.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Border {
    @NonNull Territory first;
    @NonNull Territory second;

    public Border opposite() {
        return new Border(second, first);
    }
}
