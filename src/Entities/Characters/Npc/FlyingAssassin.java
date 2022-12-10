package Entities.Characters.Npc;

import DataTypes.AdjacentCoords;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Characters.Character;
import Game.Tile;

/**
 * Flies around the map and kills anything it touches. For some reason it
 * looks a lot like Stewart Powell.
 *
 * @author Aleksandra
 * @version 1.1
 */
public class FlyingAssassin extends Npc {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "stuart_bat.gif";

    public FlyingAssassin(Coords coords, int speed, Direction dir) {
        super(CollisionType.ASSASSIN, false, coords, speed);
        this.currentDirection = dir;
        this.imagePath = IMAGE_PATH;
    }

    public FlyingAssassin(Coords coords, int speed, Direction dir, int ticksSinceLastMove) {
        this(coords, speed, dir);
        this.ticksSinceLastMove = ticksSinceLastMove;
    }

    public FlyingAssassin(Coords coords, int speed) {
        this(coords, speed, Direction.UP);
    }

    /**
     * Attempts to move to the next tile in the direction it's facing.
     * If it reaches an edge, FlyingAssassin will be rotated 180 degrees
     */
    @Override
    public void tryMove() {
        if (this.edgeReached()) {
            this.currentDirection = Direction.turnAround(this.currentDirection);
        }
        Coords nextCoords =
            Tile.getAdjacentCoords(this.coords).getCoordsInDirection(
                this.currentDirection
            );

        this.move(nextCoords);
    }

    /**
     * Checks whether the Assassin has reached the edge of the board
     */
    private boolean edgeReached() {
        AdjacentCoords adjacentCoords =
            Tile.getAdjacentCoords(this.coords);
        return adjacentCoords.getCoordsInDirection(this.currentDirection) == null;
    }


    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
                "%s %s %s ",
                this.getClass().getSimpleName(),
                this.coords.serialise(),
                this.currentDirection
        );
    }

}





