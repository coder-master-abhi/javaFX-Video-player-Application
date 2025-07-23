package com.videoplayer.controller;

import com.videoplayer.model.VideoFile;
import com.videoplayer.service.VideoPlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class VideoPlayerController implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private ListView<VideoFile> historyListView;

    @FXML
    private MenuItem historyMenuItem; 

    private MediaPlayer mediaPlayer;
    private final VideoPlayerService videoService = new VideoPlayerService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        videoService.loadHistoryFromFile();
        loadHistoryIntoListView();

        historyListView.setOnMouseClicked(event -> {
            VideoFile selected = historyListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                playVideoFromPath(selected.getPath());
            }
        });

        // 🔹 Connect "View History" menu item to open history scene
        if (historyMenuItem != null) {
            historyMenuItem.setOnAction(event -> showHistoryScene());
        }
    }

    @FXML
    private void handleOpenFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Video File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String filePath = file.getAbsolutePath();
            videoService.addToHistory(filePath);
            playVideoFromPath(filePath);
            loadHistoryIntoListView();
        }
    }

    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    private void playVideoFromPath(String filePath) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            File videoFile = new File(filePath);
            if (!videoFile.exists()) {
                System.err.println("File not found: " + filePath);
                return;
            }

            String uri = videoFile.toURI().toString();
            Media media = new Media(uri);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error playing video: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void loadHistoryIntoListView() {
        List<VideoFile> historyFiles = new ArrayList<>();
        for (String path : videoService.getVideoHistory()) {
            File file = new File(path);
            if (file.exists()) {
                historyFiles.add(new VideoFile(file.getName(), path));
            }
        }
        historyListView.getItems().setAll(historyFiles);
    }

    // 🔹 Method to show history scene
    private void showHistoryScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/history.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load history scene.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
