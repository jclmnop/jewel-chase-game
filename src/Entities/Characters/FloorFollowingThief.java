package Entities.Characters;

import DataTypes.CollisionType;

public class FloorFollowingThief extends Character {
    public FloorFollowingThief() {
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
