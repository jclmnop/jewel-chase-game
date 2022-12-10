package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

/**
 * Coffee makes liam and other lecturers go faster
 * @author Jonny
 * @version 1.1
 */
public class Coffee extends Collectable {
    public static final int POINTS_IF_MAX_SPEED_REACHED = 10;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "coffee.gif";

    public Coffee(Coords coords) {
        super(CollisionType.COFFEE, false, coords);
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public String serialise() {
        return String.format(
            "%s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise()
        );
    }

}
