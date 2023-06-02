package com.example.calculatorgame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class WinnerController {
    @FXML
    Label finalTimeLabel;
    @FXML
    Label resultMessage;
    @FXML
    Button submitButton;
    @FXML
    TextField username;
    double finalTime;
    @FXML
    public void startOver() {
        HelloApplication.stage.setScene(HelloApplication.welcome);
    }
    public void setFinalTime(double finalTime) {
        this.finalTime = finalTime;
        submitButton.setDisable(false);
        finalTimeLabel.setText(Calculator.decimalFormat.format(finalTime) + "s");
        String resultMessage;
        if (finalTime < 80) resultMessage = "Incredible! Is it even possible?";
        else if (finalTime < 100) resultMessage = "Blazingly fast! Just like the Java programming language!";
        else if (finalTime < 120) resultMessage = "Great! That was an awesome run!";
        else if (finalTime < 140) resultMessage = "Not bad!";
        else if (finalTime < 160) resultMessage = "You can do better!";
        else if (finalTime < 180) resultMessage = "Try a little harder!";
        else if (finalTime < 240) resultMessage = "Yawn";
        else resultMessage = "Snails run faster than you";

        this.resultMessage.setText(resultMessage);
    }
    @FXML
    private void submitScore() throws IOException {
        String username = this.username.getText();
        submitButton.setDisable(true);
        Leaderboard.submitScore(username, finalTime);
    }
}

