package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Entity;
import Entities.Items.Item;

import java.util.ArrayList;

public abstract class Collectable extends Item {
    public Collectable(CollisionType collisionType, boolean isBlocking, Coords coords) {
        super(collisionType, isBlocking, coords);
    }

    public static ArrayList<Collectable> getCollectables() {
        return Entity.getEntitiesOfType(Collectable.class);
    }
}
