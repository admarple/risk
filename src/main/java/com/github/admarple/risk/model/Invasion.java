package com.github.admarple.risk.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Invasion extends Movement {
    @NonNull private DiePool diePool;

    public Skirmish nextSkirmish() {
        return new Skirmish(this.getSource(), this.getDestination(), diePool);
    }

    public void onConquest() {

    }

    public void onRetreat() {

    }
}
