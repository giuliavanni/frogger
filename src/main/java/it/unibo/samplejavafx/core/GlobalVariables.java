package it.unibo.samplejavafx.core;

public final class GlobalVariables {
    // Prevent instantiation
    private GlobalVariables() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Define global variables
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int JUMP_SIZE = 46;
    public static final int LANE_NUMBER = 13;
    public static final int LANE_HEIGHT = HEIGHT / LANE_NUMBER;
    public static final int GAME_DURATION = 60;
}
