package com.github.admarple.risk.model;

import java.util.UUID;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Territory implements Plantable {
    public static final Territory NOWHERE = new Territory("Nowhere");

    public Territory(String name) {
        this(name, UUID.randomUUID().toString());
    }

    @NonNull String name;
    @NonNull String id;

    @Override
    public String asPlantUML() {
        return "object " + name + System.lineSeparator();
    }
}
