package org.techelevator.snakes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class to represent a 2D point
 */
public class Point {
    /**
     * The x coordinate of the point
     */
    private int x;

    /**
     * The y coordinate of the point
     */
    private int y;

    /**
     * Create a new point at the specified coordinates
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new point at the same coordinates as another point
     *
     * @param other The point to copy the coordinates from
     */
    public Point(Point other) {
       this.x = other.x;
       this.y = other.y;
    }

    /**
     * Get the x coordinate of the point
     *
     * @return The x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Set the x coordinate of the point
     *
     * @param x The new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y coordinate of the point
     *
     * @return The y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set the y coordinate of the point
     *
     * @param y The new y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Check if this point is equal to another point by comparing their x and y coordinates
     *
     * @param other The other point to compare to
     * @return Whether the two points are equal (have the same x and y coordinates)
     */
    public boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Move the point by the specified amount in the x and y directions
     *
     * @param dx The amount to move in the x direction
     *           (positive values move right, negative values move left)
     * @param dy The amount to move in the y direction
     *           (positive values move down, negative values move up)
     */
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Copy the coordinates of another point to this point
     *
     * @param other The point to copy the coordinates from
     */
    public void copyCoordinates(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Draw the point to the specified graphics context
     *
     * @param ctx The graphics context to draw to
     */
    public void draw(GraphicsContext ctx, long currentFrame) {
        ctx.fillRect(x * 25, y * 25, 25, 25);
    }
}
