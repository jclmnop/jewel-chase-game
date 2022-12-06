package Entities.Items;

import DataTypes.Coords;
import Entities.Entity;

import java.util.ArrayList;

public abstract class Item extends Entity {
    public static final String RESOURCES_PATH = Entity.RESOURCES_PATH + "items/";
    public Item(CollisionType collisionType, boolean isBlocking, Coords coords) {
        super(collisionType, isBlocking, coords);
    }

    public static ArrayList<Item> getItems() {
        return Entity.getEntitiesOfType(Item.class);
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        // TODO
        return null;
    }
}
