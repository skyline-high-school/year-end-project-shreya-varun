package com.example.calculatorgame;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LeaderboardController {
    @FXML
    Button previousButton;
    @FXML
    Button nextButton;
    @FXML
    GridPane leaderboard;
    Text[][] textGrid = new Text[10][3];
    int page = 0;
    @FXML
    public void goBack() {
        HelloApplication.stage.setScene(HelloApplication.welcome);
    }
    @FXML
    public void previous() {
        page--;
        refreshLeaderboards();
        if (page == 0) previousButton.setDisable(true);
        nextButton.setDisable(false);
    }
    @FXML
    public void next() {
        page++;
        refreshLeaderboards();
        if (Math.ceil(Leaderboard.records.size() / 10.0) - 1 == page) nextButton.setDisable(true);
        previousButton.setDisable(false);
    }
    @FXML
    public void initialize() {
        for (int row=1;row<=10;row++) {
            for (int col=0;col<=2;col++) {
                Text text = new Text();
                textGrid[row-1][col] = text;

                leaderboard.add(text, col, row);
                text.setFont(Font.font(16));
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, javafx.geometry.VPos.CENTER);
            }
        }
        resetLeaderboards();
    }
    public void resetLeaderboards() {
        page = 0;
        previousButton.setDisable(true);
        nextButton.setDisable(Leaderboard.records.size() <= 10);
        refreshLeaderboards();
    }
    public void refreshLeaderboards() {
        int playersLength = Leaderboard.records.size();
        for (int i=0;i<textGrid.length;i++) {
            int index = page * 10 + i;
            if (index + 1 > playersLength) {
                for (Text text : textGrid[i]) text.setText("");
            } else {
                textGrid[i][0].setText(String.valueOf(index + 1));
                textGrid[i][1].setText(Leaderboard.records.get(index).username);
                textGrid[i][2].setText(Calculator.decimalFormat.format(Leaderboard.records.get(index).time));
            }
        }
    }
}
