package com.github.admarple.risk.gameplay.core;

public interface Yieldable<T extends Command> extends Playable<T> {
    boolean isYielded();
}
