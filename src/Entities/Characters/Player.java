package Entities.Characters;

import DataTypes.Coords;
import DataTypes.Direction;
import Game.Game;

public class Player extends Character {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "liam_face.png";

    public Player(Coords coords, int speed) {
        super(CollisionType.PLAYER, true, coords, speed);
        this.currentDirection = Direction.RIGHT;
        this.imagePath = IMAGE_PATH;
        Game.addPlayer(this);
    }

    public Player(Coords coords, int speed, Direction currentDirection) {
        super(CollisionType.PLAYER, true, coords, speed);
        this.currentDirection = currentDirection;
        this.imagePath = IMAGE_PATH;
        Game.addPlayer(this);
    }

    public void tryMove(Direction direction) {
        //TODO: implement
        System.out.println("Player move: " + direction);
    }
    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.speed,
            this.currentDirection
        );
    }
}
