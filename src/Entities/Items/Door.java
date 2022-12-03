package Entities.Items;

import DataTypes.Coords;

public class Door extends Item{
    public Door(Coords coords) {
        super(CollisionType.DOOR, false, coords);
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
