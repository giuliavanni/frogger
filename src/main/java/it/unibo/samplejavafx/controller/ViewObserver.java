package it.unibo.samplejavafx.controller;

import javafx.scene.input.KeyCode;

public interface ViewObserver {
    void handleInput(KeyCode code);
    void updateView();
}
