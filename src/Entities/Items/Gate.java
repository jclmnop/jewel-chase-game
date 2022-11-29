package Entities.Items;

import DataTypes.CollisionType;
import DataTypes.Coords;

public class Gate extends Item {
    public Gate(Coords coords) {
        super(CollisionType.GATE, true, coords);
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
