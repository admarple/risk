package com.github.admarple.risk.model;

import com.github.admarple.risk.gameplay.core.Command;
import com.github.admarple.risk.gameplay.core.UserPromptCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class InvasionCommand implements Command, UserPromptCommand {

    int minimum;
    int maximum;

    public int getInvadingTroops() {
        if (minimum == maximum) {
            log.info("The only option is {}", minimum);
            return minimum;
        }
        while (true) {
            String requested = userInput();
            try {
                int requestedNumTroops = Integer.parseInt(requested);
                if (requestedNumTroops < minimum || requestedNumTroops > maximum) {
                    log.warn("Must be between {} and {}", minimum, maximum);
                    requestedNumTroops = minimum;
                }
                return requestedNumTroops;
            } catch (NumberFormatException e) {
                log.warn("Not a valid number.  Please try again.", e);
            }
        }
    }

    @Override
    public String userPrompt() {
        return "Invade with how many troops (minimum " + minimum +", maximum " + maximum + ")?";
    }
}
