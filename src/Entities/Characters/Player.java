package Entities.Characters;

import DataTypes.CollisionType;

public class Player extends Character {
    public Player() {
        super(CollisionType.PLAYER, true);
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
