package it.unibo.samplejavafx.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;

public final class SoundManager {
    private static final Map<String, Media> SOUND_EFFECTS = new HashMap<>();
    private static final Map<String, MediaPlayer> PLAYERS = new HashMap<>();
    private static MediaPlayer backgroundMusicPlayer;
    private static double musicVolume = 0.5;
    private static double effectsVolume = 0.5;

    private SoundManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void loadSoundEffects() {
        try {
            SOUND_EFFECTS.put("jump", new Media(SoundManager.class.getResource("/jump.wav").toExternalForm()));
            SOUND_EFFECTS.put("collision", new Media(SoundManager.class.getResource("/collision.wav").toExternalForm()));
            SOUND_EFFECTS.put("token", new Media(SoundManager.class.getResource("/token.wav").toExternalForm()));
            SOUND_EFFECTS.put("water", new Media(SoundManager.class.getResource("/water.wav").toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading sound effects: " + e.getMessage());
        }
    }

    public static void playSound(final String soundName) {
        Media sound = SOUND_EFFECTS.get(soundName);
        if (sound != null) {
            if (!PLAYERS.containsKey(soundName)) {
                MediaPlayer player = new MediaPlayer(sound);
                player.setVolume(effectsVolume);
                PLAYERS.put(soundName, player);
            }
            MediaPlayer player = PLAYERS.get(soundName);
            player.stop();
            player.play();
        }
    }

    public static void setMusicVolume(final double newVolume) {
        musicVolume = Math.min(1.0, Math.max(0.0, newVolume));
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
    }

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static void setEffectsVolume(final double newVolume) {
        effectsVolume = Math.min(1.0, Math.max(0.0, newVolume));
        PLAYERS.values().forEach(player -> player.setVolume(effectsVolume));
    }

    public static double getEffectsVolume() {
        return effectsVolume;
    }

    public static void playBackgroundMusic(final String musicFile) {
        try {
            Media sound = new Media(SoundManager.class.getResource(musicFile).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(sound);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
            backgroundMusicPlayer.setVolume(musicVolume);
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public static void playGameOverMusic() {
        try {
            Media sound = new Media(SoundManager.class.getResource("/game-over.mp3").toExternalForm());
            MediaPlayer gameOverPlayer = new MediaPlayer(sound);
            gameOverPlayer.setVolume(musicVolume);
            gameOverPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing game over music: " + e.getMessage());
        }
    }
}
