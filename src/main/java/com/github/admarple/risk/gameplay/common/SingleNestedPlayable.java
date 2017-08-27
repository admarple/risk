package com.github.admarple.risk.gameplay.common;

import java.util.function.Supplier;

import com.github.admarple.risk.gameplay.core.Command;
import com.github.admarple.risk.gameplay.core.Playable;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SingleNestedPlayable<TC extends Command, U extends Playable> implements Playable<TC> {

    @NonNull final U nested;
    @NonNull final Supplier<TC> commandSupplier;

    @Override
    public TC getCommand() {
        return commandSupplier.get();
    }

    @Override
    public void perform(TC command) {
        log.info("performing {}", getClass().getSimpleName());
        nested.play();
    }
}
