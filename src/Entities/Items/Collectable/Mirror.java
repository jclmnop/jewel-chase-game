package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

/**
 * Duplicates the entity when collected. If player collects this item when max
 * number of players is already on the board, then it provides extra points
 * instead.
 * @author Jonny
 * @version 1.0
 */
public class Mirror extends Collectable {
    public static final int POINTS_IF_MAX_PLAYERS_REACHED = 10;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "mirror.png";

    public Mirror(Coords coords) {
        super(CollisionType.MIRROR, false, coords);
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
