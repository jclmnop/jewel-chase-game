package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

/**
 * Adds time to the game timer if collected by player, decreases time if collected
 * by a thief.
 *
 * @author Matt
 * @version 1.0
 * @see Entities.Items.Collectable.Collectable
 */
public class Clock extends Collectable {
    /** Number of seconds that will be added/subtracted from remaining time. */
    public static final int SECONDS = 10;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "clock.gif";

    /**
     * Construct a clock at the specified coordinates.
     * @param coords Coordinates of clock.
     */
    public Clock(Coords coords) {
        super(CollisionType.CLOCK, false, coords);
        this.imagePath = IMAGE_PATH;
    }
}
