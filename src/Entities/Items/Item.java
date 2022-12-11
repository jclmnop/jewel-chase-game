package Entities.Items;

import DataTypes.Coords;
import Entities.Entity;

import java.util.ArrayList;

/**
 * Represents an item (collectable or otherwise).
 *
 * @author Jonny
 * @version 1.0
 * @see Interfaces.Renderable
 * @see Interfaces.Serialisable
 * @see Entities.Entity
 */
public abstract class Item extends Entity {
    /** Path to image files for items. */
    public static final String RESOURCES_PATH = Entity.RESOURCES_PATH + "items/";

    /**
     * Construct item with given parameters.
     * @param collisionType Collision type to be used in collisions.
     * @param blocking Whether this item blocks other entities from occupying
     *                 the same tile.
     * @param coords Coordinates of item.
     */
    public Item(CollisionType collisionType, boolean blocking, Coords coords) {
        super(collisionType, blocking, coords);
    }

    /**
     * @return All items on the board.
     */
    public static ArrayList<Item> getItems() {
        return Entity.getEntitiesOfType(Item.class);
    }
}
