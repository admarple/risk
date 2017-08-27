package com.github.admarple.risk.gameplay.core;

/**
 * A Playable represents a point in a game where a decision can be made, as well as the way to respond to that decision.
 *
 * An implementation of Playable likely has some information about the state of the game, and uses this information to
 * specify the type of Command to produce, as well as how to change the state of the game based on the command.
 *
 * @param <T> the type of command to be acted upon
 */
public interface Playable<T extends Command> {

    /**
     * Get a command indicating what to do next.
     *
     * The simplest implementation might be always returning the same Command, representing a situation in which
     * there is only one way to proceed.
     *
     * A more complex implementation might be waiting for a user to choose an option.
     *
     * @return
     */
    T getCommand();

    /**
     * Apply the changes specified in command.
     *
     * @param command
     */
    void perform(T command);

    /**
     *
     */
    default void play() {
        perform(getCommand());
    }
}
