package Entities.Characters.Npc;


import DataTypes.AdjacentCoords;
import DataTypes.Colour;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Characters.Character;
import Game.Tile;

/**
 * Thief which stays on tiles of a certain colour and follows the left-most
 * boundary in a circular path.
 *
 * @author Dillon
 * @version 1.2
 * @see Entities.Characters.Npc.Npc
 */
public class FloorFollowingThief extends Npc {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "oliver_snail.png";
    private final Colour colour;

    /**
     * Construct a floor following thief with given parameters.
     * @param coords Coordinates to spawn the thief on.
     * @param ticksPerMove How many ticks the thief must wait between moves.
     * @param colour The colour of tile this thief will stick to.
     * @param direction The initial direction this thief will face.
     */
    public FloorFollowingThief(Coords coords, int ticksPerMove, Colour colour, Direction direction) {
        super(CollisionType.THIEF, true, coords, ticksPerMove);
        this.colour = colour;
        this.currentDirection = direction;
        this.imagePath = IMAGE_PATH;
    }

    /**
     * Construct a floor following thief with given parameters. Defaults to
     * colour BLUE facing UP.
     * @param coords Coordinates to spawn the thief on.
     * @param ticksPerMove How many ticks the thief must wait between moves.
     */
    public FloorFollowingThief(Coords coords, int ticksPerMove) {
        this(coords, ticksPerMove, Colour.BLUE, Direction.UP);
    }

    /**
     * Construct a floor following thief with given parameters.
     *
     * Used to deserialise a floor following thief from a save file string.
     *
     * @param coords Coordinates to spawn the thief on.
     * @param ticksPerMove How many ticks the thief must wait between moves.
     * @param colour The colour of tile this thief will stick to.
     * @param direction The initial direction this thief will face.
     * @param ticksSinceLastMove Number of ticks which have passed since last
     *                           move.
     */
    public FloorFollowingThief(
        Coords coords,
        int ticksPerMove,
        Colour colour,
        Direction direction,
        int ticksSinceLastMove
    ) {
        this(coords, ticksPerMove, colour, direction);
        this.ticksSinceLastMove = ticksSinceLastMove;
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s %s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.ticksPerMove,
            this.colour,
            this.currentDirection,
            this.ticksSinceLastMove
        );
    }

    /**
     * Trys to move to tile with assigned colour that is left most to its
     * current direction.
     */
    @Override
    protected void tryMove() {
        AdjacentCoords adjCoords = Tile.getSingleColourAdjacentTiles(coords, colour);
        Direction dir = Direction.turnLeft(currentDirection);
        final int MAXIMUM_ATTEMPTS = 4;

        /* Checks all directions, starting left of FFT and going clockwise.
         * If there are no tiles to move to, FFT stays where it is.
        */
        boolean foundTile = false;
        int i = 0;
        while (!foundTile && i < MAXIMUM_ATTEMPTS) {
            Coords nextCoords = adjCoords.getCoordsInDirection(dir);
            if (nextCoords != null && !Tile.isBlockedCoords(nextCoords)) {
                this.move(nextCoords);
                foundTile = true;
            } else {
                dir = Direction.turnRight(dir);
                i++;
            }
        }
    }
}
