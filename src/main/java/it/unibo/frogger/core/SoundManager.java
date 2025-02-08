package it.unibo.frogger.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the sound effects and background music for the game.
 */
public final class SoundManager {
    private static final Map<String, Media> SOUND_EFFECTS = new HashMap<>();
    private static final Map<String, MediaPlayer> PLAYERS = new HashMap<>();
    private static MediaPlayer backgroundMusicPlayer;
    private static double musicVolume = 0.5;
    private static double effectsVolume = 0.5;

    // Prevent instantiation
    private SoundManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Loads the sound effects into the sound manager.
     */
    public static void loadSoundEffects() {
        try {
            SOUND_EFFECTS.put("jump", new Media(SoundManager.class.getResource("/jump.wav").toExternalForm()));
            SOUND_EFFECTS.put("collision", new Media(SoundManager.class.getResource("/collision.wav").toExternalForm()));
            SOUND_EFFECTS.put("token", new Media(SoundManager.class.getResource("/token.wav").toExternalForm()));
            SOUND_EFFECTS.put("water", new Media(SoundManager.class.getResource("/water.wav").toExternalForm()));
            SOUND_EFFECTS.put("levelup", new Media(SoundManager.class.getResource("/levelup.mp3").toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading sound effects: " + e.getMessage());
        }
    }

    /**
     * Plays the specified sound effect.
     *
     * @param soundName the name of the sound effect to play
     */
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

    /**
     * Sets the volume for the background music.
     *
     * @param newVolume the new volume level (0.0 to 1.0)
     */
    public static void setMusicVolume(final double newVolume) {
        musicVolume = Math.min(1.0, Math.max(0.0, newVolume));
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
    }

    /**
     * Gets the current volume level for the background music.
     *
     * @return the current music volume level
     */
    public static double getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the volume for the sound effects.
     *
     * @param newVolume the new volume level (0.0 to 1.0)
     */
    public static void setEffectsVolume(final double newVolume) {
        effectsVolume = Math.min(1.0, Math.max(0.0, newVolume));
        PLAYERS.values().forEach(player -> player.setVolume(effectsVolume));
    }

    /**
     * Gets the current volume level for the sound effects.
     *
     * @return the current effects volume level
     */
    public static double getEffectsVolume() {
        return effectsVolume;
    }

    /**
     * Plays the background music from the specified file.
     *
     * @param musicFile the path to the music file
     */
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

    /**
     * Stops the background music.
     */
    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    /**
     * Plays the game over music.
     */
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
