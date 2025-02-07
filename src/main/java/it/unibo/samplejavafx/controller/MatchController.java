package it.unibo.samplejavafx.controller;

import it.unibo.samplejavafx.view.MatchView;
import it.unibo.samplejavafx.core.Frog;
import it.unibo.samplejavafx.core.GameObjectNotControllable;
import it.unibo.samplejavafx.core.GlobalVariables;
import it.unibo.samplejavafx.core.Lane;
import it.unibo.samplejavafx.core.PlayerScoreManager;
import it.unibo.samplejavafx.core.SoundManager;
import it.unibo.samplejavafx.core.Token;
import it.unibo.samplejavafx.main.MainApp;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.List;

/**
 * This class controls the game logic and interactions for the Frogger game.
 */
public class MatchController implements ViewObserver {
    private Timeline timeline;
    private double timeLeft = GlobalVariables.GAME_DURATION;
    private Frog frog;
    private List<Lane> lanes;
    private MatchView view;
    private CollisionDetector collisionDetector;
    private boolean isPaused;
    private AnimationTimer gameLoop;
    private List<GameObjectNotControllable> objects;
    private MainApp mainApp;
    private int currentScore = 0;

    /**
     * Constructs a new MatchController.
     *
     * @param frog    the frog character in the game
     * @param lanes   the list of lanes in the game
     * @param objects the list of game objects not in lanes
     * @param view    the view to render the game
     * @param mainApp the main application instance
     */
    public MatchController(
        final Frog frog, 
        final List<Lane> lanes, 
        final List<GameObjectNotControllable> objects, 
        final MatchView view, 
        final MainApp mainApp
    ) {
        this.frog = frog;
        this.lanes = lanes;
        this.objects = objects;
        this.view = view;
        this.collisionDetector = new CollisionDetector();
        this.isPaused = false;
        this.mainApp = mainApp;
        SoundManager.loadSoundEffects();
        SoundManager.playBackgroundMusic("/Frogger_Theme.mp3");
        startGameLoop();
        startTimer();
    }

    /**
     * Starts the game loop.
     */
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                if (!isPaused) {
                    update();
                }
                updateView();
            }
        };
        gameLoop.start();
    }

    /**
     * Updates the game state.
     */
    private void update() {
        // Update game state
        for (Lane lane : lanes) {
            lane.updateObjectsPosition();
        }

        // Check collisions with lane objects
        for (Lane lane : lanes) {
            collisionDetector.handleCollisions(frog, lane.getObjects());
        }

        // Check collisions with tokens and other objects not in lanes
        collisionDetector.handleCollisions(frog, objects);

        // Update frog position if on log
        frog.updatePosition();

        // Check frog on top lane:
        // reset frog position, update score, increment game level
        if (frog.lanePosition() == GlobalVariables.LANE_END) {
            frog.resetPosition(GlobalVariables.WIDTH / 2, GlobalVariables.HEIGHT - GlobalVariables.JUMP_SIZE);
            updateScore(100);
            incrementLevel();
        }

        // Check game over conditions
        if (frog.getLives() <= 0) {
            gameOver();
        }
    }

    /**
     * Increments the game level by increasing the speed of logs and obstacles.
     */
    private void incrementLevel() {
        for (Lane lane : lanes) {
            lane.incrementSpeed(1); // Increment speed by 1 (adjust as needed)
        }
        resetTimer();
    }

    /**
     * Resets the game timer.
     */
    private void resetTimer() {
        timeLeft = GlobalVariables.GAME_DURATION;
        timeline.playFromStart();
    }

    /**
     * Handles input from the user.
     *
     * @param code the key code of the input
     */
    @Override
    public void handleInput(final KeyCode code) {
        if (code == KeyCode.P) {
            togglePause();
        } else {
            frog.move(code);
            if (code == KeyCode.UP) {
                updateScore(10);
            }
        }
    }

    /**
     * Updates the view to reflect the current game state.
     */
    @Override
    public void updateView() {
        if (frog.getLives() <= 0) {
            view.renderGameOver(calculateScore());
            return;
        }

        view.clearScene();

        // Render lanes
        for (int i = 0; i < lanes.size(); i++) {
            Lane lane = lanes.get(i);
            if (i == 0 || i == 6 || i == 12) {
                view.renderGroundLane(lane, i);
            } else if (i >= 1 && i <= 5) {
                view.renderLogLane(lane, i);
            } else {
                view.renderTrafficLane(lane, i);
            }
        }

        // Render tokens using objects list
        for (GameObjectNotControllable obj : objects) {
            if (obj instanceof Token) {
                view.renderToken(obj);
            }
        }

        view.renderFrog(frog);
        view.renderLives(frog);
        view.drawLaneLines();
        view.renderScore(currentScore);
    }

    /**
     * Starts the game timer.
     */
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeLeft > 0) {
                timeLeft--;  // Decrement the time
                double progress = timeLeft / GlobalVariables.GAME_DURATION;  // Calculate the remaining percentage
                view.updateTimerDisplay(progress);  // Update the timer display in the view
            } else {
                view.updateTimerDisplay(0);  // If the time is up, the bar is empty
                timeline.stop();
                gameOver();  // Call the function when the time is up
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);  // Repeat the cycle
        timeline.play();  // Start the timer
    }

    /**
     * Stops the game timer.
     */
    private void stopTimer() {
        this.timeline.stop();
    }

    /**
     * Toggles the pause state of the game.
     */
    public void togglePause() {
        isPaused = !isPaused;
        mainApp.showSettingsButton(isPaused);
        mainApp.showPausedLabel(isPaused);
        if (isPaused) {
            timeline.pause();  // Pause the timer
        } else {
            timeline.play();  // Resume the timer
        }
    }

    /**
     * Handles the game over state.
     */
    private void gameOver() {
        stop();
        stopTimer();
        SoundManager.stopBackgroundMusic();
        SoundManager.playGameOverMusic();
        int finalScore = calculateScore();
        String playerName = view.getPlayerName();
        PlayerScoreManager.saveScore(playerName, finalScore);
        view.renderGameOver(finalScore);
        System.out.println("Punteggio " + finalScore);
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        gameLoop.stop();
    }

    /**
     * Updates the score by a given value.
     *
     * @param value the value to add to the current score
     * @return the updated score
     */
    public int updateScore(final int value) {
        currentScore += value;
        return currentScore;
    }

    /**
     * Calculates the final score.
     *
     * @return the final score
     */
    private int calculateScore() {
        // Implement score calculation logic
        return currentScore;
    }
}
