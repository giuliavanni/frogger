package it.unibo.samplejavafx.core;

import java.io.*;
import java.util.Properties;

public final class GameSettingsManager {
    private static final String SETTINGS_FILE = "src/main/resources/game_settings.properties";

    // Private constructor to prevent instantiation
    private GameSettingsManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void saveSettings(final Properties settings) {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            settings.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadSettings() {
        Properties settings = new Properties();
        File file = new File(SETTINGS_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (InputStream input = new FileInputStream(file)) {
            settings.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }
}
