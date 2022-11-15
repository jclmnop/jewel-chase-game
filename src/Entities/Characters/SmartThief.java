package Entities.Characters;

import DataTypes.CollisionType;

public class SmartThief extends Character {
    public SmartThief() {
        super(CollisionType.THIEF, true);
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
