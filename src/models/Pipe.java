package src.models;

import java.awt.Image;

/**
 * Represents a pipe obstacle in the Flappy Bird game.
 */
public class Pipe {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    private boolean passed;

    /**
     * Constructs a Pipe with specified position, dimensions, and image.
     *
     * @param x      the x-coordinate of the pipe
     * @param y      the y-coordinate of the pipe
     * @param width  the width of the pipe
     * @param height the height of the pipe
     * @param image  the image representing the pipe
     */
    public Pipe(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.passed = false;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}

