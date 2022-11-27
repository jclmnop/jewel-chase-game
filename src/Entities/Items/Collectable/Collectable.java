package Entities.Items.Collectable;

import DataTypes.CollisionType;
import Entities.Entity;
import Entities.Items.Item;

import java.util.ArrayList;

public abstract class Collectable extends Item {
    public Collectable(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
    }

    public static ArrayList<Collectable> getCollectables() {
        return Entity.getEntitiesOfType(Collectable.class);
    }
}
