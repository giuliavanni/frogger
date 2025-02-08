package it.unibo.frogger.view;

import it.unibo.frogger.core.GlobalVariables;
import it.unibo.frogger.core.SoundManager;
import it.unibo.frogger.main.MainApp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class represents the main menu of the Frogger game.
 */
public class Menu {
    private Stage stage;
    private MainApp mainApp;

    private Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 36);

    /**
     * Constructs a new Menu.
     *
     * @param stage   the primary stage for this application
     * @param mainApp the main application instance
     */
    public Menu(final Stage stage, final MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    /**
     * Creates the main menu layout and displays it.
     */
    public void createMenu() {
        Label titleLabel = new Label("Frogger");
        titleLabel.setFont(pixelFont);
        titleLabel.setStyle("-fx-text-fill: white;");

        Button newGameButton = new Button("New Game");
        Button settingsButton = new Button();
        Button quitButton = new Button("Quit");
        newGameButton.setFont(pixelFont);
        quitButton.setFont(pixelFont);

        // Set icon for settings button
        ImageView settingsIcon = new ImageView(new Image(getClass().getResourceAsStream("/gear.png")));
        settingsIcon.setFitWidth(30);
        settingsIcon.setFitHeight(30);
        settingsButton.setGraphic(settingsIcon);

        newGameButton.setOnAction(e -> askForPlayerName());
        settingsButton.setOnAction(e -> openSettings());
        quitButton.setOnAction(e -> stage.close());

        // Use VBox for vertical arrangement of buttons
        VBox centerLayout = new VBox(20);
        centerLayout.setPadding(new Insets(20)); // Add some padding around the layout
        centerLayout.getChildren().addAll(titleLabel, newGameButton, quitButton);
        centerLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Use HBox for settings button in top right corner
        HBox topLayout = new HBox();
        topLayout.setPadding(new Insets(10));
        topLayout.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        topLayout.getChildren().add(settingsButton);

        // Use BorderPane for overall layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(centerLayout);
        mainLayout.setTop(topLayout);
        mainLayout.setStyle("-fx-background-color: black;");

        Scene menuScene = new Scene(mainLayout, GlobalVariables.WIDTH, GlobalVariables.HEIGHT); // Set the size of the menu
        stage.setScene(menuScene);
        stage.show(); // Show the menu
    }

    /**
     * Starts a new game by calling the setupGame method in MainApp.
     */
    private void startNewGame() {
        mainApp.setupGame();
    }

    /**
     * Asks the player for their name before starting a new game.
     */
    private void askForPlayerName() {
        VBox nameInputLayout = new VBox(20);
        nameInputLayout.setAlignment(javafx.geometry.Pos.CENTER);

        Label nameLabel = new Label("Enter your name:");
        nameLabel.setFont(pixelFont);
        nameLabel.setStyle("-fx-text-fill: white;"); // Set text color to white
        TextField nameInputField = new TextField();
        nameInputField.setFont(pixelFont);
        nameInputField.setMaxWidth(500);

        Button submitButton = new Button("Submit");
        submitButton.setFont(pixelFont);
        submitButton.setOnAction(e -> {
            SoundManager.playSound("click");
            String playerName = nameInputField.getText();
            System.out.println("Player's name: " + playerName);
            mainApp.setPlayerName(playerName);
            startNewGame(); // Call setupGame from MainApp
        });

        nameInputLayout.getChildren().addAll(nameLabel, nameInputField, submitButton);
        nameInputLayout.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        Scene nameInputScene = new Scene(nameInputLayout, GlobalVariables.WIDTH, GlobalVariables.HEIGHT);
        stage.setScene(nameInputScene); // Change to the name input scene
    }

    /**
     * Opens the settings dialog.
     */
    private void openSettings() {
        SettingsDialog settingsDialog = new SettingsDialog(stage);
        settingsDialog.show();
    }
}
