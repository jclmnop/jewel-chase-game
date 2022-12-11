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
    public static final int SECONDS = 10;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "clock.gif";

    /**
     * Construct a clock at the specified coordinates.
     * @param coords
     */
    public Clock(Coords coords) {
        super(CollisionType.CLOCK, false, coords);
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
