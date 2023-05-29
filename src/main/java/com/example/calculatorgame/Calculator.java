package com.example.calculatorgame;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.text.DecimalFormat;

public class Calculator {
    @FXML
    Label splash;
    @FXML
    GridPane buttonGrid;
    @FXML
    Label terminal;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String number1 = "0";
    String operation = "";
    String number2 = "";
    String memory = "0";
    public void startGame() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.8), event -> splash.setText("2")),
            new KeyFrame(Duration.seconds(1.6), event -> splash.setText("1")),
            new KeyFrame(Duration.seconds(2.4), event -> {
                splash.setText("GO!");
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), splash);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                buttonGrid.setVisible(true);
            })
        );
        timeline.play();
    }
    @FXML
    private void addNumber(ActionEvent event) { addNumber(((Button)event.getSource()).getText()); }
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
