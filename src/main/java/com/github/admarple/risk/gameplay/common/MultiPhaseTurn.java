package com.github.admarple.risk.gameplay.common;

import java.util.List;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Turn;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MultiPhaseTurn implements Turn<MultiPhaseTurnCommand> {

    @NonNull final Player player;
    @NonNull final List<Phase> phases;

    @Override
    public MultiPhaseTurnCommand getCommand() {
        return new MultiPhaseTurnCommand();
    }

    @Override
    public void perform(MultiPhaseTurnCommand command) {
        log.info("performing turn for {}", player.getName());
        phases.forEach(Phase::play);
    }
}
