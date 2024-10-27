package it.unibo.samplejavafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends ImageView {
    private double speed; // Speed of the obstacle

    private static final String IMAGE_PATH = "/bike.png";
    public Obstacle(double x, double y, double speed) {
        super(new Image(Obstacle.class.getResourceAsStream(IMAGE_PATH))); // Load your obstacle image
        this.setX(x);
        this.setY(y);
        this.speed = speed;
        setFitWidth(80); // Set appropriate size
        setFitHeight(80); // Set appropriate size
    }

    public void update() {
        setX(getX() + speed); // Move the obstacle horizontally
        // Reset position if it moves off-screen
        if (getX() > 800) { // Assuming the width of your window is 800
            setX(-getFitWidth()); // Reset to the left side
        }
    }
}
