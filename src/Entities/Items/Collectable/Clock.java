package Entities.Items.Collectable;

import DataTypes.Coords;

public class Clock extends Collectable {
    public Clock(Coords coords) {
        super(CollisionType.CLOCK, false, coords);
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
