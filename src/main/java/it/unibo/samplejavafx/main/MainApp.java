package it.unibo.samplejavafx.main;

import it.unibo.samplejavafx.core.Match;
import it.unibo.samplejavafx.view.MatchView;
import it.unibo.samplejavafx.view.Menu;
import it.unibo.samplejavafx.controller.MatchController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class MainApp extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Stage primaryStage;
    private Match match;
    private MatchView matchView;
    private MatchController matchController;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Frogger");
        showMenu(primaryStage); // Show the menu first
    }

    private void showMenu(Stage primaryStage) {
        Menu menu = new Menu(primaryStage, this); // Pass this instance of MainApp to Menu
        menu.createMenu(); // Create and show the menu
    }

    public void setupGame() {
        System.out.println("Setting up the game..."); // Debugging output

        matchView = new MatchView();
        match = new Match(matchView);
        matchController = new MatchController(match.getFrog(), match.getLanes(), match.getObjects(), matchView);

        // Create a new Scene for the game and set it on the stage
        StackPane root = new StackPane(matchView.getCanvas());
        Scene gameScene = new Scene(root, WIDTH, HEIGHT);

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                matchController.togglePause();
            } else {
                matchController.handleInput(event.getCode());
            }
        });

        // Update the stage with the new game scene
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Entry point's class.
     */
    public static final class Main {
        private Main() {
            // the constructor will never be called directly.
        }

        /**
         * Program's entry point.
         * @param args
         */
        public static void main(final String...args) {
            Application.launch(MainApp.class, args);
        }
    }
}