package src.utils;

import src.models.Bird;
import src.models.Pipe;

/**
 * Utility class for collision detection.
 */
public class CollisionDetector {
    /**
     * Detects collision between a bird and a pipe using AABB (Axis-Aligned Bounding Box) collision.
     *
     * @param bird the bird object
     * @param pipe the pipe object
     * @return true if collision detected, false otherwise
     */
    public static boolean detectCollision(Bird bird, Pipe pipe) {
        // AABB collision detection
        return bird.getX() < pipe.getX() + pipe.getWidth() &&           // Bird's left edge is left of pipe's right edge
               bird.getX() + bird.getWidth() > pipe.getX() &&           // Bird's right edge is right of pipe's left edge
               bird.getY() < pipe.getY() + pipe.getHeight() &&          // Bird's top edge is above pipe's bottom edge
               bird.getY() + bird.getHeight() > pipe.getY();            // Bird's bottom edge is below pipe's top edge
    }
}

