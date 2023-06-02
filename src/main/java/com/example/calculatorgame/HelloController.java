package com.example.calculatorgame;

import javafx.fxml.FXML;

public class HelloController {
    @FXML
    protected void startGame() {
        HelloApplication.gameController.startGame();
        HelloApplication.stage.setScene(HelloApplication.game);
    }
    @FXML
    protected void openHelp() { HelloApplication.stage.setScene(HelloApplication.help); }
    @FXML
    protected void showLeaderboards() {
        HelloApplication.leaderboardController.resetLeaderboards();
        HelloApplication.stage.setScene(HelloApplication.leaderboards);
    }
}