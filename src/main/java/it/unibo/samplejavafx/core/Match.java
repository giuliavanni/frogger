package it.unibo.samplejavafx.core;

import it.unibo.samplejavafx.view.MatchView;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a match in the Frogger game, managing the game setup and state.
 */
public class Match {
    private static final int OBSTACLE_WIDTH = 50;
    private static final int OBSTACLE_Y_OFFSET = 12;

    private Frog frog;
    private List<GameObjectNotControllable> objects;
    private List<Lane> lanes;

    /**
     * Constructs a new Match.
     *
     * @param view the view to render the game
     */
    public Match(final MatchView view) {
        this.objects = new ArrayList<>();
        this.lanes = new ArrayList<>();
        setupGame();
    }

    /**
     * Gets the frog character in the game.
     *
     * @return the frog character
     */
    public Frog getFrog() {
        return frog;
    }

    /**
     * Sets up the game by initializing the frog, lanes, logs, and obstacles.
     */
    private void setupGame() {
        // Initialize Frog
        frog = new Frog(
            GlobalVariables.WIDTH / 2, 
            GlobalVariables.HEIGHT - GlobalVariables.JUMP_SIZE, 
            GlobalVariables.FROG_LIVES
        );

        // Create ground lane (start)
        Lane groundStartLane = new Lane(0, 0, new ArrayList<>());
        lanes.add(groundStartLane);

        // Create log lanes and add logs to them
        for (int i = 0; i < 5; i++) {
            int direction = (i % 2 == 0) ? 1 : -1;
            Lane lane = new Lane((int) (Math.random() * 3 + 2), direction, new ArrayList<>());
            lanes.add(lane);

            // Add logs to lanes
            for (int j = 0; j < 3; j++) {
                int xPosition;
                boolean overlap;
                do {
                    overlap = false;
                    xPosition = (int) (Math.random() * (GlobalVariables.WIDTH - GlobalVariables.LOG_WIDTH));
                    for (GameObjectNotControllable obj : lane.getObjects()) {
                        if (Math.abs(obj.getXPosition() - xPosition) < GlobalVariables.LOG_WIDTH) {
                            overlap = true;
                            break;
                        }
                    }
                } while (overlap);
                int yPosition = (i + 1) * GlobalVariables.LANE_HEIGHT; // Position in the current lane
                lane.getObjects().add(new Log(xPosition, yPosition, lane.getSpeed(), lane.getDirection()));
            }
        }

        // Create ground lane (middle)
        Lane groundMiddleLane = new Lane(0, 0, new ArrayList<>());
        lanes.add(groundMiddleLane);

        // Create traffic lanes and add obstacles to them
        for (int i = 0; i < 5; i++) {
            int direction = (i % 2 == 0) ? 1 : -1;
            Lane lane = new Lane((int) (Math.random() * 3 + 2), direction, new ArrayList<>());
            lanes.add(lane);

            // Add obstacles to lanes
            for (int j = 0; j < 3; j++) {
                int xPosition;
                boolean overlap;
                do {
                    overlap = false;
                    xPosition = (int) (Math.random() * (GlobalVariables.WIDTH - OBSTACLE_WIDTH));
                    for (GameObjectNotControllable obj : lane.getObjects()) {
                        if (Math.abs(obj.getXPosition() - xPosition) < OBSTACLE_WIDTH) {
                            overlap = true;
                            break;
                        }
                    }
                } while (overlap);
                int yPosition = (i + 7) * GlobalVariables.LANE_HEIGHT - OBSTACLE_Y_OFFSET; // Position in the current lane
                lane.getObjects().add(new Obstacle(xPosition, yPosition, direction));
            }
        }

        // Create ground lane (end)
        Lane groundEndLane = new Lane(0, 0, new ArrayList<>());
        lanes.add(groundEndLane);

        addTokenInValidPosition();
    }

    /**
     * Adds a token in a valid position within the game.
     */
    private void addTokenInValidPosition() {
        int laneIndex;
        int xPosition = (
            (int) (Math.random() * (GlobalVariables.WIDTH / GlobalVariables.LANE_HEIGHT))
        ) * GlobalVariables.LANE_HEIGHT;

        do {
            laneIndex = (int) (Math.random() * 10) + 1;
            if ((laneIndex == 1) || (laneIndex == 6) || (laneIndex == 11)) {
                laneIndex = 0;
            }
        } while (laneIndex == 0);
        int yPosition = laneIndex * GlobalVariables.LANE_HEIGHT;

        Token token = new Token(xPosition, yPosition);
        objects.add(token);
    }

    /**
     * Gets the list of lanes in the game.
     *
     * @return the list of lanes
     */
    public List<Lane> getLanes() {
        return lanes;
    }

    /**
     * Gets the list of game objects not in lanes.
     *
     * @return the list of game objects not in lanes
     */
    public List<GameObjectNotControllable> getObjects() {
        return objects;
    }
}
