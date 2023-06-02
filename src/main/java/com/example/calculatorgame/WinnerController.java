package com.example.calculatorgame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WinnerController {
    @FXML
    Label finalTimeLabel;
    @FXML
    Label resultMessage;
    @FXML
    Button submitButton;
    @FXML
    TextField username;
    Map<String, Double> records = new HashMap<>();
    BufferedWriter leaderboardWriter;
    double finalTime;
    public WinnerController() throws IOException {
        String path = "src/main/resources/com/example/calculatorgame/leaderboard.txt";
        List<String> rawRecords = Files.readAllLines(Paths.get(path));
        for (int i=0;i<rawRecords.size();i+=2) {
            records.put(rawRecords.get(i), Double.parseDouble(rawRecords.get(i+1)));
        }
        leaderboardWriter = new BufferedWriter(new FileWriter(path, true));
    }
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
        leaderboardWriter.newLine();
        leaderboardWriter.write(username);
        leaderboardWriter.newLine();
        leaderboardWriter.write(Calculator.decimalFormat.format(finalTime));
        leaderboardWriter.flush();
        records.put(username, finalTime);
    }
}

