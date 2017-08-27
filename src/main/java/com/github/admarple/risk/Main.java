package com.github.admarple.risk;

import java.util.Arrays;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.model.Board;
import com.github.admarple.risk.model.Border;
import com.github.admarple.risk.model.DiePool;
import com.github.admarple.risk.model.GameMap;
import com.github.admarple.risk.model.Player;
import com.github.admarple.risk.model.ReinforcementRule;
import com.github.admarple.risk.model.Territory;

public class Main {
    public static void main(String[] args) {
        Player aegon = new Player("Aegon");
        Player torrhen = new Player("Torrhen");

        GameMap map = generateMap();
        Board board = new Board(map, new DiePool());
        Risk risk = new Risk(board, Arrays.asList(aegon, torrhen));
        risk.play();
    }

    private static GameMap generateMap() {
        Territory westeros = new Territory("Westeros");
        Territory valyria = new Territory("Valyria");
        Border b = new Border(westeros, valyria);

        ReinforcementRule reinforcementRule = new ReinforcementRule() { };

        GameMap map = new GameMap(reinforcementRule);
        map.getTerritories().add(westeros);
        map.getTerritories().add(valyria);
        map.addBorder(b);
        return map;
    }
}
