module com.example.calculatorgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;


    opens com.example.calculatorgame to javafx.fxml;
    exports com.example.calculatorgame;
}