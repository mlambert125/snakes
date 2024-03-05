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

public class SnakesApplication extends Application {
    // ----------------------------------------------
    // CONSTANTS
    // ----------------------------------------------
    private final static int FRAMES_PER_SECOND = 30;
    private final static int DIRECTION_UP = 0;
    private final static int DIRECTION_DOWN = 1;
    private final static int DIRECTION_LEFT = 2;
    private final static int DIRECTION_RIGHT = 3;

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
    private List<Point> snake = new ArrayList<>();
    private int direction = 1;

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
        snake.add(new Point(0, 9));
        snake.add(new Point(0, 8));
        snake.add(new Point(0, 7));
        snake.add(new Point(0, 6));
        snake.add(new Point(0, 5));
        snake.add(new Point(0, 4));
        snake.add(new Point(0, 3));
        snake.add(new Point(0, 2));
        snake.add(new Point(0, 1));
        snake.add(new Point(0, 0));
    }

    /**
     * Update the game state
     *
     * @param currentFrame the current frame number
     */
    public void update(long currentFrame) {
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

        // Update snake position
        if (currentFrame % 5 == 0) {
            // Move all the snake parts besides the head to the position of the part in front of it
            // Starting at the tail and going forward
            for (int i = snake.size() - 1; i > 0; i--) {
                snake.get(i).setX(snake.get(i - 1).getX());
                snake.get(i).setY(snake.get(i - 1).getY());
            }
            if (direction == DIRECTION_DOWN && snakeHead.getY() < 31) {
                snakeHead.move(0, 1);
            } else if (direction == DIRECTION_UP && snakeHead.getY() > 0) {
                snakeHead.move(0, -1);
            } else if (direction == DIRECTION_LEFT && snakeHead.getX() > 0) {
                snakeHead.move(-1, 0);
            } else if (direction == DIRECTION_RIGHT && snakeHead.getX() < 31) {
                snakeHead.move(1, 0);
            }
        }
    }

    /**
     * Draw the game frame
     *
     * @param currentFrame the current frame number
     */
    public void draw(long currentFrame) {
        ctx.clearRect(0, 0, 800, 800);

        ctx.setFill(Color.GREEN);
        for (Point p : snake) {
            ctx.fillRect(p.getX() * 25, p.getY() * 25, 25, 25);
        }

        ctx.setFill(Color.RED);
        ctx.fillRect(snake.get(0).getX() * 25, snake.get(0).getY() * 25, 25, 25);
    }
}