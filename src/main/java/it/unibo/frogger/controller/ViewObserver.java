package it.unibo.frogger.controller;

import javafx.scene.input.KeyCode;

/**
 * This interface defines the methods that a view observer should implement
 * to handle user input and update the view.
 */
public interface ViewObserver {

    /**
     * Handles input from the user.
     *
     * @param code the key code of the input
     */
    void handleInput(KeyCode code);

    /**
     * Updates the view to reflect the current game state.
     */
    void updateView();
}
