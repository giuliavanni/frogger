package it.unibo.samplejavafx.core;

import java.io.*;
import java.util.*;

public class PlayerScoreManager {
    private static final String SCORES_FILE = "player_scores.txt";

    public static void saveScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE, true))) {
            writer.write(playerName + ":" + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> loadScores() {
        Map<String, Integer> scores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}