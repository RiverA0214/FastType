package com.example.fasttype;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private final List<String> words = Arrays.asList(
            "java", "cockroach", "keyboard", "challenge", "programing", "computer", "aesthetic", "shrimp", "error", "the", "quick", "brown", "fox", "jump", "lazy", "silent", "over", "dog", "lorem", "ipsum", "dolore"
    );
    private final Random random = new Random();

    private String currentWord;
    private int correctCount = 0;
    private int level = 1;

    public String getRandomWord() {
        currentWord = words.get(random.nextInt(words.size()));
        return currentWord;
    }

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


    public int getLevel() {
        return level;
    }

}
