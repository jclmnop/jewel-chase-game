package Entities.Items.Collectable;

import DataTypes.CollisionType;
import Entities.Items.Item;

public abstract class Collectable extends Item {
    public Collectable(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
    }
}
