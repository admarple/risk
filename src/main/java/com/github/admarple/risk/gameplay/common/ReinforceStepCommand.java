package com.github.admarple.risk.gameplay.common;

import java.util.stream.Collectors;

import com.github.admarple.risk.gameplay.StepCommand;
import com.github.admarple.risk.gameplay.core.UserPromptCommand;
import com.github.admarple.risk.model.Army;
import com.github.admarple.risk.model.Player;
import com.github.admarple.risk.model.Territory;

import lombok.Data;
import lombok.NonNull;

@Data
public class ReinforceStepCommand implements StepCommand, UserPromptCommand {

    @NonNull private final Player player;

    public Army getArmy() {
        return player.getHoldingPool().split(1);
    }

    public Territory getTerritory() {
        String requested = userInput();
        return player.getArmies().stream()
            .map(Army::getLocation)
            .filter(t -> t.getName().equals(requested))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String userPrompt() {
        return player.getName() + ", enter the name of the territory you want to reinforce.  Currently available: "
            + player.getArmies().stream()
            .map(Army::getLocation)
            .map(Territory::getName)
            .collect(Collectors.joining(", "));
    }
}
