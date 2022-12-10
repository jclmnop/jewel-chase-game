package Entities.Characters.Npc;


import DataTypes.AdjacentCoords;
import DataTypes.Colour;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Characters.Character;
import Game.Tile;
import Interfaces.Handleable;

public class FloorFollowingThief extends Npc implements Handleable {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "stuart_face.png";
    private final Colour colour;

    public FloorFollowingThief(Coords coords, int ticksPerMove, Colour colour, Direction direction) {
        super(CollisionType.THIEF, true, coords, ticksPerMove);
        this.colour = colour;
        this.currentDirection = direction;
        this.imagePath = IMAGE_PATH;
    }

    public FloorFollowingThief(Coords coords, int ticksPerMove) {
        this(coords, ticksPerMove, Colour.BLUE, Direction.UP);
    }
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
     * Trys to move to tile with assigned colour that is left most to FFT's current direction.
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

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        // TODO
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
}
