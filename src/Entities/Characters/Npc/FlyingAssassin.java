package Entities.Characters.Npc;

import DataTypes.CollisionType;
import Entities.Characters.Character;

public class FlyingAssassin extends Npc {
    public FlyingAssassin() {
        super(CollisionType.ASSASSIN, false);
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
