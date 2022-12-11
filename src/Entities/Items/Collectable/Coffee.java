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
    /** Number of points granted if collected when already at max speed */
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
}
