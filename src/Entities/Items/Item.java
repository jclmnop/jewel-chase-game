package Entities.Items;

import DataTypes.CollisionType;
import Entities.Entity;

import java.util.ArrayList;

public abstract class Item extends Entity {
    public Item(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
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
