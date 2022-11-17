package Entities.Items;

import DataTypes.CollisionType;

public class Bomb extends Item {
    public Bomb() {
        super(CollisionType.BOMB, false);
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
