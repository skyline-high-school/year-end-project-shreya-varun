module com.example.calculatorgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calculatorgame to javafx.fxml;
    exports com.example.calculatorgame;
}