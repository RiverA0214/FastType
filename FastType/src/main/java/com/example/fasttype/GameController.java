package com.example.fasttype;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class GameController {

    @FXML private Label wordLabel;
    @FXML private TextField inputField;
    @FXML private Label feedbackLabel;
    @FXML private Label timerLabel;
    @FXML private Button validateButton;
    @FXML private Label infoLabel;
    private int correctStreak = 0;   // aciertos seguidos
    private int level = 1;           // nivel actual
    private boolean gameOver = false;

    private GameLogic gameLogic = new GameLogic();
    private Timeline timeline;
    private int timeLeft;
    private int totalTime = 0; // en segundos



    @FXML
    public void initialize() {
        // Cuando presione Enter en el TextField
        inputField.setOnAction(event -> validateWord());

        // Cuando presione el botón Submit
        validateButton.setOnAction(event -> validateWord());

        // Iniciar juego
        startNewRound();
    }

    private void startNewRound() {
        if (gameOver) return; // no hacer nada si el juego terminó
        String currentWord = gameLogic.getRandomWord();
        wordLabel.setText(currentWord);
        feedbackLabel.setText("");
        infoLabel.setText("");
        inputField.clear();

        // tiempo según nivel
        int roundTime = 20 - (level - 1) * 2;
        if (roundTime < 5) roundTime = 5;

        timeLeft = roundTime;
        timerLabel.setText("🕑 " + timeLeft);

        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            totalTime++;
            timerLabel.setText("🕑 " + timeLeft);
            if (timeLeft <= 0) {
                validateWord();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void validateWord() {
        if (timeline != null) {
            timeline.stop();
        }

        String input = inputField.getText();

        if (gameLogic.validateWord(input)) {
            feedbackLabel.setText("✅ Correcto!");
            correctStreak++;

            if (correctStreak % 5 == 0) { // cada 5 aciertos seguidos
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

    @FXML
    private void endGame() {
        gameOver = true;                       // marca que el juego terminó
        if (timeline != null) timeline.stop(); // detiene temporizador
        feedbackLabel.setText("❌ Incorrecto o tiempo agotado");
        infoLabel.setText("Nivel alcanzado: " + gameLogic.getLevel() +
                "\nTiempo Total de la partida: " + totalTime + " s");
        inputField.setDisable(true);
        validateButton.setDisable(true);
    }


    @FXML
    private void resetGame() {
        totalTime = 0;
        correctStreak = 0;
        level = 1;
        gameOver = false;
        if (timeline != null) timeline.stop();       // detener temporizador
        gameLogic = new GameLogic();                  // reinicia lógica
        feedbackLabel.setText("");
        infoLabel.setText("");
        inputField.clear();
        inputField.setDisable(false);
        validateButton.setDisable(false);
        startNewRound();                              // inicia primer nivel
    }



}
