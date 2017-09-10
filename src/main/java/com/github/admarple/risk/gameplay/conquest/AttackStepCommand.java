package com.github.admarple.risk.gameplay.conquest;

import java.util.Optional;

import org.apache.commons.lang3.Validate;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.StepCommand;
import com.github.admarple.risk.gameplay.core.UserPromptCommand;
import com.github.admarple.risk.model.Army;
import com.github.admarple.risk.model.Border;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;

@Data
public class AttackStepCommand implements StepCommand, UserPromptCommand {
    public static final String PASS_COMMAND = "pass";

    @NonNull private Risk risk;
    @NonNull private Player attacker;

    public Optional<Choice> getChoice() {
        String requested = userInput();

        if (PASS_COMMAND.equals(requested)) {
            return Optional.empty();
        }

        String[] sourceAndDestination = requested.split(",");

        Validate.isTrue(sourceAndDestination.length == 2);

        Optional<Army> sourceArmy = risk.getBoard().getArmyByName(sourceAndDestination[0]);
        Optional<Army> destinationArmy = risk.getBoard().getArmyByName(sourceAndDestination[1]);
        Validate.isTrue(sourceArmy.isPresent());
        Validate.isTrue(destinationArmy.isPresent());

        // validate that the border exists
        Border border = new Border(sourceArmy.get().getLocation(), destinationArmy.get().getLocation());
        Validate.isTrue(risk.getBoard().getMap().getBorders().contains(border));
        // validate that the player owns the source, but not the destination
        Validate.isTrue(sourceArmy.get().getOwner().equals(attacker));
        Validate.isTrue(!destinationArmy.get().getOwner().equals(attacker));
        // validate that the player has enough troops in the source to attack
        Validate.isTrue(sourceArmy.get().canSplit());

        return Optional.of(new Choice(sourceArmy.get(), destinationArmy.get()));
    }

    @Override
    public String userPrompt() {
        return "Attack from where to where?  Separate names by a comma (,).";
    }

    @Data
    static class Choice {
        @NonNull private Army source;
        @NonNull private Army destination;
    }
}
