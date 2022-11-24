package com.example.nurabotaypls;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("clientStart.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);

        Controller myController = fxmlLoader.getController();
        myController.setStage(stage);

        stage.setTitle("Aucion!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}