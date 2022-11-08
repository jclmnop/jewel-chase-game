package Entities.Items;

import DataTypes.CollisionType;
import Entities.Entity;

public abstract class Item extends Entity {
    public Item(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
    }
}
