package it.unibo.frogger.core;

import javafx.scene.input.KeyCode;

/**
 * Represents a game object that can be controlled by the player.
 * This class provides basic properties and methods for game objects
 * such as position and movement.
 */
public abstract class GameObjectControllable {
    private int xPosition;
    private int yPosition;

    /**
     * Constructs a new GameObjectControllable at the specified position.
     *
     * @param x the x-coordinate of the game object
     * @param y the y-coordinate of the game object
     */
    public GameObjectControllable(final int x, final int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * Gets the x-coordinate of the game object.
     *
     * @return the x-coordinate of the game object
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Sets the x-coordinate of the game object.
     *
     * @param xPosition the new x-coordinate of the game object
     */
    public void setXPosition(final int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Gets the y-coordinate of the game object.
     *
     * @return the y-coordinate of the game object
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Sets the y-coordinate of the game object.
     *
     * @param yPosition the new y-coordinate of the game object
     */
    public void setYPosition(final int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Moves the game object based on the specified key code.
     * This method should be implemented by subclasses to define
     * specific behavior for moving the game object.
     *
     * @param code the key code representing the direction of movement
     */
    public abstract void move(KeyCode code);
}
