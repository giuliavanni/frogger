package it.unibo.samplejavafx;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;


public class MainApp extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Frog frog;
    private List<Obstacle> obstacles;
    private Canvas canvas;
    private GraphicsContext gc;
    private Stage primaryStage;
    private boolean isPaused = false;
    private Text pauseText;
    Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/java/it/unibo/samplejavafx/PressStart2P-Regular.ttf"), 36);

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
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
    
        pauseText = new Text("Paused");
        pauseText.setFont(pixelFont);
        pauseText.setFill(javafx.scene.paint.Color.WHITE); // Set text color
        pauseText.setStyle("-fx-font-size: 36px;"); // Set font size
        pauseText.setVisible(false); // Initially hidden
        pauseText.setX(WIDTH / 2 - 50); // Center horizontally
        pauseText.setY(HEIGHT / 2); // Center vertically
    
        // Initialize Frog
        frog = new Frog(WIDTH / 2, HEIGHT - 50);
        obstacles = new ArrayList<>();
    
        // Create obstacles
        for (int i = 0; i < 5; i++) {
            double speed = Math.random() * 3 + 2;
            obstacles.add(new Obstacle(Math.random() * WIDTH, Math.random() * HEIGHT, speed));
        }
    
        // Set up the AnimationTimer for game updates
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused) {
                    update();
                    pauseText.setVisible(false);
                } else {
                    pauseText.setVisible(true);
                }
                draw(gc);
            }
        }.start();
    
        // Create a new Scene for the game and set it on the stage
        StackPane root = new StackPane(canvas, pauseText); // Ensure pauseText is added here
        Scene gameScene = new Scene(root, WIDTH, HEIGHT);
    
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                isPaused = !isPaused;
            } else {
                frog.move(event.getCode());
                frog.setX(frog.getX());
                frog.setY(frog.getY());
            }
        });
    
        // Update the stage with the new game scene
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }
    
    
    

    private void update() {
        for (Obstacle obstacle : obstacles) {
            obstacle.update(); // Update each obstacle's position
            if (checkCollision(frog, obstacle)) {
                handleCollision(obstacle); // Handle collision
            }
        }
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK); // Set the background color
        gc.fillRect(0, 0, WIDTH, HEIGHT); // Fill the canvas with the color

        // Draw the frog
        gc.drawImage(frog.getImage(), frog.getX(), frog.getY(), 40, 40);

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            gc.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), 80, 80);
        }
    }


    private boolean checkCollision(Frog frog, Obstacle obstacle) {
        return frog.getBoundsInParent().intersects(obstacle.getBoundsInParent());
    }

    private void handleCollision(Obstacle obstacle) {
        // Logic to handle collision
        System.out.println("Collision detected!");
        showGameOverScreen();
        frog.setX(WIDTH / 2);
        frog.setY(HEIGHT - 50);
    }

    private void showGameOverScreen() {
    
    // Create a VBox for the layout
    VBox gameOverLayout = new VBox(40);
    gameOverLayout.setAlignment(javafx.geometry.Pos.CENTER);
    
    // Create a label for the Game Over message
    Label gameOverLabel = new Label("Game Over");
    gameOverLabel.setStyle("36px; -fx-text-fill: white;");
    gameOverLabel.setFont(pixelFont);
    
    // Button to restart the game
    Button restartButton = new Button("Restart");
    restartButton.setFont(pixelFont);
    restartButton.setOnAction(e -> {
        setupGame(); // Call your method to set up a new game
    });
    
    // Button to quit
    Button quitButton = new Button("Quit");
    quitButton.setFont(pixelFont);
    quitButton.setOnAction(e -> {
        primaryStage.close();
    });
    
    // Add the components to the layout
    gameOverLayout.getChildren().addAll(gameOverLabel, restartButton, quitButton);
    
    // Set the background color to black
    gameOverLayout.setStyle("-fx-background-color: black;");
    
    // Create a new scene and set it
    Scene gameOverScene = new Scene(gameOverLayout, WIDTH, HEIGHT);
    primaryStage.setScene(gameOverScene);
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
            /* 
            The following line raises: Error: class it.unibo.samplejavafx.JavaFXApp$Main 
            is not a subclass of javafx.application.Application
            Because if you do not provide the Application subclass to launch() it will consider the enclosing class)
            */
            // JavaFXApp.launch(args);
            // Whereas the following would do just fine:
            // JavaFXApp.run(args)
        }
    }
}
