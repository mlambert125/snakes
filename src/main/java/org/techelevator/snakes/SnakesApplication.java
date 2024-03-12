package org.techelevator.snakes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main application class for the Snakes game
 */
public class SnakesApplication extends Application {
    // ----------------------------------------------
    // CONSTANTS
    // ----------------------------------------------
    /**
     * The number of frames per second
     */
    private final static int FRAMES_PER_SECOND = 60;

    /**
     * The title screen
     */
    private final static int SCREEN_TITLE = 0;

    /**
     * The game screen
     */
    private final static int SCREEN_GAME = 1;

    /**
     * The game over screen
     */
    private final static int SCREEN_GAME_OVER = 2;

    // ----------------------------------------------
    // GRAPHICS CONTEXT
    // ----------------------------------------------
    /**
     * The graphics context to draw to
     */
    private GraphicsContext ctx;

    // ----------------------------------------------
    // KEYBOARD STATE
    // ----------------------------------------------
    /**
     * Whether the up key is currently pressed
     */
    private boolean keydown_up = false;

    /**
     * Whether the down key is currently pressed
     */
    private boolean keydown_down = false;

    /**
     * Whether the left key is currently pressed
     */
    private boolean keydown_left = false;

    /**
     * Whether the right key is currently pressed
     */
    private boolean keydown_right = false;

    /**
     * Whether the space key is currently pressed
     */
    private boolean keydown_space = false;

    // ----------------------------------------------
    // GAME STATE
    // ----------------------------------------------
    /**
     * The snake
     */
    private Snake snake = new Snake();

    /**
     * The apple
     */
    private Apple apple = new Apple();

    /**
     * The speed of the game
     */
    private int speed = 3;

    /**
     * The change in speed to apply
     */
    private int speedChange = 0;

    /**
     * The currently active screen
     */
    private int currentScreen = SCREEN_TITLE;

    /**
     * The frame number when the game over screen was activated
     */
    private long gameOverFrame = 0;

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
        stage.setTitle("SNAKES!!!");
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
        if (currentScreen == SCREEN_GAME) {
            if (keydown_up) {
                snake.setDirectionUp();
            }
            if (keydown_down) {
                snake.setDirectionDown();
            }
            if (keydown_left) {
                snake.setDirectionLeft();
            }
            if (keydown_right) {
                snake.setDirectionRight();
            }

            if (currentFrame % (6 - speed) == 0) {
                boolean shouldGrow = false;

                if (snake.getHead().equals(apple)) {
                    apple.moveToRandomLocation();
                    shouldGrow = true;
                }
                snake.move(shouldGrow);

                if (snake.isDead()) {
                    gameOverFrame = currentFrame;
                    currentScreen = SCREEN_GAME_OVER;
                }
            }
        } else if (currentScreen == SCREEN_TITLE) {
            if (keydown_up && speed < 5) {
                speedChange = 1;
            }
            if (keydown_down && speed > 1) {
                speedChange = -1;
            }

            if (currentFrame % 10 == 0) {
                speed += speedChange;
                speedChange = 0;
            }

            if (keydown_space) {
                snake = new Snake();
                apple = new Apple();
                currentScreen = SCREEN_GAME;
            }
        } else if (currentScreen == SCREEN_GAME_OVER) {
            if (currentFrame - gameOverFrame == 90) {
                currentScreen = SCREEN_TITLE;
            }
        }
    }

    /**
     * Draw the game state
     *
     * @param currentFrame the current frame number
     */
    public void draw(long currentFrame) {
        ctx.clearRect(0, 0, 800, 800);

        if (currentScreen == SCREEN_GAME) {
            Image background = new Image("file:background.png");
            ctx.drawImage(background, 0, 0, 800, 800);
            apple.draw(ctx, currentFrame);
            snake.draw(ctx, currentFrame);
            ctx.setFill(Color.WHITE);
            ctx.fillText("Score: " + snake.getLength(), 700, 12);
        } else if (currentScreen == SCREEN_TITLE) {
            // load and draw snakes.jpg
            Image img = new Image("file:snakes.jpg");
            ctx.drawImage(img, 0, 0, 800, 800);
        } else if (currentScreen == SCREEN_GAME_OVER) {
            Image img = new Image("file:gameover.jpg");
            ctx.drawImage(img, 0, 0, 800, 800);
        }
    }
}