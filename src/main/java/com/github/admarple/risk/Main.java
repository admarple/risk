package com.github.admarple.risk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.model.Board;
import com.github.admarple.risk.model.Border;
import com.github.admarple.risk.model.DiePool;
import com.github.admarple.risk.model.Direction;
import com.github.admarple.risk.model.GameMap;
import com.github.admarple.risk.model.Player;
import com.github.admarple.risk.model.ReinforcementRule;
import com.github.admarple.risk.model.Territory;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("About to play");
        Player aegon = new Player("Aegon");
        Player torrhen = new Player("Torrhen");

        log.info("Players created: {}, {}", aegon, torrhen);

        GameMap map = generateMap();
        log.info("Map created: {}", map);
        Board board = new Board(map, new DiePool());
        log.info("Board created: {}", board);
        Risk risk = new Risk(board, Arrays.asList(aegon, torrhen));
        log.info("Game created: {}", risk);

        risk.play();

        log.info("Game finished: {}", risk);
        System.out.println("As PlantUML: ");
        System.out.println(risk.asPlantUML());
        System.out.println("Try viewing as SVG at: " + writeSVG(risk).toURI());
    }

    public static File writeSVG(Risk risk) {
        String fileName = "build/board.svg";

        SourceStringReader reader = new SourceStringReader(risk.asPlantUML());
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            reader.outputImage(fos, new FileFormatOption(FileFormat.SVG));
        }
        catch (IOException e) {
            log.warn("Unable to ");
        }

        return new File(fileName);
    }

    private static GameMap generateMap() {
        Territory westeros = new Territory("Westeros");
        Territory valyria = new Territory("Valyria");
        Border b = new Border(westeros, valyria);
        b.setDirection(Direction.RIGHT);

        ReinforcementRule reinforcementRule = new ReinforcementRule() { };

        GameMap map = new GameMap(reinforcementRule);
        map.getTerritories().add(westeros);
        map.getTerritories().add(valyria);
        map.addBorder(b);
        return map;
    }
}
