package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

public class Mushroom extends Collectable {
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
