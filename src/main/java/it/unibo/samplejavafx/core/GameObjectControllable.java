package it.unibo.samplejavafx.core;

public abstract class GameObjectControllable {
    protected int xPosition;
    protected int yPosition;

    public GameObjectControllable(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public abstract void move(String direction);
}
