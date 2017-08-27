package com.github.admarple.risk.gameplay.common;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Turn;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

// TODO: Look at de-duping with SingleStepPhase, e.g. into abstract class SingleNestedPlayable
@Slf4j
@Data
public class SinglePhaseTurn<T extends Phase> implements Turn<SinglePhaseTurnCommand> {

    @NonNull final Player player;
    @NonNull final T phase;

    @Override
    public SinglePhaseTurnCommand getCommand() {
        return new SinglePhaseTurnCommand();
    }

    @Override
    public void perform(SinglePhaseTurnCommand command) {
        log.info("performing turn for {}", player.getName());
        phase.play();
    }
}
