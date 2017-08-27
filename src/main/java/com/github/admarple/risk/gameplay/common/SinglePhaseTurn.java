package com.github.admarple.risk.gameplay.common;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Turn;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SinglePhaseTurn<T extends Phase> extends SingleNestedPlayable<SinglePhaseTurnCommand, T> {

    @NonNull final Player player;

    public SinglePhaseTurn(Player player, T phase) {
        super(phase, SinglePhaseTurnCommand::new);
        this.player = player;
    }

    @Override
    public void perform(SinglePhaseTurnCommand command) {
        log.info("performing turn for {}", player.getName());
        super.perform(command);
    }
}
