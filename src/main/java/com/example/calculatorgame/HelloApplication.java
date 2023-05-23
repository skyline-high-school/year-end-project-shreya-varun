package com.example.calculatorgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        SceneManager.loadEnums();

        stage.getIcons().add(new Image("file:assets/calculatrice.png"));
        stage.setTitle("Calculator");
        SceneManager.switchScene(SceneName.WELCOME);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}