package Entities.Items.Collectable;

import DataTypes.CollisionType;
import DataTypes.Coords;
import Entities.Items.Item;

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
