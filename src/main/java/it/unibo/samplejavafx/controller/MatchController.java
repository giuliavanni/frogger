package it.unibo.samplejavafx.controller;

import it.unibo.samplejavafx.core.Frog;
import it.unibo.samplejavafx.view.MatchView;

public class MatchController {
    private Frog frog;
    private MatchView view;

    public MatchController(Frog frog, MatchView view) {
        this.frog = frog;
        this.view = view;
    }

    public void handleInput(String input) {
        // Implement input handling logic
    }

    public void updateView() {
        // Implement view update logic
    }
}