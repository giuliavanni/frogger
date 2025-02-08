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
    private static final String SCORES_FILE = "src/main/resources/player_scores.txt";

    // Prevent instantiation
    private PlayerScoreManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Saves the score of a player to the scores file.
     *
     * @param playerName the name of the player
     * @param score      the score of the player
     */
    public static void saveScore(final String playerName, final int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE, true))) {
            writer.write(playerName + ":" + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the scores from the scores file.
     *
     * @return a map of player names to their scores
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
                if (parts.length == 2) {
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * Retrieves the top scores up to a specified limit.
     *
     * @param limit the maximum number of top scores to retrieve
     * @return a list of entries containing player names and their scores, sorted by score in descending order
     */
    public static List<Map.Entry<String, Integer>> getTopScores(final int limit) {
        Map<String, Integer> scores = loadScores();
        return scores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .toList();
    }
}
