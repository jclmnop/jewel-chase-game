package Entities.Items;

import DataTypes.Coords;

public class Bomb extends Item {
    public Bomb(Coords coords) {
        super(CollisionType.BOMB, false, coords);
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
