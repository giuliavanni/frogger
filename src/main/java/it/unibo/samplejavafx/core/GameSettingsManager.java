package it.unibo.samplejavafx.core;

import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Utility class for managing game settings.
 * Provides methods to save and load settings from a properties file.
 */
public final class GameSettingsManager {
    private static final String SETTINGS_FILE = "src/main/resources/game_settings.properties";

    /**
     * Private constructor to prevent instantiation.
     */
    private GameSettingsManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Saves the specified settings to the properties file.
     *
     * @param settings the properties object containing the settings to save
     */
    public static void saveSettings(final Properties settings) {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            settings.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the settings from the properties file.
     *
     * @return the properties object containing the loaded settings
     */
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
