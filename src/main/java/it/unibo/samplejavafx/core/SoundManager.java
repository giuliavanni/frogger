package it.unibo.samplejavafx.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, Media> soundEffects = new HashMap<>();
    private static final Map<String, MediaPlayer> players = new HashMap<>();
    private static MediaPlayer backgroundMusicPlayer;
    private static double musicVolume = 0.5;
    private static double effectsVolume = 0.5;

    public static void loadSoundEffects() {
        try {
            soundEffects.put("jump", new Media(SoundManager.class.getResource("/jump.wav").toExternalForm()));
            soundEffects.put("collision", new Media(SoundManager.class.getResource("/collision.wav").toExternalForm()));
            soundEffects.put("token", new Media(SoundManager.class.getResource("/token.wav").toExternalForm()));
            soundEffects.put("water", new Media(SoundManager.class.getResource("/water.wav").toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading sound effects: " + e.getMessage());
        }
    }

    public static void playSound(String soundName) {
        Media sound = soundEffects.get(soundName);
        if (sound != null) {
            if (!players.containsKey(soundName)) {
                MediaPlayer player = new MediaPlayer(sound);
                player.setVolume(effectsVolume);
                players.put(soundName, player);
            }
            MediaPlayer player = players.get(soundName);
            player.stop();
            player.play();
        }
    }

    public static void setMusicVolume(double newVolume) {
        musicVolume = Math.min(1.0, Math.max(0.0, newVolume));
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
    }

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static void setEffectsVolume(double newVolume) {
        effectsVolume = Math.min(1.0, Math.max(0.0, newVolume));
        players.values().forEach(player -> player.setVolume(effectsVolume));
    }

    public static double getEffectsVolume() {
        return effectsVolume;
    }

    public static void playBackgroundMusic(String musicFile) {
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