package it.unibo.samplejavafx.core;

/**
 * This class contains global constants used throughout the game.
 */
public final class GlobalVariables {
    // Prevent instantiation
    private GlobalVariables() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Define global variables

    /**
     * The width of the game window.
     */
    public static final int WIDTH = 800;

    /**
     * The height of the game window.
     */
    public static final int HEIGHT = 600;

    /**
     * The size of each jump the frog makes.
     */
    public static final int JUMP_SIZE = 46;

    /**
     * The number of lives the frog has.
     */
    public static final int FROG_LIVES = 3;

    /**
     * The width of each log in the game.
     */
    public static final int LOG_WIDTH = 100;

    /**
     * The number of lanes in the game.
     */
    public static final int LANE_NUMBER = 13;

    /**
     * The height of each lane.
     */
    public static final int LANE_HEIGHT = HEIGHT / LANE_NUMBER;

    /**
     * The starting lane position.
     */
    public static final int LANE_START = 13;

    /**
     * The ending lane position.
     */
    public static final int LANE_END = 0;

    /**
     * The duration of the game in seconds.
     */
    public static final int GAME_DURATION = 60;
}
