package it.unibo.frogger.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * This class represents the frog character in the game.
 */
public class Frog extends GameObjectControllable {
    private int lives;
    private ImageView imageView;
    private boolean onLog = false;
    private int logSpeed = 0;
    private int logDirection = 0;

    private static final String IMAGE_PATH = "/froggy1.png"; // Path to your image

    /**
     * Constructs a new Frog.
     *
     * @param x     the initial x position of the frog
     * @param y     the initial y position of the frog
     * @param lives the initial number of lives of the frog
     */
    public Frog(final int x, final int y, final int lives) {
        super(x, y);
        this.imageView = new ImageView(new Image(Frog.class.getResourceAsStream(IMAGE_PATH)));
        this.imageView.setFitWidth(40); // Set appropriate size
        this.imageView.setFitHeight(40); // Set appropriate size
        this.lives = lives;
    }

    /**
     * Checks if the frog is on a log.
     *
     * @return true if the frog is on a log, false otherwise
     */
    public boolean isOnLog() {
        return onLog;
    }

    /**
     * Gets the image view of the frog.
     *
     * @return the image view of the frog
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the number of lives of the frog.
     *
     * @return the number of lives of the frog
     */
    public int getLives() {
        return lives;
    }

    /**
     * Decreases the number of lives of the frog by one.
     */
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    /**
     * Increases the number of lives of the frog by one.
     */
    public void gainLife() {
        lives++;
    }

    /**
     * Collects a token if the frog is at the same position as the token.
     *
     * @param token the token to collect
     */
    public void collectToken(final Token token) {
        if (this.getXPosition() == token.getXPosition() && this.getYPosition() == token.getYPosition()) {
            token.applyEffect(this);
        }
    }

    /**
     * Moves the frog based on the given key code.
     *
     * @param code the key code of the input
     */
    public void move(final KeyCode code) {
        switch (code) {
            case UP: 
                setYPosition(getYPosition() - GlobalVariables.JUMP_SIZE); 
                SoundManager.playSound("jump");
                break;
            case DOWN: 
                setYPosition(getYPosition() + GlobalVariables.JUMP_SIZE); 
                SoundManager.playSound("jump");
                break;
            case LEFT: 
                setXPosition(getXPosition() - GlobalVariables.JUMP_SIZE); 
                SoundManager.playSound("jump");
                break;
            case RIGHT: 
                setXPosition(getXPosition() + GlobalVariables.JUMP_SIZE); 
                SoundManager.playSound("jump");
                break;
            default: break;
        }
        // Limit movement within window boundaries
        setXPosition(Math.max(0, Math.min(getXPosition(), GlobalVariables.WIDTH - GlobalVariables.JUMP_SIZE)));
        setYPosition(Math.max(0, Math.min(getYPosition(), GlobalVariables.HEIGHT - GlobalVariables.JUMP_SIZE)));
        imageView.setX(getXPosition());
        imageView.setY(getYPosition());
    }

    /**
     * Resets the position of the frog to the given coordinates.
     *
     * @param x the new x position
     * @param y the new y position
     */
    public void resetPosition(final int x, final int y) {
        setXPosition(x);
        setYPosition(y);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * Sets the frog's state of being on a log.
     *
     * @param onLog       true if the frog is on a log, false otherwise
     * @param logSpeed    the speed of the log
     * @param logDirection the direction of the log
     */
    public void setOnLog(final boolean onLog, final int logSpeed, final int logDirection) {
        this.onLog = onLog;
        if (onLog) {
            this.logSpeed = logSpeed;
            this.logDirection = logDirection;
        } else {
            this.logSpeed = 0;
            this.logDirection = 0;
        }
    }

    /**
     * Updates the position of the frog if it is on a log.
     */
    public void updatePosition() {
        int frogLane = getLanePosition();

        //frog on middle lane
        if (frogLane == 6) {
            this.onLog = false;
            this.logSpeed = 0;
        }

        // frog on top lane
        if (frogLane == 0) {
            this.onLog = false;
            this.logSpeed = 0;
        }

        if (this.onLog) {
            setXPosition(getXPosition() + this.logSpeed * this.logDirection);
            setXPosition(Math.max(0, Math.min(getXPosition(), GlobalVariables.WIDTH - GlobalVariables.JUMP_SIZE)));
            imageView.setX(getXPosition() + this.logSpeed * this.logDirection);
        }
    }

    /**
     * Gets the lane position of the frog.
     *
     * @return the lane position of the frog
     */
    public int getLanePosition() {
        return (this.getYPosition() / GlobalVariables.LANE_HEIGHT);
    }
}
