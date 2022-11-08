package Entities.Items;

import DataTypes.CollisionType;

public class Gate extends Item {
    public Gate() {
        super(CollisionType.GATE, true);
    }
}
