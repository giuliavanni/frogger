package it.unibo.frogger.view;

import java.util.List;
import java.util.Map;

import it.unibo.frogger.core.Frog;
import it.unibo.frogger.core.Lane;
import it.unibo.frogger.core.PlayerScoreManager;
import it.unibo.frogger.core.GameObjectNotControllable;
import it.unibo.frogger.core.GlobalVariables;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.unibo.frogger.main.MainApp;

/**
 * This class represents the view for the match in the Frogger game.
 */
public class MatchView {
    private Canvas canvas;
    private GraphicsContext gc;
    private Font titlePixelFont;
    private Font subtitlePixelFont;
    private Font pixelFont;
    private Stage stage;
    private MainApp mainApp;

    /**
     * Constructs a new MatchView.
     *
     * @param stage   the primary stage for this application
     * @param mainApp the main application instance
     */
    public MatchView(final Stage stage, final MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.canvas = new Canvas(GlobalVariables.WIDTH, GlobalVariables.HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.titlePixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 36);
        this.subtitlePixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 24);
        this.pixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 16);
    }

    /**
     * Gets the canvas for the match view.
     *
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Clears the scene by filling it with the background color.
     */
    public void clearScene() {
        gc.setFill(Color.BLACK); // Set the background color
        gc.fillRect(0, 0, GlobalVariables.WIDTH, GlobalVariables.HEIGHT); // Clear the canvas with the background color
    }

    /**
     * Renders the frog on the canvas.
     *
     * @param frog the frog to render
     */
    public void renderFrog(final Frog frog) {
        gc.drawImage(
            frog.getImageView().getImage(),
            frog.getXPosition(), frog.getYPosition(),
            40,
            40
        );
    }

    /**
     * Renders a ground lane on the canvas.
     *
     * @param lane      the lane to render
     * @param laneIndex the index of the lane
     */
    public void renderGroundLane(final Lane lane, final int laneIndex) {
        gc.setFill(Color.PURPLE); // Set the background color to purple for ground lanes
        // Fill the lane with purple color
        gc.fillRect(
            0, 
            laneIndex * GlobalVariables.LANE_HEIGHT,
            GlobalVariables.WIDTH, 
            GlobalVariables.LANE_HEIGHT
        );
        for (GameObjectNotControllable obj : lane.getObjects()) {
            gc.drawImage(
                obj.getImageView().getImage(), 
                obj.getXPosition(), 
                obj.getYPosition(), 
                obj.getImageView().getFitWidth(), 
                obj.getImageView().getFitHeight()
            );
        }
    }

    /**
     * Renders a traffic lane on the canvas.
     *
     * @param lane      the lane to render
     * @param laneIndex the index of the lane
     */
    public void renderTrafficLane(final Lane lane, final int laneIndex) {
        gc.setFill(Color.BLACK); // Set the background color to black for traffic lanes
        // Fill the lane with black color
        gc.fillRect(
            0, 
            laneIndex * GlobalVariables.LANE_HEIGHT, 
            GlobalVariables.WIDTH, 
            GlobalVariables.LANE_HEIGHT
        );
        for (GameObjectNotControllable obj : lane.getObjects()) {
            gc.drawImage(
                obj.getImageView().getImage(), 
                obj.getXPosition(), 
                obj.getYPosition(), 
                obj.getImageView().getFitWidth(), 
                obj.getImageView().getFitHeight()
            );
        }
    }

    /**
     * Renders a log lane on the canvas.
     *
     * @param lane      the lane to render
     * @param laneIndex the index of the lane
     */
    public void renderLogLane(final Lane lane, final int laneIndex) {
        gc.setFill(Color.BLUE); // Set the background color to blue for log lanes
        // Fill the lane with blue color
        gc.fillRect(
            0, 
            laneIndex * GlobalVariables.LANE_HEIGHT, 
            GlobalVariables.WIDTH, 
            GlobalVariables.LANE_HEIGHT
        );
        for (GameObjectNotControllable obj : lane.getObjects()) {
            gc.drawImage(
                obj.getImageView().getImage(), 
                obj.getXPosition(), 
                obj.getYPosition(), 
                obj.getImageView().getFitWidth(), 
                obj.getImageView().getFitHeight()
            );
        }
    }

    /**
     * Renders a token on the canvas.
     *
     * @param token the token to render
     */
    public void renderToken(final GameObjectNotControllable token) {
        gc.drawImage(
            token.getImageView().getImage(), 
            token.getXPosition(), 
            token.getYPosition(), 
            token.getImageView().getFitWidth(), 
            token.getImageView().getFitHeight()
        );
    }

    /**
     * Updates the position of the frog on the canvas.
     *
     * @param frog the frog to update
     */
    public void updateFrogPosition(final Frog frog) {
        renderFrog(frog); // Render the frog at the new position
    }

    /**
     * Renders the lives of the frog on the canvas.
     *
     * @param frog the frog whose lives to render
     */
    public void renderLives(final Frog frog) {
        // Define size and position of lives icons
        final int lifeIconSize = 30;  // Smaller size for lives icons
        final int padding = 10;         // Space from border
        final int spacing = 5;          // Space between icons
        final int baseY = GlobalVariables.HEIGHT - lifeIconSize - padding;  // Base Y position

        // For each lives draw a Frog icon
        for (int i = 0; i < frog.getLives(); i++) {
            double x = padding + (i * (lifeIconSize + spacing));
            gc.drawImage(frog.getImageView().getImage(), x, baseY, lifeIconSize, lifeIconSize);
        }
    }

    /**
     * Updates the timer display with the given progress.
     *
     * @param progress the progress to set on the timer bar (0.0 to 1.0)
     */
    public void updateTimerDisplay(final double progress) {
        mainApp.showTimerBar(progress);  // Update the progress bar in the main application
    }

    /**
     * Renders the score on the canvas.
     *
     * @param score the score to render
     */
    public void renderScore(final int score) {
        gc.setFill(Color.WHITE); // Set the text color to white
        gc.setFont(pixelFont); // Use the pixel font
        gc.fillText("Score: " + score, 10, 20); // Draw the score at the top-left corner
    }

    /**
     * Renders the game over screen with the final score.
     *
     * @param score the final score
     */
    public void renderGameOver(final int score) {
        showGameOverScreen(score);
    }

    /**
     * Shows the game over screen with the final score and top scores.
     *
     * @param score the final score
     */
    private void showGameOverScreen(final int score) {
        VBox gameOverLayout = new VBox(30);  // Reduce spacing between elements
        gameOverLayout.setAlignment(javafx.geometry.Pos.CENTER);
        gameOverLayout.setPadding(new Insets(10));  // Add padding around the layout

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(titlePixelFont);
        gameOverLabel.setStyle("-fx-text-fill: white;");

        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setFont(subtitlePixelFont);
        scoreLabel.setStyle("-fx-text-fill: white;");

        // Add top scores
        List<Map.Entry<String, Integer>> topScores = PlayerScoreManager.getTopScores(5);
        VBox topScoresLayout = new VBox(5);  // Reduce spacing between score entries
        topScoresLayout.setAlignment(javafx.geometry.Pos.CENTER);
        Label topScoresLabel = new Label("Top 5 Scores:");
        topScoresLabel.setFont(pixelFont);
        topScoresLabel.setStyle("-fx-text-fill: white;");
        topScoresLayout.getChildren().add(topScoresLabel);
        for (Map.Entry<String, Integer> entry : topScores) {
            Label scoreEntryLabel = new Label(entry.getKey() + ": " + entry.getValue());
            scoreEntryLabel.setFont(pixelFont);
            scoreEntryLabel.setStyle("-fx-text-fill: white;");
            topScoresLayout.getChildren().add(scoreEntryLabel);
        }

        Button restartButton = new Button("Restart");
        restartButton.setFont(titlePixelFont);
        restartButton.setOnAction(e -> mainApp.setupGame());

        Button quitButton = new Button("Quit");
        quitButton.setFont(titlePixelFont);
        quitButton.setOnAction(e -> stage.close());

        gameOverLayout.getChildren().addAll(gameOverLabel, scoreLabel, topScoresLayout, restartButton, quitButton);
        gameOverLayout.setStyle("-fx-background-color: black;");

        Scene gameOverScene = new Scene(gameOverLayout, GlobalVariables.WIDTH, GlobalVariables.HEIGHT);
        stage.setScene(gameOverScene);
    }

    /**
     * Draws horizontal lines between lanes on the canvas.
     */
    public void drawLaneLines() {
        gc.setStroke(Color.WHITE); // Make lines white for better visibility
        gc.setLineWidth(2); // Make lines thicker

        // Draw horizontal lines between lanes
        for (int i = 0; i <= 13; i++) { // Changed to <= to draw the bottom line
            double y = i * GlobalVariables.LANE_HEIGHT;
            gc.strokeLine(0, y, GlobalVariables.WIDTH, y);
        }
    }

    /**
     * Gets the player name from the main application.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return mainApp.getPlayerName();
    }
}
