package Entities.Characters;

import DataTypes.Coords;
import DataTypes.Direction;

public class Player extends Character {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "liam_face.png";

    public Player(Coords coords, int speed) {
        super(CollisionType.PLAYER, true, coords, speed);
        this.currentDirection = Direction.RIGHT;
        this.imagePath = IMAGE_PATH;
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
