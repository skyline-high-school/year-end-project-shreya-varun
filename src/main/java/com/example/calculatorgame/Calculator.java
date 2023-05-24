package com.example.calculatorgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class Calculator {
    @FXML
    Label calculator;
    String number1 = "";
    String operation = "";
    String number2 = "";
    @FXML
    private void addNumber(ActionEvent event) {
        String number = ((Button)event.getSource()).getText();

        if (number1.isEmpty() || operation.isEmpty()) number1 = number;
        else number2 = number;
        updateDisplay();
    }
    @FXML
    private void setOperation(ActionEvent event) {
        String operation = ((Button)event.getSource()).getText();
        if (!this.operation.isEmpty()) evaluateEquation();
        this.operation = operation;
        updateDisplay();
    }
    @FXML
    private void evaluateEquation() {
        double number = 0;
        double number1 = Double.parseDouble(this.number1);
        double number2 = Double.parseDouble(this.number2);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");

        switch (operation) {
            case "+":
                number = number1 + number2;
                break;
            case "-":
                number = number1 - number2;
                break;
            case "*":
                number = number1 * number2;
                break;
            case "/":
                number = number1 / number2;
                break;
        }

        this.number1 = decimalFormat.format(number);
        operation = "";
        this.number2 = "";
        updateDisplay();
    }
    private void updateDisplay() {
        calculator.setText(number1 + " " + operation + " " + number2);
    }
}
