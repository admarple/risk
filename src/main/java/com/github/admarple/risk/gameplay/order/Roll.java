package com.github.admarple.risk.gameplay.order;

import org.apache.commons.lang3.Validate;

import com.github.admarple.risk.model.Die;

import lombok.Data;
import lombok.NonNull;

@Data
public class Roll {
    private State state = State.ROLL;
    @NonNull
    private Die die;

    public boolean isRolling() {
        return getState() == State.ROLL;
    }

    public boolean isDone() {
        return getState() == State.DONE;
    }

    public void roll() {
        Validate.validState(isRolling());

        die.roll();
        state = State.DONE;
    }

    public void startRolling() {
        state = State.ROLL;
    }

    public void stopRolling() {
        state = State.SKIP;
    }

    enum State {
        SKIP,
        ROLL,
        DONE
    }
}
