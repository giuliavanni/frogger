package it.unibo.samplejavafx.controller;

import it.unibo.samplejavafx.core.*;
import it.unibo.samplejavafx.view.MatchView;
import it.unibo.samplejavafx.main.MainApp;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.List;

public class MatchController implements ViewObserver {
    private static final int GAME_DURATION = 60;
    private Timeline timeline;
    private double timeLeft = GAME_DURATION;
    private Frog frog;
    private List<Lane> lanes;
    private MatchView view;
    private CollisionDetector collisionDetector;
    private boolean isPaused;
    private AnimationTimer gameLoop;
    private List<GameObjectNotControllable> objects;
    private MainApp mainApp;
    private int currentScore = 0;

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

        // Check game over conditions
        if (frog.getLives() <= 0) {
            gameOver();
        }
    }

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

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeLeft > 0) {
                timeLeft--;  // Decrement the time
                double progress = timeLeft / GAME_DURATION;  // Calculate the remaining percentage
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

    private void stopTimer() {
        this.timeline.stop();
    }

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

    public void stop() {
        gameLoop.stop();
    }

    public int updateScore(final int value) {
        currentScore += value;
        return 0;
    }

    private int calculateScore() {
        // Implement score calculation logic
        return currentScore;
    }
}
