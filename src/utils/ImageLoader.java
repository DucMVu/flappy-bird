package src.utils;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Utility class for loading game images.
 */
public class ImageLoader {
    /**
     * Loads an image from the resources folder.
     *
     * @param resourcePath the path to the image resource
     * @return the loaded Image
     */
    public static Image loadImage(String resourcePath) {
        ImageIcon icon = new ImageIcon(ImageLoader.class.getResource("/" + resourcePath));
        return icon.getImage();
    }
}

