package Entities.Items;

import DataTypes.Coords;

/**
 * If player/thief touches this door once all collectable items have been picked
 * up, victory/loss is triggered.
 *
 * @author Will
 * @version 1.0
 */
public class Door extends Item{
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "door.png";

    /**
     * Construct a door at the given coordinates.
     * @param coords Coordinates of door.
     */
    public Door(Coords coords) {
        super(CollisionType.DOOR, false, coords);
        this.imagePath = IMAGE_PATH;
    }
}
