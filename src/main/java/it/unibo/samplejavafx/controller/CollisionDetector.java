package it.unibo.samplejavafx.controller;

import it.unibo.samplejavafx.core.Frog;
import it.unibo.samplejavafx.core.GameObjectNotControllable;
import it.unibo.samplejavafx.core.GlobalVariables;
import it.unibo.samplejavafx.core.Obstacle;
import it.unibo.samplejavafx.core.SoundManager;
import it.unibo.samplejavafx.core.Log;
import it.unibo.samplejavafx.core.Token;

import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for detecting and handling collisions between the frog and other game objects.
 */
public class CollisionDetector {
    private static final double INVULNERABILITY_TIME = 2.0; // seconds
    private long lastCollisionTime = 0;

    /**
     * Checks if there is a collision between the given game object and the frog.
     *
     * @param obj  the game object to check for collision
     * @param frog the frog to check for collision
     * @return true if there is a collision, false otherwise
     */
    public boolean checkCollision(final GameObjectNotControllable obj, final Frog frog) {
        // Add some tolerance to the collision detection
        double frogWidth = frog.getImageView().getFitWidth() * 0.8; // Reduce hitbox by 20%
        double frogHeight = frog.getImageView().getFitHeight() * 0.8;
        double objWidth = obj.getImageView().getFitWidth() * 0.8;
        double objHeight = obj.getImageView().getFitHeight() * 0.8;

        // Add offset to center the hitbox
        double frogX = frog.getXPosition() + (frog.getImageView().getFitWidth() - frogWidth) / 2;
        double frogY = frog.getYPosition() + (frog.getImageView().getFitHeight() - frogHeight) / 2;
        double objX = obj.getXPosition() + (obj.getImageView().getFitWidth() - objWidth) / 2;
        double objY = obj.getYPosition() + (obj.getImageView().getFitHeight() - objHeight) / 2;

        // Check for rectangle intersection with adjusted positions and sizes
        return !(frogX + frogWidth <= objX     // frog is to the left
                || objX + objWidth <= frogX       // frog is to the right
                || frogY + frogHeight <= objY     // frog is above
                || objY + objHeight <= frogY);      // frog is below
    }

    /**
     * Handles collisions between the frog and a list of game objects.
     *
     * @param frog    the frog to check for collisions
     * @param objects the list of game objects to check for collisions
     */
    public void handleCollisions(final Frog frog, final List<GameObjectNotControllable> objects) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime < INVULNERABILITY_TIME * 1000) {
            return;
        }

        boolean onLog = false;
        int logSpeed = 0;
        int logDirection = 0;
        int frogY;
        int[] logCounter = new int[6];

        Iterator<GameObjectNotControllable> iterator = objects.iterator();
        while (iterator.hasNext()) {
            GameObjectNotControllable obj = iterator.next();
            if (checkCollision(obj, frog)) {
                if (obj instanceof Obstacle) {
                    handleObstacleCollision(frog);
                    return;
                } else if (obj instanceof Log) {
                    onLog = true;
                    logSpeed = ((Log) obj).getSpeed();
                    logDirection = ((Log) obj).getDirection();
                    frog.setOnLog(onLog, logSpeed, logDirection);
                    return;
                } else if (obj instanceof Token) {
                    handleTokenCollision(frog, (Token) obj);
                    iterator.remove(); // Remove the token after collection
                    return;
                }
            } else {
                // Check if player missed the log
                if (obj instanceof Log) {
                    int llane = obj.getYPosition() / GlobalVariables.LANE_HEIGHT;
                    frogY = frog.getYPosition();
                    int flane = frogY / GlobalVariables.LANE_HEIGHT;
                    if ((flane >= 1) && (flane <= 5)) {
                        if (llane == flane) { 
                            logCounter[llane]++;
                        }
                    }
                }
            }
        }
        int logFault = 0;
        for (int i = 0; i <= 5; i++) {
            if (logCounter[i] == 3) {
                logFault++;
            }
        }
        if (logFault > 0) {
            System.out.println("Log miss detected!");
            frog.setOnLog(false, 0, 0);
            handleLogMiss(frog);
        }
    }

    /**
     * Handles the collision between the frog and an obstacle.
     *
     * @param frog the frog that collided with the obstacle
     */
    private void handleObstacleCollision(final Frog frog) {
        SoundManager.playSound("collision");
        frog.loseLife();
        frog.resetPosition(GlobalVariables.WIDTH / 2, GlobalVariables.HEIGHT - GlobalVariables.JUMP_SIZE);
        lastCollisionTime = System.currentTimeMillis();
    }

    /**
     * Handles the event when the frog misses a log.
     *
     * @param frog the frog that missed the log
     */
    private void handleLogMiss(final Frog frog) {
        SoundManager.playSound("water");
        frog.loseLife();
        frog.resetPosition(GlobalVariables.WIDTH / 2, GlobalVariables.HEIGHT - GlobalVariables.JUMP_SIZE);
        lastCollisionTime = System.currentTimeMillis();
    }

    /**
     * Handles the collision between the frog and a token.
     *
     * @param frog   the frog that collided with the token
     * @param token  the token that the frog collided with
     */
    private void handleTokenCollision(final Frog frog, final Token token) {
        SoundManager.playSound("token");
        token.applyEffect(frog);
    }
}
