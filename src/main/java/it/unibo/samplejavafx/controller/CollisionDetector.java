package it.unibo.samplejavafx.controller;

import it.unibo.samplejavafx.core.Frog;
import it.unibo.samplejavafx.core.GameObjectNotControllable;
import it.unibo.samplejavafx.core.Obstacle;
import it.unibo.samplejavafx.core.SoundManager;
import it.unibo.samplejavafx.core.Log;
import it.unibo.samplejavafx.core.Token;

import java.util.Iterator;
import java.util.List;

public class CollisionDetector {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double INVULNERABILITY_TIME = 2.0; // seconds
    private long lastCollisionTime = 0;
    private static final int LANE_HEIGHT = HEIGHT / 13;

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
        int logDirection = 0;
        int frogY;
        int[] logCnt = new int[6];

        Iterator<GameObjectNotControllable> iterator = objects.iterator();
        while (iterator.hasNext()) {
            GameObjectNotControllable obj = iterator.next();
            if (checkCollision(obj, frog)) {
                if (obj instanceof Obstacle) {
                    handleObstacleCollision(frog);
                    return;
                } else if (obj instanceof Log) {
                    //System.out.println("Log collision detected!");
                    //System.out.println("Log speed: " + ((Log) obj).getSpeed());
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
            }
            else
            {
                //controllo se ho mancato il tronco
                if (obj instanceof Log) {
                    int llane = obj.getYPosition() / LANE_HEIGHT;
                    frogY = frog.getYPosition();
                    int flane = frogY / LANE_HEIGHT;
                    if ((flane >= 1) && (flane <=5))
                    {
                        //System.out.println("Log lane " + llane);
                        if (llane == flane)  
                        { 
                            logCnt[llane]++;     
                        }
                    }
                }
            }            
        }
        int logFault = 0;
        for (int i = 0; i <= 5; i++) {
            if (logCnt[i] == 3)
                logFault++;
        }
        if (logFault > 0)
        {
            System.out.println("Log miss detected!");
            frog.setOnLog(false, 0, 0);
            handleLogMiss(frog);
        }
    }

    private void handleObstacleCollision(Frog frog) {
        SoundManager.playSound("collision");
        frog.loseLife();
        frog.resetPosition(WIDTH / 2, HEIGHT - 46);
        lastCollisionTime = System.currentTimeMillis();
    }

    private void handleLogMiss(Frog frog) {
        SoundManager.playSound("water");
        frog.loseLife();
        frog.resetPosition(WIDTH / 2, HEIGHT - 46);
        lastCollisionTime = System.currentTimeMillis();
    }

    private void handleTokenCollision(Frog frog, Token token) {
        SoundManager.playSound("token");
        token.applyEffect(frog);
    }
}