package it.unibo.samplejavafx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Menu {

    private Stage stage;
    private MainApp mainApp; // Reference to MainApp
    
    Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 36);
    //Font pixelFont = new Font(80);

    public Menu(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp; // Initialize the reference
    }

    public void createMenu() {
        
        Label titleLabel = new Label("Frogger");
        titleLabel.setFont(pixelFont);
        titleLabel.setStyle("-fx-text-fill: white;");

        Button newGameButton = new Button("New Game");
        Button quitButton = new Button("Quit");
        newGameButton.setFont(pixelFont);
        quitButton.setFont(pixelFont);

        newGameButton.setOnAction(e -> askForPlayerName());
        quitButton.setOnAction(e -> stage.close());

        // Use VBox for vertical arrangement
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20)); // Add some padding around the layout
        layout.getChildren().addAll(titleLabel, newGameButton, quitButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setStyle("-fx-background-color: black;");

        Scene menuScene = new Scene(layout, 800, 600); // Set the size of the menu
        stage.setScene(menuScene);
        stage.show(); // Show the menuu
    }

    private void startNewGame() {
        mainApp.setupGame(); // Call the setupGame method in MainApp
    }

    private void askForPlayerName() {
        VBox nameInputLayout = new VBox(20);
        nameInputLayout.setAlignment(javafx.geometry.Pos.CENTER);

        Label nameLabel = new Label("Enter your name:");
        nameLabel.setFont(pixelFont);
        TextField nameInputField = new TextField();
        nameInputField.setFont(pixelFont);
        nameInputField.setMaxWidth(500);

        Button submitButton = new Button("Submit");
        submitButton.setFont(pixelFont);
        submitButton.setOnAction(e -> {
            String playerName = nameInputField.getText();
            System.out.println("Player's name: " + playerName);
            mainApp.setupGame(); // Call setupGame from MainApp
        });

        nameInputLayout.getChildren().addAll(nameLabel, nameInputField, submitButton);
        nameInputLayout.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        Scene nameInputScene = new Scene(nameInputLayout, 800, 600);
        stage.setScene(nameInputScene); // Change to the name input scene
    }
}


