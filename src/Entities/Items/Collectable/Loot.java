package Entities.Items.Collectable;

import DataTypes.Coords;

public class Loot extends Collectable {
    public Loot(Coords coords) {
        super(CollisionType.LOOT, false, coords);
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
