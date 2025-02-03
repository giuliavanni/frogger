package it.unibo.samplejavafx.controller;

import it.unibo.samplejavafx.core.Frog;
import it.unibo.samplejavafx.core.GameObjectNotControllable;
import it.unibo.samplejavafx.core.Obstacle;
import it.unibo.samplejavafx.core.Log;
import it.unibo.samplejavafx.core.Token;

import java.util.Iterator;
import java.util.List;

public class CollisionDetector {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double INVULNERABILITY_TIME = 2.0; // seconds
    private long lastCollisionTime = 0;

    public boolean checkCollision(GameObjectNotControllable obj, Frog frog) {
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
        return !(frogX + frogWidth <= objX ||    // frog is to the left
                objX + objWidth <= frogX ||      // frog is to the right
                frogY + frogHeight <= objY ||    // frog is above
                objY + objHeight <= frogY);      // frog is below
    }

    public void handleCollisions(Frog frog, List<GameObjectNotControllable> objects) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime < INVULNERABILITY_TIME * 1000) {
            return;
        }
        
        boolean onLog = false;
        int logSpeed = 0;
    
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
                } else if (obj instanceof Token) {
                    handleTokenCollision(frog, (Token) obj);
                    iterator.remove(); // Remove the token after collection
                    return;
                }
            }
        }

        frog.setOnLog(onLog, logSpeed);
    }

    private void handleObstacleCollision(Frog frog) {
        frog.loseLife();
        frog.resetPosition(WIDTH / 2, HEIGHT - 46);
        lastCollisionTime = System.currentTimeMillis();
    }

    private void handleTokenCollision(Frog frog, Token token) {
        token.applyEffect(frog);
    }
}