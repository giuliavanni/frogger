package it.unibo.samplejavafx.core;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Log extends GameObjectNotControllable {
    private static final String IMAGE_PATH = "/log.png"; // Path to your log image
    private int speed;
    private int direction;
    private List<GameObjectControllable> laneObjects; // Lista degli oggetti della lane

    public Log(int x, int y, int speed, int direction) {
        super(x, y, new ImageView(new Image(Log.class.getResourceAsStream(IMAGE_PATH))));
        this.getImageView().setFitWidth(100); // Set appropriate size
        this.getImageView().setFitHeight(40); // Set appropriate size
        this.speed = speed;
        this.direction = direction;
    }

    @Override
    public void updatePosition() {
        // Implement position update logic
        setXPosition(getXPosition() + speed * direction);

        // Controlla se la rana è sopra il tronco
        for (GameObjectControllable obj : laneObjects) {
            // Verifica se l'oggetto è un'istanza di Frog
            if (obj instanceof Frog) {
                Frog frog = (Frog) obj;  // Fai il cast sicuro a Frog

                // Verifica se la rana è sopra il tronco
                if (frog.getYPosition() == this.getYPosition() && 
                    frog.getXPosition() >= this.getXPosition() && 
                    frog.getXPosition() <= this.getXPosition() + this.getImageView().getFitWidth()) {
                    // La rana è sopra il tronco, quindi deve muoversi insieme
                    frog.setOnLog(true, this.speed, this.direction);
                } else {
                    // La rana non è più sopra il tronco
                    frog.setOnLog(false, 0, 0);
                }
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }
}
