package it.unibo.samplejavafx.core;

import javafx.scene.image.ImageView;

public abstract class GameObjectNotControllable {
    protected int xPosition;
    protected int yPosition;
    protected ImageView imageView;

    public GameObjectNotControllable(int xPosition, int yPosition, ImageView imageView) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.imageView = imageView;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public abstract void updatePosition();
}