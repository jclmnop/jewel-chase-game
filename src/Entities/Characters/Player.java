package Entities.Characters;

import DataTypes.AdjacentCoords;
import DataTypes.Coords;
import DataTypes.Direction;
import Game.Game;
import Game.Tile;

/**
 * Represents the Player. Game will end with a loss if there are no Player
 * objects in the game.
 * @author Jonny
 * @version 1.1
 */
public class Player extends Character {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "liam_stick.gif";

    /**
     * Construct a player with the given coords and ticksPerMove. Direction
     * defaults to RIGHT.
     * @param coords Coordinates of player.
     * @param ticksPerMove How many ticks need to pass before each movement.
     */
    public Player(Coords coords, int ticksPerMove) {
        super(CollisionType.PLAYER, true, coords, ticksPerMove);
        this.currentDirection = Direction.RIGHT;
        this.imagePath = IMAGE_PATH;
        Game.addPlayer(this);
    }

    /**
     * Construct a player with the given coords, ticksPerMove and direction.
     * @param coords Coordinates of player.
     * @param ticksPerMove How many ticks need to pass before each movement.
     * @param currentDirection Direction that the player will be facing.
     */
    public Player(Coords coords, int ticksPerMove, Direction currentDirection) {
        super(CollisionType.PLAYER, true, coords, ticksPerMove);
        this.currentDirection = currentDirection;
        this.imagePath = IMAGE_PATH;
        Game.addPlayer(this);
    }

    /**
     * Construct a player with the given coords, ticksPerMove, direction and
     * ticksSinceLastMove. Used when deserialising from a save file. .
     * @param coords Coordinates of player.
     * @param ticksPerMove How many ticks need to pass before each movement.
     * @param currentDirection Direction that the player will be facing.
     * @param ticksSinceLastMove Number of ticks that have passed since the
     *                           player last moved.
     */
    public Player(Coords coords, int ticksPerMove, Direction currentDirection, int ticksSinceLastMove) {
        this(coords, ticksPerMove, currentDirection);
        this.ticksSinceLastMove = ticksSinceLastMove;
    }

    /**
     * Attempt to move in the given direction.
     * @param direction Direction to attempt movement in.
     */
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
            this.ticksPerMove,
            this.currentDirection,
            this.ticksSinceLastMove
        );
    }
}
