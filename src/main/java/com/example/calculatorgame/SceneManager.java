package com.example.calculatorgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class SceneManager {
    static Dictionary<SceneName, Scene> sceneByName = new Hashtable<>();
    public static void loadEnums() {
        sceneByName.put(SceneName.WELCOME, loadScene("hello-view.fxml"));
        sceneByName.put(SceneName.WELCOME, loadScene("calculator.fxml"));
    }

    public static void switchScene(SceneName sceneName) {
        HelloApplication.stage.setScene(sceneByName.get(sceneName));
    }

    static Scene loadScene(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(path));
            fxmlLoader.load();
            return new Scene(fxmlLoader.load(), 256, 256);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}

