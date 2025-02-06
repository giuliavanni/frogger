package it.unibo.samplejavafx.core;

import javafx.scene.input.KeyCode;

public abstract class GameObjectControllable {
    private int xPosition;
    private int yPosition;

    public GameObjectControllable(final int x, final int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(final int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(final int yPosition) {
        this.yPosition = yPosition;
    }

    public abstract void move(KeyCode code);
}
