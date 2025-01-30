package it.unibo.samplejavafx.view;

import it.unibo.samplejavafx.core.Frog;
import it.unibo.samplejavafx.core.Lane;
import it.unibo.samplejavafx.core.GameObjectNotControllable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MatchView {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int LANE_HEIGHT = HEIGHT / 13; // 13 lanes in total

    private Canvas canvas;
    private GraphicsContext gc;
    private Font pixelFont;

    public MatchView() {
        this.canvas = new Canvas(WIDTH, HEIGHT); // Set the game window size to 800x600
        this.gc = canvas.getGraphicsContext2D();
        this.pixelFont = Font.loadFont(getClass().getResourceAsStream("/PressStart2P-Regular.ttf"), 36);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void clearScene() {
        gc.setFill(Color.BLACK); // Set the background color
        gc.fillRect(0, 0, WIDTH, HEIGHT); // Clear the canvas with the background color
    }

    public void renderFrog(Frog frog) {
        gc.drawImage(frog.getImageView().getImage(), frog.getXPosition(), frog.getYPosition(), 40, 40);
    }

    public void renderGroundLane(Lane lane, int laneIndex) {
        gc.setFill(Color.GREEN); // Set the background color to green for ground lanes
        gc.fillRect(0, laneIndex * LANE_HEIGHT, WIDTH, LANE_HEIGHT); // Fill the lane with green color
        for (GameObjectNotControllable obj : lane.getObjects()) {
            gc.drawImage(obj.getImageView().getImage(), obj.getXPosition(), obj.getYPosition(), obj.getImageView().getFitWidth(), obj.getImageView().getFitHeight());
        }
    }

    public void renderTrafficLane(Lane lane, int laneIndex) {
        gc.setFill(Color.BLACK); // Set the background color to black for traffic lanes
        gc.fillRect(0, laneIndex * LANE_HEIGHT, WIDTH, LANE_HEIGHT); // Fill the lane with gray color
        for (GameObjectNotControllable obj : lane.getObjects()) {
            gc.drawImage(obj.getImageView().getImage(), obj.getXPosition(), obj.getYPosition(), obj.getImageView().getFitWidth(), obj.getImageView().getFitHeight());
        }
    }

    public void renderLogLane(Lane lane, int laneIndex) {
        gc.setFill(Color.BLUE); // Set the background color to blue for log lanes
        gc.fillRect(0, laneIndex * LANE_HEIGHT, WIDTH, LANE_HEIGHT); // Fill the lane with blue color
        for (GameObjectNotControllable obj : lane.getObjects()) {
            gc.drawImage(obj.getImageView().getImage(), obj.getXPosition(), obj.getYPosition(), obj.getImageView().getFitWidth(), obj.getImageView().getFitHeight());
        }
    }

    public void renderToken(GameObjectNotControllable token) {
        gc.drawImage(token.getImageView().getImage(), token.getXPosition(), token.getYPosition(), token.getImageView().getFitWidth(), token.getImageView().getFitHeight());
    }

    public void updateFrogPosition(Frog frog) {
        renderFrog(frog); // Render the frog at the new position
    }

    public void renderGameOver(int score) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the screen
        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont);
        gc.fillText("Game Over", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2 - 50);
        gc.fillText("Score: " + score, canvas.getWidth() / 2 - 100, canvas.getHeight() / 2);
    }

    public void drawLaneLines() {
        gc.setStroke(Color.WHITE); // Make lines white for better visibility
        gc.setLineWidth(2); // Make lines thicker
        
        // Draw horizontal lines between lanes
        for (int i = 0; i <= 13; i++) { // Changed to <= to draw the bottom line
            double y = i * LANE_HEIGHT;
            gc.strokeLine(0, y, WIDTH, y);
        }
    }
}