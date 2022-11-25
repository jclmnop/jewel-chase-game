package Entities.Items.Collectable;

import DataTypes.CollisionType;
import Entities.Items.Item;

public class Lever extends Item {
    public Lever() {
        super(CollisionType.LEVER, false);
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
