package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

/**
 * Coffee makes liam and other lecturers go faster. Provides extra points
 * to player if max speed is already reached.
 *
 * @author Jonny
 * @version 1.1
 * @see Entities.Items.Collectable.Collectable
 */
public class Coffee extends Collectable {
    public static final int POINTS_IF_MAX_SPEED_REACHED = 10;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "coffee.gif";

    /**
     * Spawn coffee item at specified coordinates.
     * @param coords Coordinates of coffee.
     */
    public Coffee(Coords coords) {
        super(CollisionType.COFFEE, false, coords);
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
