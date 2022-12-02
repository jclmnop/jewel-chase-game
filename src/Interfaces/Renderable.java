package Interfaces;

import javafx.scene.image.Image;

/**
 * Interface for anything that can be rendered, such as tile colours, characters
 * and entities.
 */
public interface Renderable {
    public static final String RESOURCES_PATH = "App/resources/";
    /**
     * Returns a loaded image to be rendered by JavaFx
     * @return the loaded image.
     */
    Image toImage();
}
