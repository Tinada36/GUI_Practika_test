package com.example.gui_practika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("KinderGardenUI.fxml")));
        primaryStage.setTitle("KinderGarden Application");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
        System.out.println("all good");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
