package Entities.Items;

import DataTypes.CollisionType;

public class Door extends Item{
    public Door() {
        super(CollisionType.DOOR, false);
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
