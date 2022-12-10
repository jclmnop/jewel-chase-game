package Entities.Characters.Npc;

import DataTypes.Coords;
import Entities.Characters.Character;

public class FlyingAssassin extends Npc {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "stuart_bat.gif";

    public FlyingAssassin(Coords coords, int speed) {
        super(CollisionType.ASSASSIN, false, coords, speed);
        this.imagePath = IMAGE_PATH;
    }

    @Override
    protected void tryMove() {
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
