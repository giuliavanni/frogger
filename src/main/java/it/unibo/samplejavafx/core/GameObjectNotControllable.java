package it.unibo.samplejavafx.core;

import javafx.scene.image.ImageView;

public abstract class GameObjectNotControllable {
    private int xPosition;
    private int yPosition;
    private ImageView imageView;

    public GameObjectNotControllable(final int xPosition, final int yPosition, final ImageView imageView) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.imageView = imageView;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(final int xPosition) {
        this.xPosition = xPosition;
        this.imageView.setX(xPosition);
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(final int yPosition) {
        this.yPosition = yPosition;
        this.imageView.setY(yPosition);
    }

    public void setPosition(final int xPosition, final int yPosition) {
        setXPosition(xPosition);
        setYPosition(yPosition);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(final ImageView imageView) {
        this.imageView = imageView;
    }

    public abstract void updatePosition();
}
