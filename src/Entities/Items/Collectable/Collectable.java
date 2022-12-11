package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Entity;
import Entities.Items.Item;

import java.util.ArrayList;

/**
 * Represents an item that can be collected by the player or a thief.
 *
 * @author Jonny
 * @version 1.0
 * @see Entities.Items.Item
 * @see Interfaces.Serialisable
 * @see Interfaces.Renderable
 */
public abstract class Collectable extends Item {

    /**
     * Create collectable item with given parameters.
     * @param collisionType Collision type used to decide outcome of collisions
     *                      between this item and other entities.
     * @param blocking Whether this item blocks other entities from occupying
     *                 the same tile.
     * @param coords Coordinates of this item.
     */
    public Collectable(CollisionType collisionType, boolean blocking, Coords coords) {
        super(collisionType, blocking, coords);
    }

    /**
     * @return All collectable items.
     * @see Entities.Items.Door
     */
    public static ArrayList<Collectable> getCollectables() {
        return Entity.getEntitiesOfType(Collectable.class);
    }
}
