package org.techelevator.snakes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakesApplication extends Application {
    // ----------------------------------------------
    // CONSTANTS
    // ----------------------------------------------
    private final static int FRAMES_PER_SECOND = 30;

    // ----------------------------------------------
    // GRAPHICS CONTEXT
    // ----------------------------------------------
    private GraphicsContext ctx;

    // ----------------------------------------------
    // KEYBOARD STATE
    // ----------------------------------------------
    private boolean keydown_up = false;
    private boolean keydown_down = false;
    private boolean keydown_left = false;
    private boolean keydown_right = false;
    private boolean keydown_space = false;

    // ----------------------------------------------
    // GAME STATE
    // ----------------------------------------------
    private int squareX = 0;
    private int squareY = 0;

    // ----------------------------------------------
    // GAME BOILERPLATE
    // ----------------------------------------------
    /**
     * Main method to start the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Start the application, set up the game loop and key listeners
     *
     * Don't worry much about this method, it's just setting up the game loop and key listeners
     * and won't need any modification from this point forward
     *
     * @param stage JavaFX stage
     * @throws IOException if the stage cannot be created
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("The Movable Red Rectangle! (Use Arrow Keys to Move)");
        stage.setResizable(false);
        Pane root = new Pane();
        Canvas canvas = new Canvas(800, 800);

        // give canvas black background
        root.setStyle("-fx-background-color: black");

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                        keydown_up = true;
                        break;
                    case DOWN:
                        keydown_down = true;
                        break;
                    case LEFT:
                        keydown_left = true;
                        break;
                    case RIGHT:
                        keydown_right = true;
                        break;
                    case SPACE:
                        keydown_space = true;
                        break;
                }
            }
        });

        canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                        keydown_up = false;
                        break;
                    case DOWN:
                        keydown_down = false;
                        break;
                    case LEFT:
                        keydown_left = false;
                        break;
                    case RIGHT:
                        keydown_right = false;
                        break;
                    case SPACE:
                        keydown_space = false;
                        break;
                }
            }
        });
        ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
        new AnimationTimer() {
            private long lastUpdate = System.nanoTime();
            private long currentFrame = 0;

            public void handle(long presentNanoTime) {
                if (presentNanoTime - lastUpdate > (1000000000 / FRAMES_PER_SECOND)) {
                    currentFrame++;
                    update(currentFrame);
                    draw(currentFrame);
                    lastUpdate = presentNanoTime;
                }
            }
        }.start();
        stage.show();
    }

    // ----------------------------------------------
    // GAME LOGIC
    //
    // (Update and then Draw are called repeatedly 30
    // times per second)
    // ----------------------------------------------
    /**
     * Update the game state
     *
     * @param currentFrame the current frame number
     */
    public void update(long currentFrame) {
        if (keydown_up && squareY > 0) {
            squareY -= 5;
        }
        if (keydown_down && squareY < 750) {
            squareY += 5;
        }
        if (keydown_left && squareX > 0) {
            squareX -= 5;
        }
        if (keydown_right && squareX < 750) {
            squareX += 5;
        }
    }

    /**
     * Draw the game frame
     *
     * @param currentFrame the current frame number
     */
    public void draw(long currentFrame) {
        ctx.clearRect(0, 0, 800, 800);
        ctx.setFill(Color.RED);
        ctx.fillRect(squareX, squareY, 50, 50);
    }
}