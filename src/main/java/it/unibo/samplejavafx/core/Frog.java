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

    public Frog(int x, int y, int lives) {
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

    public void collectToken(Token token) {
        if (this.xPosition == token.getXPosition() && this.yPosition == token.getYPosition()) {
            token.applyEffect(this);
        }
    }

    public void move(KeyCode code) {
        switch (code) {
            case UP: 
                yPosition -= 46; 
                SoundManager.playSound("jump");
                break;
            case DOWN: 
                yPosition += 46; 
                SoundManager.playSound("jump");
                break;
            case LEFT: 
                xPosition -= 46; 
                SoundManager.playSound("jump");
                break;
            case RIGHT: 
                xPosition += 46; 
                SoundManager.playSound("jump");
                break;
            default: break;
        }
        // Limit movement within window boundaries
        xPosition = Math.max(0, Math.min(xPosition, 800 - 46));
        yPosition = Math.max(0, Math.min(yPosition, 600 - 46));
        imageView.setX(xPosition);
        imageView.setY(yPosition);
    }

    public void resetPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        imageView.setX(x);
        imageView.setY(y);
    }

    public void setOnLog(boolean onLog, int logSpeed, int logDirection) {
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
        // System.out.println("F onLog: " + this.onLog);
        // System.out.println("F logSpeed: " + this.logSpeed);
        // System.out.println("F logDirection: " + this.logDirection);
        int lheight = 600 / 13;
        int flane = this.getYPosition() / lheight;
        if ((flane == 0) || (flane == 6))
        {
            this.onLog = false;
            this.logSpeed = 0;
        }
        
        if (this.onLog) {
            xPosition += this.logSpeed * this.logDirection;
            xPosition = Math.max(0, Math.min(xPosition, 800 - 46));
            imageView.setX(xPosition);
        }
        //System.out.println("F position: " + xPosition);
    }
}