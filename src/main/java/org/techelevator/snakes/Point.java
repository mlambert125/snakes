package org.techelevator.snakes;

/**
 * A simple class to represent a point in 2D space
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
     * Create a new point at the origin (0, 0)
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new point with the same coordinates as another point
     *
     * @param other Other point to copy
     */
    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Get the x coordinate of the point
     *
     * @return the x coordinate of the point
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x coordinate of the point
     *
     * @param x the new x coordinate of the point
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y coordinate of the point
     *
     * @return the y coordinate of the point
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y coordinate of the point
     *
     * @param y the new y coordinate of the point
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Move the point by a given amount
     *
     * @param dx the amount to move in the x direction
     * @param dy the amount to move in the y direction
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Check if this point is equal to another point
     *
     * @param other the other point to compare to
     * @return true if the points have the same coordinates, false otherwise
     */
    public boolean equals(Point other) {
        return x == other.x && y == other.y;
    }
}
