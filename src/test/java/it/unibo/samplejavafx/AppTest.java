package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import it.unibo.samplejavafx.core.*;
import it.unibo.samplejavafx.controller.CollisionDetector;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

class AppTest {

    private Frog frog;
    private CollisionDetector detector;
    private Obstacle obstacle;
    private Token token;
    private Log log;

    @BeforeEach
    void setup() {
        frog = new Frog(400, 500, 3); // Create frog with 3 lives at position (400,500)
        detector = new CollisionDetector();
        obstacle = new Obstacle(400, 500,1); // Same position as frog to test collision
        token = new Token(400, 500); // Same position as frog to test collection
        log = new Log(400, 500, 2, 1); // Same position as frog with speed 2
    }

    @Test 
    void testFrogMovement() {
        int initialX = frog.getXPosition();
        int initialY = frog.getYPosition();
        
        frog.move(KeyCode.RIGHT);
        assertEquals(initialX + 46, frog.getXPosition(), "Frog should move 46 pixels right");
        
        frog.move(KeyCode.LEFT);
        assertEquals(initialX, frog.getXPosition(), "Frog should move back to initial X position");
        
        frog.move(KeyCode.DOWN);
        assertEquals(initialY + 46, frog.getYPosition(), "Frog should move 46 pixels down");
        
        frog.move(KeyCode.UP);
        assertEquals(initialY, frog.getYPosition(), "Frog should move back to initial Y position");
    }

    @Test
    void testFrogLives() {
        assertEquals(3, frog.getLives(), "Frog should start with 3 lives");
        
        frog.loseLife();
        assertEquals(2, frog.getLives(), "Frog should have 2 lives after losing one");
        
        frog.gainLife();
        assertEquals(3, frog.getLives(), "Frog should have 3 lives after gaining one");
    }

    @Test
    void testCollisionDetection() {
        assertTrue(detector.checkCollision(obstacle, frog), 
            "Should detect collision when frog and obstacle are at same position");
        
        // Move obstacle away and test again
        obstacle.setPosition(500, 500);
        assertFalse(detector.checkCollision(obstacle, frog),
            "Should not detect collision when objects are apart");
    }

    @Test
    void testLaneMovement() {
        List<GameObjectNotControllable> objects = new ArrayList<>();
        objects.add(obstacle);
        
        Lane lane = new Lane(2, 1, objects); // Speed 2, direction right (1)
        int initialX = obstacle.getXPosition();
        
        lane.updateObjectsPosition();
        assertEquals(initialX + 2, obstacle.getXPosition(),
            "Object should move right by 2 pixels");
    }

    @Test
    void testTokenCollection() {
        token.applyEffect(frog);
        assertEquals(4, frog.getLives(),
            "Frog should gain a life after collecting token");
    }

    @Test
    void testLogInteraction() {
        assertFalse(frog.isOnLog(), "Frog should not start on a log");
        
        frog.setOnLog(true, 2,1);
        assertTrue(frog.isOnLog(), "Frog should be on log after setting");
        
        int initialX = frog.getXPosition();
        frog.updatePosition();
        assertEquals(initialX + 2, frog.getXPosition(),
            "Frog should move with log speed when on log");
    }

    @Test
    void testFrogBoundaries() {
        // Test right boundary
        frog.resetPosition(800, 500);
        frog.move(KeyCode.RIGHT);
        assertTrue(frog.getXPosition() <= 800 - 46,
            "Frog should not move beyond right boundary");

        // Test left boundary
        frog.resetPosition(0, 500);
        frog.move(KeyCode.LEFT);
        assertTrue(frog.getXPosition() >= 0,
            "Frog should not move beyond left boundary");

        // Test bottom boundary
        frog.resetPosition(400, 600);
        frog.move(KeyCode.DOWN);
        assertTrue(frog.getYPosition() <= 600 - 46,
            "Frog should not move beyond bottom boundary");

        // Test top boundary
        frog.resetPosition(400, 0);
        frog.move(KeyCode.UP);
        assertTrue(frog.getYPosition() >= 0,
            "Frog should not move beyond top boundary");
    }
}