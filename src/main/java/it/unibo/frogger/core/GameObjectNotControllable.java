package it.unibo.frogger.core;

import javafx.scene.image.ImageView;

/**
 * Represents a game object that cannot be controlled by the player.
 * This class provides basic properties and methods for game objects
 * such as position and image representation.
 */
public abstract class GameObjectNotControllable {
    private int xPosition;
    private int yPosition;
    private int width;
    private ImageView imageView;

    /**
     * Constructs a new GameObjectNotControllable at the specified position
     * with the specified image.
     *
     * @param xPosition the x-coordinate of the game object
     * @param yPosition the y-coordinate of the game object
     * @param width the width of the game object
     * @param imageView the image representation of the game object
     */
    public GameObjectNotControllable(final int xPosition, final int yPosition,final int width, final ImageView imageView) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.imageView = imageView;
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
        this.imageView.setX(xPosition);
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
        this.imageView.setY(yPosition);
    }

    /**
     * Sets the position of the game object.
     *
     * @param xPosition the new x-coordinate of the game object
     * @param yPosition the new y-coordinate of the game object
     */
    public void setPosition(final int xPosition, final int yPosition) {
        setXPosition(xPosition);
        setYPosition(yPosition);
    }

    /**
     * Gets the image representation of the game object.
     *
     * @return the image representation of the game object
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets the image representation of the game object.
     *
     * @param imageView the new image representation of the game object
     */
    public void setImageView(final ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Updates the position of the game object.
     * This method should be implemented by subclasses to define
     * specific behavior for updating the position.
     */
    public abstract void updatePosition();

    /**
     * Gets the width of the game object.
     *
     * @return the width of the game object
     */
    public int getWidth()
    {
        return width;
    }
}
