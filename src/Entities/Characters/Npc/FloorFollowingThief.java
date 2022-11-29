package Entities.Characters.Npc;

import DataTypes.CollisionType;
import DataTypes.Coords;
import Entities.Characters.Character;

public class FloorFollowingThief extends Npc {
    public FloorFollowingThief(Coords coords, int speed) {
        super(CollisionType.THIEF, true, coords, speed);
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
