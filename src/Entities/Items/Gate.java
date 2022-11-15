package Entities.Items;

import DataTypes.CollisionType;

public class Gate extends Item {
    public Gate() {
        super(CollisionType.GATE, true);
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
