package com.github.admarple.risk.gameplay;

public interface Step<T extends Command> {
    T getPlayerCommand();
    void executeCommand(T command, Game game);
}
