package it.unibo.frogger.main;

import java.util.Properties;

import it.unibo.frogger.controller.MatchController;
import it.unibo.frogger.core.GameSettingsManager;
import it.unibo.frogger.core.GlobalVariables;
import it.unibo.frogger.core.Match;
import it.unibo.frogger.core.SoundManager;
import it.unibo.frogger.view.MatchView;
import it.unibo.frogger.view.Menu;
import it.unibo.frogger.view.SettingsDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

/**
 * The main application class for the Frogger game.
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private MatchView matchView;
    private Match match;
    private MatchController matchController;
    private Button settingsButton;
    private Label pausedLabel;
    private String playerName;
    private ProgressBar timerBar;

    /**
     * Gets the player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player name.
     *
     * @param playerName the player name to set
     */
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    /**
     * Starts the application.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(final Stage primaryStage) {
        Properties settings = GameSettingsManager.loadSettings();
        double musicVolume = Double.parseDouble(settings.getProperty("musicVolume", "0.5"));
        double effectsVolume = Double.parseDouble(settings.getProperty("effectsVolume", "0.5"));
        SoundManager.setMusicVolume(musicVolume);
        SoundManager.setEffectsVolume(effectsVolume);
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Frogger");
        showMenu();
    }

    /**
     * Sets up the game by initializing the match view, match, and match controller.
     */
    public void setupGame() {
        matchView = new MatchView(primaryStage, this); // Pass stage and this
        match = new Match(matchView);
        matchController = new MatchController(match.getFrog(), match.getLanes(), match.getObjects(), matchView, this);

        // Create a new Scene for the game and set it on the stage
        StackPane root = new StackPane(matchView.getCanvas());

        // Add settings button to the top right corner
        settingsButton = new Button();
        ImageView settingsIcon = new ImageView(new Image(getClass().getResourceAsStream("/gear.png")));
        settingsIcon.setFitWidth(30);
        settingsIcon.setFitHeight(30);
        settingsButton.setGraphic(settingsIcon);
        settingsButton.setOnAction(e -> openSettings());
        settingsButton.setVisible(false); // Initially hidden

        // Add paused label to the center
        pausedLabel = new Label("Paused");
        pausedLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 48));
        pausedLabel.setTextFill(Color.WHITE);
        pausedLabel.setVisible(false); // Initially hidden

        // Create progressbar for timer
        timerBar = new ProgressBar(1.0);  // Start at 100%
        timerBar.setPrefWidth(200); 
        timerBar.setPrefHeight(25);
        timerBar.setStyle("-fx-accent: green;");

        // Add all elements to scene
        root.getChildren().addAll(settingsButton, pausedLabel, timerBar);
        StackPane.setAlignment(settingsButton, javafx.geometry.Pos.TOP_RIGHT);
        StackPane.setMargin(settingsButton, new javafx.geometry.Insets(10));
        StackPane.setAlignment(pausedLabel, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(timerBar, javafx.geometry.Pos.BOTTOM_RIGHT);
        StackPane.setMargin(timerBar, new javafx.geometry.Insets(10));
        Scene gameScene = new Scene(root, GlobalVariables.WIDTH, GlobalVariables.HEIGHT);

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

    /**
     * Shows or hides the settings button.
     *
     * @param show true to show the settings button, false to hide it
     */
    public void showSettingsButton(final boolean show) {
        settingsButton.setVisible(show);
    }

    /**
     * Shows or hides the paused label.
     *
     * @param show true to show the paused label, false to hide it
     */
    public void showPausedLabel(final boolean show) {
        pausedLabel.setVisible(show);
    }

    /**
     * Updates the timer bar with the given progress.
     *
     * @param progress the progress to set on the timer bar (0.0 to 1.0)
     */
    public void showTimerBar(final double progress) {
        timerBar.setProgress(progress);  // Update progress bar
    }

    /**
     * Opens the settings dialog.
     */
    private void openSettings() {
        SettingsDialog settingsDialog = new SettingsDialog(primaryStage);
        settingsDialog.show();
    }

    /**
     * Shows the main menu.
     */
    private void showMenu() {
        Menu menu = new Menu(primaryStage, this);
        menu.createMenu();
    }

    /**
     * The main method to launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
