package com.videoplayer.service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class VideoPlayerService {

    private final List<String> videoHistory = new ArrayList<>();
    private static final String HISTORY_FILE = "src/main/resources/history.txt";

    public void addToHistory(String videoPath) {
        videoHistory.add(videoPath);
        saveHistoryToFile(videoPath); 
    }

    public List<String> getVideoHistory() {
        return videoHistory;
    }

    public void loadHistoryFromFile() {
        try {
            Path path = Paths.get(HISTORY_FILE);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                videoHistory.clear();
                videoHistory.addAll(lines);
            }
        } catch (IOException e) {
            System.out.println("Failed to load history: " + e.getMessage());
        }
    }

    private void saveHistoryToFile(String videoPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.write(videoPath);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to save history: " + e.getMessage());
        }
    }
}
