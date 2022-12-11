package Interfaces;

import javafx.scene.image.Image;

/**
 * Interface for anything that can be rendered, such as tile colours, characters
 * and entities.
 *
 * @author Jonny
 * @version 1.0
 * @see Entities.Entity
 */
public interface Renderable {
    /** Path to base resources directory */
    String RESOURCES_PATH = "App/resources/";

    /**
     * Returns a loaded image to be rendered by JavaFx
     * @return the loaded image.
     */
    Image toImage();
}
