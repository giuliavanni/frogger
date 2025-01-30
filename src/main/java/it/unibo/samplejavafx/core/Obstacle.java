package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends GameObjectNotControllable {

    private static final String IMAGE_PATH = "/bike.png";

    public Obstacle(int x, int y) {
        super(x, y, new ImageView(new Image(Obstacle.class.getResourceAsStream(IMAGE_PATH))));
        this.imageView.setFitWidth(70); // Set appropriate size
        this.imageView.setFitHeight(70); // Set appropriate size
    }

    @Override
    public void updatePosition() {
        // The position update logic will be handled by the Lane class
    }

    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.imageView.setX(x);
        this.imageView.setY(y);
    }
}