package Entities.Characters.Npc;


import DataTypes.AdjacentCoords;
import DataTypes.CollisionType;
import DataTypes.Colour;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Characters.Character;
import Game.Tile;

public class FloorFollowingThief extends Npc {

    private final Colour colour;

    public FloorFollowingThief(Coords coords, int speed, Colour colour, Direction direction) {
        super(CollisionType.THIEF, true, coords, speed);
        this.colour = colour;
        this.currentDirection = direction;
    }

    public FloorFollowingThief(Coords coords, int speed) {
        this(coords, speed, Colour.BLUE, Direction.UP);
    }

    /**
     * Trys to move to tile with assigned colour that is left most to FFT's current direction.
     */
    @Override
    public void tryMove() {
        AdjacentCoords adjCoords = Tile.getSingleColourAdjacentTiles(coords, colour);
        Direction dir = Direction.turnLeft(currentDirection);

        /* Checks all directions, starting left of FFT and going clockwise.
         * If there are no tiles to move to, FFT stays where it is.
        */
        boolean foundTile = false;
        int i = 0;
        while (!foundTile || i < 4) {
            if (adjCoords.getTileInDirection(dir) != null) {
                coords = adjCoords.getCoordsInDirection(dir);
                currentDirection = dir;
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
        return null;
    }

}
