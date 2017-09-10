package com.github.admarple.risk.model;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.core.Playable;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Invasion extends Movement implements Playable<InvasionCommand> {
    @NonNull private final Risk risk;

    public Invasion(Army source, Army destination, Risk risk) {
        super(source, destination);
        this.risk = risk;
    }

    public Skirmish nextSkirmish(int numInvadingTroops) {
        return new Skirmish(this.getSource(), numInvadingTroops, this.getDestination(), risk.getBoard().getDiePool());
    }

    @Override
    public InvasionCommand getCommand() {
        return new InvasionCommand(1, Skirmish.MAX_INVADER_DICE);
    }

    @Override
    public void perform(InvasionCommand command) {
        Skirmish lastSkirmish = null;
        Territory destinationLocation = getDestination().getLocation();
        while (getSource().canSplit() && !getDestination().isDefeated()) {
            int numInvadingTroops = getCommand().getInvadingTroops();

            if (numInvadingTroops == 0) {
                log.info("User decided to call off the invasion.");
                return;
            }

            lastSkirmish = nextSkirmish(numInvadingTroops);
            lastSkirmish.execute();
        }

        if (getDestination().isDefeated()) {
            assert lastSkirmish != null; // defending army is only defeated as a result of a Skirmish
            log.info("Conquered {}", getDestination().getLocation().getName());
            int numMovingTroops = new InvasionCommand(lastSkirmish.getNumInvaders(), getSource().getSize() - 1).getInvadingTroops();
            Army invadingArmy = getSource().split(numMovingTroops);
            risk.getBoard().acquire(invadingArmy, destinationLocation);
        }
    }
}
