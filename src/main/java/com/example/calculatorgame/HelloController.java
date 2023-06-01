package com.example.calculatorgame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void startGame() {
        //HelloApplication.gameController.startGame();
        HelloApplication.stage.setScene(HelloApplication.winner);
    }
    @FXML
    protected void openHelp() { HelloApplication.stage.setScene(HelloApplication.help); }
}