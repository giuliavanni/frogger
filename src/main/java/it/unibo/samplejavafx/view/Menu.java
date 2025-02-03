package it.unibo.samplejavafx.view;

import it.unibo.samplejavafx.core.SoundManager;
import it.unibo.samplejavafx.main.MainApp;
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

public class Menu {

    private Stage stage;
    private MainApp mainApp; // Reference to MainApp
    
    Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 36);

    public Menu(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp; // Initialize the reference
    }

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

        newGameButton.setOnAction(e -> {
            SoundManager.playSound("click");
            askForPlayerName();
        });
        settingsButton.setOnAction(e -> {
            SoundManager.playSound("click");
            openSettings();
        });
        quitButton.setOnAction(e -> {
            SoundManager.playSound("click");
            stage.close();
        });

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

        Scene menuScene = new Scene(mainLayout, 800, 600); // Set the size of the menu
        stage.setScene(menuScene);
        stage.show(); // Show the menu
    }

    private void startNewGame() {
        mainApp.setupGame(); // Call the setupGame method in MainApp
    }

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
            startNewGame(); // Call setupGame from MainApp
        });

        nameInputLayout.getChildren().addAll(nameLabel, nameInputField, submitButton);
        nameInputLayout.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        Scene nameInputScene = new Scene(nameInputLayout, 800, 600);
        stage.setScene(nameInputScene); // Change to the name input scene
    }

    private void openSettings() {
        SettingsDialog settingsDialog = new SettingsDialog(stage);
        settingsDialog.show();
    }
}