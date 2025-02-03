package it.unibo.samplejavafx.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, Media> soundEffects = new HashMap<>();
    private static final Map<String, MediaPlayer> players = new HashMap<>();
    private static MediaPlayer backgroundMusicPlayer;
    private static double volume = 0.5;

    public static void loadSoundEffects() {
        try {
            soundEffects.put("jump", new Media(SoundManager.class.getResource("/jump.wav").toExternalForm()));
            soundEffects.put("collision", new Media(SoundManager.class.getResource("/collision.wav").toExternalForm()));
            soundEffects.put("token", new Media(SoundManager.class.getResource("/token.wav").toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading sound effects: " + e.getMessage());
        }
    }

    public static void playSound(String soundName) {
        Media sound = soundEffects.get(soundName);
        if (sound != null) {
            if (!players.containsKey(soundName)) {
                MediaPlayer player = new MediaPlayer(sound);
                player.setVolume(volume);
                players.put(soundName, player);
            }
            MediaPlayer player = players.get(soundName);
            player.stop();
            player.play();
        }
    }

    public static void setVolume(double newVolume) {
        volume = Math.min(1.0, Math.max(0.0, newVolume));
        players.values().forEach(player -> player.setVolume(volume));
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }

    public static void playBackgroundMusic(String musicFile) {
        try {
            Media sound = new Media(SoundManager.class.getResource(musicFile).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(sound);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
            backgroundMusicPlayer.setVolume(volume);
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    public static void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
    }

    public static void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }
}