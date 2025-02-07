package it.unibo.samplejavafx.core;

import java.util.List;

/**
 * This class represents a lane in the game, which contains objects that move at a certain speed and direction.
 */
public class Lane {
    private int speed;
    private int direction;
    private List<GameObjectNotControllable> objects;

    /**
     * Constructs a new Lane.
     *
     * @param speed     the speed at which objects in the lane move
     * @param direction the direction in which objects in the lane move (1 for right, -1 for left)
     * @param objects   the list of objects in the lane
     */
    public Lane(final int speed, final int direction, final List<GameObjectNotControllable> objects) {
        this.speed = speed;
        this.direction = direction;
        this.objects = objects;
    }

    /**
     * Gets the speed of the lane.
     *
     * @return the speed of the lane
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the direction of the lane.
     *
     * @return the direction of the lane (1 for right, -1 for left)
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Gets the list of objects in the lane.
     *
     * @return the list of objects in the lane
     */
    public List<GameObjectNotControllable> getObjects() {
        return objects;
    }

    /**
     * Updates the position of the objects in the lane based on the speed and direction.
     * If an object moves off-screen, its position is reset to the opposite side.
     */
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
