package com.example.calculatorgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    public static Scene welcome;
    public static Scene game;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root1 = loader1.load();
        welcome = new Scene(root1, 256, 256);
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("calculator.fxml"));
        Parent root2 = loader2.load();
        game = new Scene(root2, 256, 256);

        stage.getIcons().add(new Image("file:assets/calculatrice.png"));
        stage.setTitle("Calculator");
        stage.setScene(welcome);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}