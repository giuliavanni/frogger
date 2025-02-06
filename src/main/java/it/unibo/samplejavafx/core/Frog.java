package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Frog extends GameObjectControllable {
    private int lives;
    private ImageView imageView;
    private boolean onLog = false;
    private int logSpeed = 0;
    private int logDirection = 0;

    private static final String IMAGE_PATH = "/froggy1.png"; // Path to your image

    public Frog(final int x, final int y, final int lives) {
        super(x, y);
        this.imageView = new ImageView(new Image(Frog.class.getResourceAsStream(IMAGE_PATH)));
        this.imageView.setFitWidth(40); // Set appropriate size
        this.imageView.setFitHeight(40); // Set appropriate size
        this.lives = lives;
    }

    public boolean isOnLog() {
        return onLog;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void gainLife() {
        lives++;
    }

    public void collectToken(final Token token) {
        if (this.getXPosition() == token.getXPosition() && this.getYPosition() == token.getYPosition()) {
            token.applyEffect(this);
        }
    }

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

    public void resetPosition(final int x, final int y) {
        setXPosition(x);
        setYPosition(y);
        imageView.setX(x);
        imageView.setY(y);
    }

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

    public void updatePosition() {
        int flane = this.getYPosition() / GlobalVariables.LANE_HEIGHT;

        //frog on middle line
        if (flane == 6) {
            this.onLog = false;
            this.logSpeed = 0;
        }

        // frog on top line
        if (flane == 0) {
            this.onLog = false;
            this.logSpeed = 0;
        }

        if (this.onLog) {
            setXPosition(getXPosition() + this.logSpeed * this.logDirection);
            setXPosition(Math.max(0, Math.min(getXPosition(), GlobalVariables.WIDTH - GlobalVariables.JUMP_SIZE)));
            imageView.setX(getXPosition());
        }
    }

    public int lanePosition() {
        return (this.getYPosition() / GlobalVariables.LANE_HEIGHT);
    }
}
