package it.unibo.frogger;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import it.unibo.frogger.core.*;
import it.unibo.frogger.controller.CollisionDetector;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

class AppTest {

    private static final int INITIAL_FROG_X = 400;
    private static final int INITIAL_FROG_Y = 500;
    private static final int INITIAL_FROG_LIVES = 3;
    private static final int MOVE_DISTANCE = 46;
    private static final int OBSTACLE_X = 400;
    private static final int OBSTACLE_Y = 500;
    private static final int OBSTACLE_SPEED = 1;
    private static final int TOKEN_X = 400;
    private static final int TOKEN_Y = 500;
    private static final int LANE_SPEED = 2;
    private static final int LANE_DIRECTION = 1;
    private static final int LOG_SPEED = 2;
    private static final int LOG_DIRECTION = 1;
    private static final int BOUNDARY_RIGHT = 800;
    private static final int BOUNDARY_BOTTOM = 600;

    private Frog frog;
    private CollisionDetector detector;
    private Obstacle obstacle;
    private Token token;

    @BeforeEach
    void setup() {
        frog = new Frog(INITIAL_FROG_X, INITIAL_FROG_Y, INITIAL_FROG_LIVES); // Create frog with 3 lives at position (400,500)
        detector = new CollisionDetector();
        obstacle = new Obstacle(OBSTACLE_X, OBSTACLE_Y, OBSTACLE_SPEED); // Same position as frog to test collision
        token = new Token(TOKEN_X, TOKEN_Y); // Same position as frog to test collection
    }

    @Test 
    void testFrogMovement() {
        int initialX = frog.getXPosition();
        int initialY = frog.getYPosition();

        frog.move(KeyCode.RIGHT);
        assertEquals(initialX + MOVE_DISTANCE, frog.getXPosition(), "Frog should move 46 pixels right");

        frog.move(KeyCode.LEFT);
        assertEquals(initialX, frog.getXPosition(), "Frog should move back to initial X position");

        frog.move(KeyCode.DOWN);
        assertEquals(initialY + MOVE_DISTANCE, frog.getYPosition(), "Frog should move 46 pixels down");

        frog.move(KeyCode.UP);
        assertEquals(initialY, frog.getYPosition(), "Frog should move back to initial Y position");
    }

    @Test
    void testFrogLives() {
        assertEquals(INITIAL_FROG_LIVES, frog.getLives(), "Frog should start with 3 lives");

        frog.loseLife();
        assertEquals(INITIAL_FROG_LIVES - 1, frog.getLives(), "Frog should have 2 lives after losing one");

        frog.gainLife();
        assertEquals(INITIAL_FROG_LIVES, frog.getLives(), "Frog should have 3 lives after gaining one");
    }

    @Test
    void testCollisionDetection() {
        assertTrue(detector.checkCollision(obstacle, frog), 
            "Should detect collision when frog and obstacle are at same position");

        // Move obstacle away and test again
        obstacle.setPosition(OBSTACLE_X + OBSTACLE_X, OBSTACLE_Y + OBSTACLE_Y);
        assertFalse(detector.checkCollision(obstacle, frog),
            "Should not detect collision when objects are apart");
    }

    @Test
    void testLaneMovement() {
        List<GameObjectNotControllable> objects = new ArrayList<>();
        objects.add(obstacle);

        Lane lane = new Lane(LANE_SPEED, LANE_DIRECTION, objects); // Speed 2, direction right (1)
        int initialX = obstacle.getXPosition();

        lane.updateObjectsPosition();
        assertEquals(initialX + LANE_SPEED, obstacle.getXPosition(),
            "Object should move right by 2 pixels");
    }

    @Test
    void testTokenCollection() {
        token.applyEffect(frog);
        assertEquals(INITIAL_FROG_LIVES + 1, frog.getLives(),
            "Frog should gain a life after collecting token");
    }

    @Test
    void testLogInteraction() {
        assertFalse(frog.isOnLog(), "Frog should not start on a log");

        frog.setOnLog(true, LOG_SPEED, LOG_DIRECTION);
        assertTrue(frog.isOnLog(), "Frog should be on log after setting");

        int initialX = frog.getXPosition();
        frog.updatePosition();
        assertEquals(initialX + LOG_SPEED, frog.getXPosition(),
            "Frog should move with log speed when on log");
    }

    @Test
    void testFrogBoundaries() {
        // Test right boundary
        frog.resetPosition(BOUNDARY_RIGHT, INITIAL_FROG_Y);
        frog.move(KeyCode.RIGHT);
        assertTrue(frog.getXPosition() <= BOUNDARY_RIGHT - MOVE_DISTANCE,
            "Frog should not move beyond right boundary");

        // Test left boundary
        frog.resetPosition(0, INITIAL_FROG_Y);
        frog.move(KeyCode.LEFT);
        assertTrue(frog.getXPosition() >= 0,
            "Frog should not move beyond left boundary");

        // Test bottom boundary
        frog.resetPosition(INITIAL_FROG_X, BOUNDARY_BOTTOM);
        frog.move(KeyCode.DOWN);
        assertTrue(frog.getYPosition() <= BOUNDARY_BOTTOM - MOVE_DISTANCE,
            "Frog should not move beyond bottom boundary");

        // Test top boundary
        frog.resetPosition(INITIAL_FROG_X, 0);
        frog.move(KeyCode.UP);
        assertTrue(frog.getYPosition() >= 0,
            "Frog should not move beyond top boundary");
    }
}
