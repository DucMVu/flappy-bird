package src.models;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Represents a bird in the Flappy Bird game.
 */
public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;

    /**
     * Constructs a Bird with specified position, dimensions, and image.
     *
     * @param x      the x-coordinate of the bird
     * @param y      the y-coordinate of the bird
     * @param width  the width of the bird
     * @param height the height of the bird
     * @param image  the image representing the bird
     */
    public Bird(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
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
}

