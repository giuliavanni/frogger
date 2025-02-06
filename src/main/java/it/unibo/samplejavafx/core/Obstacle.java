package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends GameObjectNotControllable {
    private static final String IMAGE_PATH = "/bike.png";
    private static final String IMAGE_REVERSED_PATH = "/biker.png";

    public Obstacle(int x, int y, int direction) {
        super(x, y, new ImageView(new Image(Obstacle.class.getResourceAsStream(direction == -1 ? IMAGE_REVERSED_PATH : IMAGE_PATH))));
        this.getImageView().setFitWidth(70); // Set appropriate size
        this.getImageView().setFitHeight(70); // Set appropriate size
    }

    @Override
    public void updatePosition() {
        // The position update logic will be handled by the Lane class
    }

    @Override
    public void setPosition(int x, int y) {
        setXPosition(x);
        setYPosition(y);
    }
}
