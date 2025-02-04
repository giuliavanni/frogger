package it.unibo.samplejavafx.core;

import java.io.*;
import java.util.Properties;

public class GameSettingsManager {
    private static final String SETTINGS_FILE = "game_settings.properties";

    public static void saveSettings(Properties settings) {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            settings.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadSettings() {
        Properties settings = new Properties();
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            settings.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }
}