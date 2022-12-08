package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Entity;
import Entities.Items.Item;

import java.util.ArrayList;

//TODO mushroom (can take one more hit before dying)
//TODO star (cloning item)
public abstract class Collectable extends Item {
    public Collectable(CollisionType collisionType, boolean isBlocking, Coords coords) {
        super(collisionType, isBlocking, coords);
    }

    public static ArrayList<Collectable> getCollectables() {
        return Entity.getEntitiesOfType(Collectable.class);
    }
}
