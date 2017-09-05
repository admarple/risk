package com.github.admarple.risk.gameplay.core;

import java.util.Scanner;

/**
 * A Command used to capture user input.
 *
 * TODO: This interface is ugly just to get things working during experimentation.
 */
public interface UserPromptCommand extends Command {

    /**
     * TODO: This needs to be closed at some point
     * TODO: There might be a better place to manage this singleton than an interface field.
     */
    Scanner SCANNER = new Scanner(System.in);

    /**
     * The prompt to print to the user before obatining their request
     * @return
     */
    String userPrompt();

    /**
     * The scanner from which to read the user's input
     * @return
     */
    default Scanner getScanner() {
        return SCANNER;
    }

    /**
     * TODO: Instead of String, support arbitrary structured input
     * @return
     */
    default String userInput() {
        System.out.println(userPrompt());
        return getScanner().nextLine();
    }
}
