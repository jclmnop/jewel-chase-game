package Entities.Characters;

import DataTypes.CollisionType;

public class FlyingAssassin extends Character {
    public FlyingAssassin() {
        super(CollisionType.ASSASSIN, false);
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
