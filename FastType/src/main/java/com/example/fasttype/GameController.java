package com.example.fasttype;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Controller class for the FastType game UI.
 * <p>
 * Manages player input, validates typed words, updates the timer, handles levels,
 * and coordinates communication between the interface and the game logic.
 */
public class GameController {

    @FXML private Label wordLabel;
    @FXML private TextField inputField;
    @FXML private Label feedbackLabel;
    @FXML private Label timerLabel;
    @FXML private Button validateButton;
    @FXML private Label infoLabel;

    private int correctStreak = 0;
    private int level = 1;
    private boolean gameOver = false;

    private GameLogic gameLogic = new GameLogic();
    private Timeline timeline;
    private int timeLeft;
    private int totalTime = 0;

    /**
     * Initializes the controller when the UI is loaded.
     * <p>
     * Sets up event listeners for user input and starts the first game round.
     */
    @FXML
    public void initialize() {
        inputField.setOnAction(event -> validateWord());
        validateButton.setOnAction(event -> validateWord());
        startNewRound();
    }

    /**
     * Starts a new round by generating a new word, resetting the timer,
     * and preparing the UI for the next input.
     */
    private void startNewRound() {
        if (gameOver) return;

        String currentWord = gameLogic.getRandomWord();
        wordLabel.setText(currentWord);
        feedbackLabel.setText("");
        infoLabel.setText("");
        inputField.clear();

        int roundTime = 20 - (level - 1) * 2;
        if (roundTime < 5) roundTime = 5;

        timeLeft = roundTime;
        timerLabel.setText("🕑 " + timeLeft);

        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            totalTime++;
            timerLabel.setText("🕑 " + timeLeft);
            if (timeLeft <= 0) validateWord();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Validates the player's typed word using {@link GameLogic}.
     * <p>
     * If the word is correct, the player may level up. Otherwise, the game ends.
     */
    @FXML
    private void validateWord() {
        if (timeline != null) timeline.stop();

        String input = inputField.getText();

        if (gameLogic.validateWord(input)) {
            feedbackLabel.setText("✅ Correcto!");
            correctStreak++;

            if (correctStreak % 5 == 0) {
                level++;
                feedbackLabel.setText("🚀 ¡Nivel " + level + " alcanzado!");
            }

        } else {
            endGame();
        }

        inputField.clear();

        Timeline pause = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> startNewRound())
        );
        pause.setCycleCount(1);
        pause.play();
    }

    /**
     * Ends the game by stopping timers, locking input, and showing results.
     */
    @FXML
    private void endGame() {
        gameOver = true;
        if (timeline != null) timeline.stop();

        feedbackLabel.setText("❌ Incorrecto o tiempo agotado");
        infoLabel.setText(
                "Nivel alcanzado: " + gameLogic.getLevel() +
                        "\nTiempo Total de la partida: " + totalTime + " s"
        );

        inputField.setDisable(true);
        validateButton.setDisable(true);
    }

    /**
     * Resets the game state, clears progress, re-enables input,
     * and starts a new game from level 1.
     */
    @FXML
    private void resetGame() {
        totalTime = 0;
        correctStreak = 0;
        level = 1;
        gameOver = false;

        if (timeline != null) timeline.stop();

        gameLogic = new GameLogic();
        feedbackLabel.setText("");
        infoLabel.setText("");
        inputField.clear();
        inputField.setDisable(false);
        validateButton.setDisable(false);

        startNewRound();
    }
}
