package org.techelevator.snakes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * A class to represent a snake
 */
public class Snake {
    /**
     * Up direction
     */
    public final static int DIRECTION_UP = 0;

    /**
     * Down direction
     */
    public final static int DIRECTION_DOWN = 1;

    /**
     * Left direction
     */
    public final static int DIRECTION_LEFT = 2;

    /**
     * Right direction
     */
    public final static int DIRECTION_RIGHT = 3;

    /**
     * The direction the snake is currently moving in
     */
    private int direction = DIRECTION_DOWN;

    /**
     * The segments of the snake
     */
    private final List<Point> segments = new ArrayList<>();

    /**
     * Create a new snake of length 2 at 0,1 and 0,0
     */
    public Snake() {
        segments.add(new Point(0, 2));
        segments.add(new Point(0, 1));
        segments.add(new Point(0, 0));
    }

    /**
     * Get the head of the snake (first segment)
     *
     * @return The head of the snake
     */
    public Point getHead() {
        return segments.get(0);
    }

    /**
     * Get the length of the snake
     *
     * @return The length of the snake
     */
    public int getLength() {
        return segments.size();
    }

    /**
     * Change the direction of the snake to up
     */
    public void setDirectionUp() {
        if (this.direction != DIRECTION_DOWN) {
            this.direction = DIRECTION_UP;
        }
    }

    /**
     * Change the direction of the snake to down
     */
    public void setDirectionDown() {
        if (this.direction != DIRECTION_UP) {
            this.direction = DIRECTION_DOWN;
        }
    }

    /**
     * Change the direction of the snake to left
     */
    public void setDirectionLeft() {
        if (this.direction != DIRECTION_RIGHT) {
            this.direction = DIRECTION_LEFT;
        }
    }

    /**
     * Change the direction of the snake to right
     */
    public void setDirectionRight() {
        if (this.direction != DIRECTION_LEFT) {
            this.direction = DIRECTION_RIGHT;
        }
    }

    /**
     * First move all the segments to the position of the segment in front of it,
     * then move the head in the current direction
     *
     * @param shouldGrow Whether the snake should grow after moving
     */
    public void move(boolean shouldGrow) {
        Point tail = segments.get(segments.size() - 1);
        Point newSegment = new Point(tail);

        for (int i = segments.size() - 1; i > 0; i--) {
            segments.get(i).copyCoordinates(segments.get(i - 1));
        }

        if (direction == DIRECTION_UP) {
            getHead().move(0, -1);
        } else if (direction == DIRECTION_DOWN) {
            getHead().move(0, 1);
        } else if (direction == DIRECTION_LEFT) {
            getHead().move(-1, 0);
        } else if (direction == DIRECTION_RIGHT) {
            getHead().move(1, 0);
        }

        if (shouldGrow) {
            segments.add(newSegment);
        }
    }

    /**
     * Check if the snake is out of bounds
     *
     * @return Whether the snake is out of bounds
     */
    private boolean isOutOfBounds() {
        return getHead().getX() < 0 || getHead().getX() > 31 || getHead().getY() < 0 || getHead().getY() > 31;
    }

    /**
     * Check if the snake is self-colliding
     *
     * @return Whether the snake is self-colliding
     */
    private boolean isSelfColliding() {
        for (int i = 1; i < segments.size(); i++) {
            if (getHead().equals(segments.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the snake is dead
     *
     * @return Whether the snake is dead
     */
    public boolean isDead() {
        return isOutOfBounds() || isSelfColliding();
    }

    /**
     * Draws the snake to a graphics context
     *
     * @param ctx Graphics context to draw to
     * @param currentFrame The current frame number
     */
    public void draw(GraphicsContext ctx, long currentFrame) {
        ctx.setFill(Color.GREEN);
        for (Point segment : segments) {
            segment.draw(ctx, currentFrame);
        }
        ctx.setFill(Color.DARKGREEN);
        getHead().draw(ctx, currentFrame);
    }
}
