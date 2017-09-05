package com.github.admarple.risk.gameplay.conquest;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import com.github.admarple.risk.gameplay.StepCommand;
import com.github.admarple.risk.gameplay.core.UserPromptCommand;
import com.github.admarple.risk.model.Card;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;

@Data
public class ExchangeStepCommand implements StepCommand, UserPromptCommand {
    private static final String PASS = "pass";

    @NonNull private final Player player;

    @Override
    public String userPrompt() {
        StringBuilder prompt = new StringBuilder("Exchange a set of cards, or 'pass' to skip?  Your hand: ");
        player.getHand().forEach(card -> {
            prompt.append("  ");
            prompt.append(card.getTerritory().getName());
            prompt.append(" (");
            prompt.append(card.getType());
            prompt.append(")");
            prompt.append(System.lineSeparator());
        });
        return prompt.toString();
    }

    public Optional<Set<Card>> getTradeIn() {
        String requested = userInput();
        if (PASS.equals(requested)) {
            return Optional.empty();
        }

        return Optional.of(Arrays.stream(requested.split(","))
            .map(name -> player.getHand().stream()
                .filter(card -> name.equals(card.getTerritory().getName()))
                .findFirst())
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toSet()));
    }
}
