package org.techelevator.snakes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * A class to represent an apple
 */
public class Apple extends Point {
    /**
     * Create a new apple at the specified coordinates
     */
    public Apple() {
        super(0, 0);
        this.moveToRandomLocation();
    }

    /**
     * Move the apple to a random location
     */
    public void moveToRandomLocation() {
        Random random = new Random();
        setX(random.nextInt(32));
        setY(random.nextInt(32));
    }

    /**
     * Draw the apple
     *
     * @param ctx The graphics context to draw to
     * @param currentFrame The current frame
     */
    @Override
    public void draw(GraphicsContext ctx, long currentFrame) {
        ctx.setFill(Color.RED);
        ctx.fillOval(getX() * 25, getY() * 25, 25, 25);
    }
}
