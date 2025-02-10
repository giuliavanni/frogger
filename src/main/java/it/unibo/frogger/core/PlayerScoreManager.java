package it.unibo.frogger.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the player scores, allowing to save, load, and retrieve top scores.
 */
public final class PlayerScoreManager {
    private static final String SCORES_FILE = "player_scores.txt";

    /**
     * Private constructor to prevent instantiation.
     */
    private PlayerScoreManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Saves the player's score to a file.
     *
     * @param playerName the name of the player
     * @param score the score of the player
     */
    public static void saveScore(final String playerName, final int score) {
        // Add timestamp to make each entry unique
        String timestamp = String.valueOf(System.currentTimeMillis());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE, true))) {
            writer.write(playerName + ":" + score + ":" + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the scores from the file.
     *
     * @return a map of player names and their scores
     */
    public static Map<String, Integer> loadScores() {
        Map<String, Integer> scores = new HashMap<>();
        File file = new File(SCORES_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    // Use playerName + timestamp as a unique key
                    String key = parts[0] + "_" + (parts.length == 3 ? parts[2] : System.currentTimeMillis());
                    scores.put(key, Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * Retrieves the top scores.
     *
     * @param limit the maximum number of top scores to retrieve
     * @return a list of entries containing player names and their scores
     */
    public static List<Map.Entry<String, Integer>> getTopScores(final int limit) {
        Map<String, Integer> scores = loadScores();
        return scores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(limit)
            // Remove the timestamp from the name when displaying scores
            .map(entry -> Map.entry(entry.getKey().split("_")[0], entry.getValue()))
            .toList();
    }
}
