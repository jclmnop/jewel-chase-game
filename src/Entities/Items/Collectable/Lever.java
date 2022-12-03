package Entities.Items.Collectable;

import DataTypes.Coords;

public class Lever extends Collectable {
    public Lever(Coords coords) {
        super(CollisionType.LEVER, false, coords);
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
