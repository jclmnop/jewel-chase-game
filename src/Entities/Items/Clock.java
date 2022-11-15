package Entities.Items;

import DataTypes.CollisionType;

public class Clock extends Item {
    public Clock() {
        super(CollisionType.CLOCK, false);
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
