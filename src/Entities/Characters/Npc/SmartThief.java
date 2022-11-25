package Entities.Characters.Npc;

import DataTypes.CollisionType;
import Entities.Characters.Character;

public class SmartThief extends Npc {
    public SmartThief() {
        super(CollisionType.THIEF, true);
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
