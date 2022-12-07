package Entities.Characters;

import DataTypes.AdjacentCoords;
import DataTypes.Coords;
import DataTypes.Direction;
import Game.Game;
import Game.Tile;

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

    public Player(Coords coords, int speed, Direction currentDirection, int ticksSinceLastMove) {
        this(coords, speed, currentDirection);
        this.ticksSinceLastMove = ticksSinceLastMove;
    }

    public void tryMove(Direction direction) {
        AdjacentCoords adjacentCoords = Tile.getMultiColourAdjacentTiles(this.coords);
        Coords to = adjacentCoords.getCoordsInDirection(direction);
        if (to != null && !Tile.isBlockedCoords(to)) {
            this.move(to);
        }
    }
    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.speed,
            this.currentDirection,
            this.ticksSinceLastMove
        );
    }
}
