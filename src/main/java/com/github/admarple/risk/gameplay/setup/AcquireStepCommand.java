package com.github.admarple.risk.gameplay.setup;

import java.util.stream.Collectors;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.gameplay.StepCommand;
import com.github.admarple.risk.gameplay.core.UserPromptCommand;
import com.github.admarple.risk.model.Army;
import com.github.admarple.risk.model.Player;
import com.github.admarple.risk.model.Territory;

import lombok.Data;
import lombok.NonNull;

@Data
public class AcquireStepCommand implements StepCommand, UserPromptCommand {

    @NonNull private final Risk risk;
    @NonNull private final Player player;

    public Territory getTerritory() {
        String requested = userInput();
        return risk.getBoard().getMap().getTerritories().stream()
            .filter(t -> t.getName().equals(requested))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String userPrompt() {
        return player.getName() + ", enter the name of the territory you want to capture.  Currently available: "
            + risk.getBoard().getUnoccupiedTerritories().stream()
            .map(Territory::getName)
            .collect(Collectors.joining(", "));
    }
}
