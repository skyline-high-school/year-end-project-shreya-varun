package com.example.calculatorgame;

import javafx.fxml.FXML;

public class WinnerController {
    @FXML
    public void startOver() {
        HelloApplication.stage.setScene(HelloApplication.welcome);
    }
}

