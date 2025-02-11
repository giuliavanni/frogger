package it.unibo.frogger.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents an obstacle in the game, which moves at a certain speed and direction.
 */
public class Obstacle extends GameObjectNotControllable {
    private static final String IMAGE_PATH = "/bike.png";
    private static final String IMAGE_REVERSED_PATH = "/biker.png";

    /**
     * Constructs a new Obstacle.
     *
     * @param x         the initial x position of the obstacle
     * @param y         the initial y position of the obstacle
     * @param direction the direction in which the obstacle moves (1 for right, -1 for left)
     */
    public Obstacle(final int x, final int y, final int direction) {
        super(x, y, 70, new ImageView(new Image(
            Obstacle.class.getResourceAsStream(
                direction == -1 ? IMAGE_REVERSED_PATH : IMAGE_PATH
            )
        )));
        this.getImageView().setFitWidth(70); // Set appropriate size
        this.getImageView().setFitHeight(70); // Set appropriate size
    }

    /**
     * Updates the position of the obstacle.
     * The position update logic will be handled by the Lane class.
     */
    @Override
    public void updatePosition() {
        // The position update logic will be handled by the Lane class
    }

    /**
     * Sets the position of the obstacle.
     *
     * @param x the new x position
     * @param y the new y position
     */
    @Override
    public void setPosition(final int x, final int y) {
        setXPosition(x);
        setYPosition(y);
    }
}
