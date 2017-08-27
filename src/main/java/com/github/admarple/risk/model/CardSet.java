package com.github.admarple.risk.model;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class CardSet {
    @NonNull private List<Card> cards;

    public boolean isValid() {
        long numberOfTypes = cards.stream().map(Card::getType).distinct().count();
        return cards.size() == 3 && (numberOfTypes == 1 || numberOfTypes == 3);
    }
}
