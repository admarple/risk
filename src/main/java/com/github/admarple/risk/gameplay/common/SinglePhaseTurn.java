package com.github.admarple.risk.gameplay.common;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Turn;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;

// TODO: Look at de-duping with SingleStepPhase, e.g. into abstract class SingleNestedPlayable
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
        phase.play();
    }
}
