package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

public class Mushroom extends Collectable {
    public static final int POINTS_IF_MAX_SPEED_REACHED = 10;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "mushroom.png";

    public Mushroom(Coords coords) {
        super(CollisionType.MUSHROOM, false, coords);
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
