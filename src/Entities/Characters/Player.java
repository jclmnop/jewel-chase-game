package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;

public class Player extends Character {
    public Player(Coords coords, int speed) {
        super(CollisionType.PLAYER, true, coords, speed);
    }

    public void tryMove(Direction direction) {
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
