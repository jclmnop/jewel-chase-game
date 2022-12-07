package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

/**
 * Duplicates the entity when collected.
 * @author Jonny
 * @version 1.0
 */
public class Star extends Collectable {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "star.png";

    public Star(Coords coords) {
        super(CollisionType.STAR, false, coords);
        this.imagePath = IMAGE_PATH;
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise()
        );
    }
}
