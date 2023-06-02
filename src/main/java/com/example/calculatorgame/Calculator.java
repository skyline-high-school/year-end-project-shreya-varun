package com.example.calculatorgame;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.Random;

public class Calculator {
    @FXML
    Label splash;
    @FXML
    GridPane buttonGrid;
    @FXML
    Label terminal;
    @FXML
    Label tally;
    @FXML
    Label target;
    @FXML
    ImageView checkbox;
    @FXML
    Label timerLabel;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            double elapsed = (now - startTime) / 1_000_000_000.0;
            timerLabel.setText(String.format("%.2fs", Math.max(elapsed, 0)));
        }
    };
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    long startTime;
    Random random = new Random();
    int round;
    final int lastRound = 10;

    String targetNumber = "";
    String number1 = "0";
    String operation = "";
    String number2 = "";
    String memory = "0";
    @FXML
    private void goBack() { HelloApplication.stage.setScene(HelloApplication.welcome); }
    private void resetGame() {
        splash.setOpacity(1);
        splash.setText("3");
        buttonGrid.setVisible(false);
        targetNumber = "";
        number1 = "0";
        operation = "";
        number2 = "";
        round = 0;
        target.setText("Target:");
        tally.setText("");
        terminal.setText("");
        timer.stop();
        timerLabel.setText("");
    }
    public void startGame() {
        resetGame();
        FadeTransition splashFade = new FadeTransition(Duration.seconds(0.5), splash);
        splashFade.setFromValue(1);
        splashFade.setToValue(0);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.8), event -> splash.setText("2")),
            new KeyFrame(Duration.seconds(1.6), event -> splash.setText("1")),
            new KeyFrame(Duration.seconds(2.4), event -> {
                splash.setText("GO!");
                splashFade.play();
                buttonGrid.setVisible(true);
                newRound();
                startTime = System.nanoTime();
                timer.start();
            })
        );
        timeline.play();
    }
    public void newRound() {
        round++;
        if (round > lastRound) {
            endGame();
            return;
        }
        tally.setText(round + "/" + lastRound);

        // Rounds 1-3 up to 100, 4-6 up to 1000, 7-9 up to 10000, 10 is up to 100 with a decimal
        double potentialTarget;
        if (round <= 3) potentialTarget = random.nextInt(100) + 1;
        else if (round <= 6) potentialTarget = random.nextInt(1000) + 1;
        else if (round <= 9) potentialTarget = random.nextInt(10000) + 1;
        else potentialTarget = random.nextDouble(100) + 1;
        // 1/5 chance to make the number negative
        potentialTarget *= random.nextInt(5) == 0 ? -1 : 1;

        targetNumber = decimalFormat.format(potentialTarget);
        target.setText("Target: " + targetNumber);
    }
    public void endGame() {
        double finalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        HelloApplication.winnerController.setFinalTime(finalTime);
        HelloApplication.stage.setScene(HelloApplication.winner);
    }
    @FXML
    private void addNumber(ActionEvent event) { addNumber(((Button) event.getSource()).getText()); }
    private void addNumber(String number) {
        if (operation.isEmpty()) number1 = number1.equals("0.") ? "0." + number : number;
        else number2 = number2.equals("0.") ? "0." + number : number;
        updateDisplay();
    }
    @FXML
    private void setOperation(ActionEvent event) {
        String operation = ((Button)event.getSource()).getText();

        if (!this.operation.isEmpty() && !number2.isEmpty()) evaluateEquation();
        this.operation = operation;
        if (number1.equals("0.")) number1 = "0";
        updateDisplay();
    }
    @FXML
    private void applyInstantFunction(ActionEvent event) {
        String function = ((Button)event.getSource()).getText();
        evaluateEquation();
        double number = Double.parseDouble(this.number1);

        switch (function) {
            case "1/x" -> number = 1 / number;
            case "+/-" -> number *= -1;
            case "round" -> number = Math.round(number);
            case "x^2" -> number = Math.pow(number, 2);
            case "2^x" -> number = Math.pow(2, number);
            case "sqrtx" -> number = Math.sqrt(number);
        }

        setFirstNumber(number);
    }
    @FXML
    private void useSpecialFunction(ActionEvent event) {
        String function = ((Button)event.getSource()).getText();
        switch (function) {
            case "C" -> setFirstNumber(0);
            case "AC" -> {
                setFirstNumber(0);
                memory = "0";
            }
            case "DEL" -> {
                if (operation.isEmpty()) number1 = number1.equals("0") ? "" : "0";
                else if (number2.isEmpty()) operation = "";
                else number2 = number2.equals("0") ? "" : "0";
                updateDisplay();
            }
        }
    }
    @FXML
    private void executeMemory(ActionEvent event) {
        String function = ((Button) event.getSource()).getText();
        switch (function) {
            case "MC" -> memory = "0";
            case "MR" -> addNumber(memory);
            case "M+" -> {
                evaluateEquation();
                double newMemory = constrainNumber(Double.parseDouble(number1) + Double.parseDouble(memory));
                memory = decimalFormat.format(newMemory);
            }
            case "M-" -> {
                evaluateEquation();
                double newMemory = constrainNumber(Double.parseDouble(number1) - Double.parseDouble(memory));
                memory = decimalFormat.format(newMemory);
            }
        }
    }
    @FXML
    private void addDecimal() {
        if (operation.isEmpty()) number1 = "0.";
        else number2 = "0.";
        updateDisplay();
    }
    @FXML
    private void runTargetCheck() {
        evaluateEquation();
        if (!number1.equals(targetNumber)) return;

        newRound();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), checkbox);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }
    private void evaluateEquation() {
        if (number2.isEmpty()) {
            operation = "";
            updateDisplay();
            return;
        }
        double number = Double.parseDouble(this.number1);
        double number2 = Double.parseDouble(this.number2);

        switch (operation) {
            case "+" -> number += number2;
            case "-" -> number -= number2;
            case "*" -> number *= number2;
            case "/" -> number /= number2;
        }

        setFirstNumber(number);
    }
    private void setFirstNumber(double number) {
        number = constrainNumber(number);

        this.number1 = decimalFormat.format(number);
        operation = "";
        this.number2 = "";
        updateDisplay();
    }
    private double constrainNumber(double number) { return Double.isNaN(number) ? 0 : Math.max(-1_000_000, Math.min(1_000_000, number)); }
    private void updateDisplay() { terminal.setText(number1 + " " + operation + " " + number2); }
}
