package org.techelevator.snakes;

import java.util.*;
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

/**
 * A simple class to represent a point in 2D space
 */
public class SnakesApplication extends Application {
    // ----------------------------------------------
    // CONSTANTS
    // ----------------------------------------------
    /**
     * The number of frames per second
     */
    private final static int FRAMES_PER_SECOND = 30;

    /**
     * UP direction
     */
    private final static int DIRECTION_UP = 0;

    /**
     * DOWN direction
     */
    private final static int DIRECTION_DOWN = 1;

    /**
     * LEFT direction
     */
    private final static int DIRECTION_LEFT = 2;

    /**
     * RIGHT direction
     */
    private final static int DIRECTION_RIGHT = 3;

    /**
     * Title screen
     */
    private final static int SCREEN_TITLE = 0;

    /**
     * Game screen
     */
    private final static int SCREEN_GAME = 1;

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
     * The current screen
     */
    private int screen = SCREEN_TITLE;
    /**
     * The snake (a list of points)
     */
    private List<Point> snake = new ArrayList<>();
    /**
     * The current direction of the snake
     */
    private int direction = DIRECTION_DOWN;
    /**
     * The apple (a single point)
     */
    private Point apple = new Point(5, 5);

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
        initialize();
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
     * Initialize the game state
     */
    public void initialize(){
        snake.clear();
        snake.add(new Point(0, 0));

        direction= DIRECTION_DOWN;
        placeApple();
    }

    /**
     * Update the game state
     *
     * @param currentFrame the current frame number
     */
    public void update(long currentFrame) {
        if (screen == SCREEN_GAME) {
            Point snakeHead = snake.get(0);

            // Update direction if needed
            if (keydown_up && direction != DIRECTION_DOWN) {
                direction = DIRECTION_UP;
            } else if (keydown_down && direction != DIRECTION_UP) {
                direction = DIRECTION_DOWN;
            } else if (keydown_left && direction != DIRECTION_RIGHT) {
                direction = DIRECTION_LEFT;
            } else if (keydown_right && direction != DIRECTION_LEFT) {
                direction = DIRECTION_RIGHT;
            }

            // Update snake positions
            if (currentFrame % 2 == 0) {
                Point tail = snake.get(snake.size() - 1);
                Point newSegment = new Point(tail);

                // Move all the snake parts besides the head to the position of the part in front of it
                // Starting at the tail and going forward
                for (int i = snake.size() - 1; i > 0; i--) {
                    snake.get(i).setX(snake.get(i - 1).getX());
                    snake.get(i).setY(snake.get(i - 1).getY());
                }
                if (direction == DIRECTION_DOWN) {
                    snakeHead.move(0, 1);
                } else if (direction == DIRECTION_UP) {
                    snakeHead.move(0, -1);
                } else if (direction == DIRECTION_LEFT) {
                    snakeHead.move(-1, 0);
                } else if (direction == DIRECTION_RIGHT) {
                    snakeHead.move(1, 0);
                }

                // Check for collision with edges
                if (snakeHead.getX() < 0 || snakeHead.getX() > 31 || snakeHead.getY() < 0 || snakeHead.getY() > 31) {
                    screen = SCREEN_TITLE;
                }

                // Check for collision with self
                for (int i = 1; i < snake.size(); i++) {
                    if (snakeHead.equals(snake.get(i))) {
                        screen = SCREEN_TITLE;
                    }
                }

                // Check for collision with apple
                if (snakeHead.equals(apple)) {
                    placeApple();
                    snake.add(newSegment);
                }
            }
        } else if (screen == SCREEN_TITLE) {
            if (keydown_space) {
                initialize();
                screen = SCREEN_GAME;
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

        if (screen == SCREEN_GAME) {
            // Draw apple
            ctx.setFill(Color.RED);
            ctx.fillOval(apple.getX() * 25, apple.getY() * 25, 25, 25);

            // Draw snake
            ctx.setFill(Color.GREEN);
            for (Point p : snake) {
                ctx.fillRect(p.getX() * 25, p.getY() * 25, 25, 25);
            }

            // Draw snake head (again)
            ctx.setFill(Color.DARKGREEN);
            ctx.fillRect(snake.get(0).getX() * 25, snake.get(0).getY() * 25, 25, 25);

            ctx.setFill(Color.WHITE);
            ctx.fillText("Score: " + (snake.size()), 700, 12);
        } else if (screen == SCREEN_TITLE) {
            ctx.setFill(Color.GREEN);
            ctx.fillText("SNAKE GAME", 300, 200);
            ctx.setFill(Color.WHITE);
            ctx.fillText("Press SPACE to Start", 300, 400);
        }
    }

    /**
     * Place the apple at a random location
     */
    private void placeApple()  {
        Random rand = new Random();
        apple = new Point(rand.nextInt(30) + 1, rand.nextInt(30) + 1);
    }
}