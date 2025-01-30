package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Token extends GameObjectNotControllable {

    private static final String IMAGE_PATH = "/token.png"; // Path to your token image

    public Token(int x, int y) {
        super(x, y, new ImageView(new Image(Token.class.getResourceAsStream(IMAGE_PATH))));
        this.imageView.setFitWidth(30); // Set appropriate size
        this.imageView.setFitHeight(30); // Set appropriate size
    }

    public void applyEffect(Frog frog) {
        // Implement effect application logic
        frog.gainLife();
    }

    @Override
    public void updatePosition() {
        // Implement position update logic
    }
}