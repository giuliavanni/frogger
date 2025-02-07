package it.unibo.samplejavafx.core;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a log in the game, which moves at a certain speed and direction.
 */
public class Log extends GameObjectNotControllable {
    private static final String IMAGE_PATH = "/log.png";
    private int speed;
    private int direction;
    private List<GameObjectControllable> laneObjects;

    /**
     * Constructs a new Log.
     *
     * @param x         the initial x position of the log
     * @param y         the initial y position of the log
     * @param speed     the speed at which the log moves
     * @param direction the direction in which the log moves (1 for right, -1 for left)
     */
    public Log(final int x, final int y, final int speed, final int direction) {
        super(x, y, new ImageView(new Image(Log.class.getResourceAsStream(IMAGE_PATH))));
        this.getImageView().setFitWidth(100); // Set appropriate size
        this.getImageView().setFitHeight(40); // Set appropriate size
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Updates the position of the log and checks if the frog is on the log.
     */
    @Override
    public void updatePosition() {
        setXPosition(getXPosition() + speed * direction);

        // Check if the frog is on the log
        for (GameObjectControllable obj : laneObjects) {
            if (obj instanceof Frog) {
                Frog frog = (Frog) obj;

                if (frog.getYPosition() == this.getYPosition()
                    && frog.getXPosition() >= this.getXPosition()
                    && frog.getXPosition() <= this.getXPosition() + this.getImageView().getFitWidth()) {
                    // The frog is on the log, so it should move with it
                    frog.setOnLog(true, this.speed, this.direction);
                } else {
                    // The frog is no longer on the log
                    frog.setOnLog(false, 0, 0);
                }
            }
        }
    }

    /**
     * Gets the speed of the log.
     *
     * @return the speed of the log
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the log.
     *
     * @param speed the new speed to set
     */
    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    /**
     * Gets the direction of the log.
     *
     * @return the direction of the log (1 for right, -1 for left)
     */
    public int getDirection() {
        return direction;
    }
}
