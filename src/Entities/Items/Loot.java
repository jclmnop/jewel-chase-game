package Entities.Items;

import DataTypes.CollisionType;

public class Loot extends Item {
    public Loot() {
        super(CollisionType.LOOT, false);
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
