package com.github.admarple.risk.gameplay;

public class RollStep implements Step<RollCommand>{

    /**
     * The player has no option but to roll.
     *
     * @return
     */
    @Override
    public RollCommand getPlayerCommand() {
        return new RollCommand();
    }

    @Override
    public void executeCommand(RollCommand command, Game game) {

    }
}
