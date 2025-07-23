package com.videoplayer.controller;

import com.videoplayer.model.VideoFile;
import com.videoplayer.service.VideoPlayerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryController {

    @FXML
    private ListView<VideoFile> historyListView;

    @FXML
    private Button backButton;

    private final VideoPlayerService videoService = new VideoPlayerService();

    @FXML
    private void initialize() {
        loadHistory();
    }

    private void loadHistory() {
        List<VideoFile> videoList = new ArrayList<>();
        for (String path : videoService.getVideoHistory()) {
            File file = new File(path);
            if (file.exists()) {
                videoList.add(new VideoFile(file.getName(), path));
            }
        }
        historyListView.setItems(FXCollections.observableArrayList(videoList));
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
