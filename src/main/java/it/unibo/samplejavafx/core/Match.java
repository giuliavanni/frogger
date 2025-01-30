package it.unibo.samplejavafx.core;

import it.unibo.samplejavafx.controller.ViewObserver;
import it.unibo.samplejavafx.controller.CollisionDetector;
import it.unibo.samplejavafx.view.MatchView;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class Match implements ViewObserver {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int LANE_HEIGHT = HEIGHT / 13; // 13 lanes in total

    private Frog frog;
    private List<GameObjectNotControllable> objects;
    private List<Lane> lanes;
    private MatchView view;
    private CollisionDetector collisionDetector;
    private boolean isPaused = false;
    private AnimationTimer gameLoop;

    public Match(MatchView view) {
        this.view = view;
        this.collisionDetector = new CollisionDetector();
        this.objects = new ArrayList<>();
        this.lanes = new ArrayList<>();
        setupGame();
    }

    public Frog getFrog() {
        return frog;
    }

    public boolean isPaused() {
        return isPaused;
    }

    private void setupGame() {
        // Initialize Frog
        frog = new Frog(WIDTH / 2, HEIGHT - 50, 3);
    
        // Create ground lane (start)
        Lane groundStartLane = new Lane(0, 0, new ArrayList<>());
        lanes.add(groundStartLane);
    
        // Create traffic lanes and add obstacles to them
        for (int i = 0; i < 5; i++) {
            int direction = (i % 2 == 0) ? 1 : -1;
            Lane lane = new Lane((int) (Math.random() * 3 + 2), direction, new ArrayList<>());
            lanes.add(lane);
    
            // Add obstacles to lanes
            for (int j = 0; j < 3; j++) {
                int yPosition = (i + 1) * LANE_HEIGHT; // Position in the current lane
                lane.getObjects().add(new Obstacle((int) (Math.random() * WIDTH), yPosition));
            }
        }
    
        // Create ground lane (middle)
        Lane groundMiddleLane = new Lane(0, 0, new ArrayList<>());
        lanes.add(groundMiddleLane);
    
        // Create log lanes and add logs to them
        for (int i = 0; i < 5; i++) {
            int direction = (i % 2 == 0) ? 1 : -1;
            Lane lane = new Lane((int) (Math.random() * 3 + 2), direction, new ArrayList<>());
            lanes.add(lane);
    
            // Add logs to lanes
            for (int j = 0; j < 3; j++) {
                int yPosition = (i + 7) * LANE_HEIGHT; // Position in the current lane
                lane.getObjects().add(new Log((int) (Math.random() * WIDTH), yPosition, lane.getSpeed()));
            }
        }
    
        // Create ground lane (end)
        Lane groundEndLane = new Lane(0, 0, new ArrayList<>());
        lanes.add(groundEndLane);
    
        // Create token
        objects.add(new Token((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT)));
    
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused) {
                    update();
                }
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {
        for (Lane lane : lanes) {
            lane.updateObjectsPosition();
        }
        collisionDetector.handleCollisions(frog, objects);
        frog.updatePosition(); // Update frog's position if on a log
    }

    private void render() {
        view.clearScene(); // Clear the scene before rendering
        
        // Render lanes and other elements first
        view.renderGroundLane(lanes.get(0), 0);

        for (int i = 1; i <= 5; i++) {
            view.renderLogLane(lanes.get(i), i);
        }

        view.renderGroundLane(lanes.get(6), 6);

        for (int i = 7; i <= 11; i++) {
            view.renderTrafficLane(lanes.get(i), i);
        }

        view.renderGroundLane(lanes.get(12), 12);

        view.renderFrog(frog);
        
        for (GameObjectNotControllable token : objects) {
            view.renderToken(token);
        }
        
        // Draw lines last so they appear on top
        view.drawLaneLines();
        
        view.updateFrogPosition(frog);
    }

    @Override
    public void handleInput(KeyCode code) {
        frog.move(code);
    }

    @Override
    public void updateView() {
        render();
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void stop() {
        gameLoop.stop();
    }
}