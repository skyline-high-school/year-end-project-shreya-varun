package com.example.calculatorgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    public static Scene welcome;
    public static Scene game;
    public static Calculator gameController;
    public static Scene help;
    public static Scene winner;
    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        welcome = loadFXML("hello-view.fxml");
        game = loadFXML("calculator.fxml");
        help = loadFXML("Page.fxml");
        winner = loadFXML("winner-page.fxml");

        stage.setResizable(false);
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("calculatrice.png")));
        stage.setTitle("Calculator");
        stage.setScene(welcome);
        stage.show();
    }
    public Scene loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        if (path.equals("calculator.fxml")) gameController = loader.getController();
        return new Scene(root, 512, 512);
    }
    public static void main(String[] args) {
        launch();
    }
}