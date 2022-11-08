package Entities.Items;

import DataTypes.CollisionType;

public class Loot extends Item {
    public Loot() {
        super(CollisionType.LOOT, false);
    }
}
