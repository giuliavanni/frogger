package it.unibo.samplejavafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Frog extends ImageView {
    private double x, y;
    private static final String IMAGE_PATH = "/froggy1.png"; // Path to your image

    public Frog(double x, double y) {
        super(new Image(Frog.class.getResourceAsStream(IMAGE_PATH))); // Load image here
        this.x = x;
        this.y = y;
        setX(x);
        setY(y);
        setFitWidth(40);
        setFitHeight(40);
    }

    public void move(KeyCode code) {
        switch (code) {
            case UP: y -= 20; break;
            case DOWN: y += 20; break;
            case LEFT: x -= 20; break;
            case RIGHT: x += 20; break;
            default: break;
        }
        // Limit movement within window boundaries
        x = Math.max(0, Math.min(x, 800 - 20));
        y = Math.max(0, Math.min(y, 600 - 20));

        // Update the position of the frog
        setX(x);
        setY(y);
    }
}
