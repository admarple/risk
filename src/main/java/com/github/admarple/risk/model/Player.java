package com.github.admarple.risk.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Player {
    @NonNull String name;
    @NonNull List<Card> hand = new LinkedList<>();
    @NonNull Map<Territory, Army> armies = new HashMap<>();
    private boolean eliminated = false;
}
