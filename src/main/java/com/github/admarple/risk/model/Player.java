package com.github.admarple.risk.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class Player {
    @NonNull private String name;
    private final List<Card> hand = new LinkedList<>();
    private final Map<Territory, Army> armies = new HashMap<>();
    private boolean eliminated = false;
}
