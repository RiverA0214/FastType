package com.example.fasttype;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Handles the core logic of the FastType game.
 * <p>
 * This class is responsible for providing random words, validating player input,
 * and tracking the player's progress such as correct words and level.
 */
public class GameLogic {

    private final List<String> words = Arrays.asList(
            "java", "cockroach", "keyboard", "challenge", "programing", "computer",
            "aesthetic", "shrimp", "error", "the", "quick", "brown", "fox",
            "jump", "lazy", "silent", "over", "dog", "lorem", "ipsum", "dolore"
    );
    private final Random random = new Random();

    private String currentWord;
    private int correctCount = 0;
    private int level = 1;

    /**
     * Generates and returns a random word from the word list.
     *
     * @return a randomly selected word
     */
    public String getRandomWord() {
        currentWord = words.get(random.nextInt(words.size()));
        return currentWord;
    }

    /**
     * Validates the player's typed input against the current target word.
     * <p>
     * If the word is correct, the correct counter increases, and the level
     * increases every 5 correct words.
     *
     * @param input the word typed by the player
     * @return {@code true} if the input matches the current word, {@code false} otherwise
     */
    public boolean validateWord(String input) {
        if (input.equalsIgnoreCase(currentWord)) {
            correctCount++;
            if (correctCount % 5 == 0) {
                level++;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the current level of the player.
     *
     * @return the player's level
     */
    public int getLevel() {
        return level;
    }
}

