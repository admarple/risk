package com.github.admarple.risk.gameplay.core;

/**
 * A game in which only one player is able to take an action at any given time.
 *
 * Perhaps "turn-based" would have been a better name, but "single actor" was chosen to avoid confusion with
 * {@link com.github.admarple.risk.gameplay.Turn}.
 *
 * @param <T>
 */
public interface SingleActorGame<T extends Playable> {
    default void play() {
        while(!gameOver()) {
            T next = nextPlayable();
            next.play();
        }
    }

    T nextPlayable();
    boolean gameOver();
}
