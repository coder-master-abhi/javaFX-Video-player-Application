package com.videoplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VideoPlayerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        Parent root = loader.load();

        // Set up stage
        primaryStage.setTitle("JavaFX Video Player");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
