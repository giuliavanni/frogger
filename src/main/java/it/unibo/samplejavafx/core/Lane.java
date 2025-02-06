package it.unibo.samplejavafx.core;

import java.util.List;

public class Lane {
    private int speed;
    private int direction; // 1 for right, -1 for left
    private List<GameObjectNotControllable> objects;

    public Lane(final int speed, final int direction, final List<GameObjectNotControllable> objects) {
        this.speed = speed;
        this.direction = direction;
        this.objects = objects;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public List<GameObjectNotControllable> getObjects() {
        return objects;
    }

    public void updateObjectsPosition() {
        for (GameObjectNotControllable obj : objects) {
            int newX = obj.getXPosition() + (speed * direction);
            obj.setPosition(newX, obj.getYPosition());

            // Reset position if it moves off-screen
            if (newX > GlobalVariables.WIDTH) { // Assuming the width of your window is 800
                obj.setPosition((int) -obj.getImageView().getFitWidth(), obj.getYPosition());
            } else if (newX < -obj.getImageView().getFitWidth()) {
                obj.setPosition(GlobalVariables.WIDTH, obj.getYPosition());
            }
        }
    }
}
