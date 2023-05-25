package com.example.calculatorgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void startGame() {
        HelloApplication.stage.setScene(HelloApplication.game);
    }
    @FXML
    protected void openHelp() { HelloApplication.stage.setScene(HelloApplication.help); }
}git chec