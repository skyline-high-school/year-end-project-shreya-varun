package com.example.calculatorgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.text.DecimalFormat;

public class Calculator {
    @FXML
    Label calculator;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String number1 = "0";
    String operation = "";
    String number2 = "";
    @FXML
    private void addNumber(ActionEvent event) {
        String number = ((Button)event.getSource()).getText();

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
            case "1/x":
                number = 1 / number;
                break;
            case "+/-":
                number *= -1;
                break;
        }

        setFirstNumber(number);
    }
    @FXML
    private void useSpecialFunction(ActionEvent event) {
        String function = ((Button)event.getSource()).getText();
        switch (function) {
            case "C":
                setFirstNumber(0);
                break;
            case "DEL":
                if (operation.isEmpty()) number1 = number1.equals("0") ? "" : "0";
                else if (number2.isEmpty()) operation = "";
                else number2 = number2.equals("0") ? "" : "0";
                updateDisplay();
                break;
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
            case "+":
                number += number2;
                break;
            case "-":
                number -= number2;
                break;
            case "*":
                number *= number2;
                break;
            case "/":
                number /= number2;
                break;
        }

        setFirstNumber(number);
    }
    private void setFirstNumber(double number) {
        if (Double.isNaN(number)) number = 0;
        else number = Math.max(-1_000_000, Math.min(1_000_000, number));

        this.number1 = decimalFormat.format(number);
        operation = "";
        this.number2 = "";
        updateDisplay();
    }
    private void updateDisplay() {
        calculator.setText(number1 + " " + operation + " " + number2);
    }
}
