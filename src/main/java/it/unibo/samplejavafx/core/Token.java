package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Token extends GameObjectNotControllable {
    private static final String IMAGE_PATH = "/token.png"; // Path to your token image

    public Token(final int x, final int y) {
        super(x, y, new ImageView(new Image(Token.class.getResourceAsStream(IMAGE_PATH))));
        this.getImageView().setFitWidth(30); // Set appropriate size
        this.getImageView().setFitHeight(30); // Set appropriate size
    }

    public void applyEffect(final Frog frog) {
        // Implement effect application logic
        frog.gainLife();
    }

    @Override
    public void updatePosition() {
        // Implement position update logic
    }
}
