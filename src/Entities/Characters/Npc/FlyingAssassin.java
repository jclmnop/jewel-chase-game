package Entities.Characters.Npc;

import DataTypes.Coords;

public class FlyingAssassin extends Npc {
    public FlyingAssassin(Coords coords, int speed) {
        super(CollisionType.ASSASSIN, false, coords, speed);
    }

    @Override
    public void tryMove() {
        //TODO: implement
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
