package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Log extends GameObjectNotControllable {

    private static final String IMAGE_PATH = "/log.png"; // Path to your log image
    private int speed;

    public Log(int x, int y, int speed) {
        super(x, y, new ImageView(new Image(Log.class.getResourceAsStream(IMAGE_PATH))));
        this.imageView.setFitWidth(100); // Set appropriate size
        this.imageView.setFitHeight(40); // Set appropriate size
        this.speed = speed;
    }

    @Override
    public void updatePosition() {
        // Implement position update logic
    }

    public int getSpeed() {
        return speed;
    }
}