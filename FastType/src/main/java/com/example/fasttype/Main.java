package com.example.fasttype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the FastType application.
 * <p>
 * This class initializes and starts the JavaFX application by loading
 * the main game interface from the FXML file.
 */
public class Main extends Application {

    /**
     * Starts the FastType application.
     *
     * @param stage the primary stage used to display the game's main window
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("FastType - Escritura Rápida");
        stage.setScene(scene);
        stage.show();
    }
}
