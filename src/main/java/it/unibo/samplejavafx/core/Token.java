package it.unibo.samplejavafx.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a token in the game that the frog can collect.
 * When collected, the token applies an effect to the frog.
 */
public class Token extends GameObjectNotControllable {
    private static final String IMAGE_PATH = "/token.png"; // Path to your token image

    /**
     * Constructs a new Token at the specified position.
     *
     * @param x the x-coordinate of the token
     * @param y the y-coordinate of the token
     */
    public Token(final int x, final int y) {
        super(x, y, new ImageView(new Image(Token.class.getResourceAsStream(IMAGE_PATH))));
        this.getImageView().setFitWidth(30); // Set appropriate size
        this.getImageView().setFitHeight(30); // Set appropriate size
    }

    /**
     * Applies the effect of the token to the specified frog.
     * In this case, the frog gains an extra life.
     *
     * @param frog the frog to which the effect is applied
     */
    public void applyEffect(final Frog frog) {
        frog.gainLife();
    }

    /**
     * Updates the position of the token.
     * This method is currently not implemented as tokens do not move.
     */
    @Override
    public void updatePosition() {
        // Implement position update logic
    }
}
