package Entities.Characters.Npc;

import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Characters.Character;
import Game.Tile;

public class FlyingAssassin extends Npc {

    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "stuart_bat.gif";
    private static final int MAX_WIDTH = Tile.getWidth();
    private static final int MIN_WIDTH = 0;
    private static final int MAX_HEIGHT = Tile.getHeight();
    private static final int MIN_HEIGHT = 0;

    public FlyingAssassin(Coords coords, int speed, Direction dir) {
        super(CollisionType.ASSASSIN, false, coords, speed);
        this.currentDirection = dir;
        this.imagePath = IMAGE_PATH;
    }

    public FlyingAssassin(Coords coords, int speed) {

        this(coords, speed, currentDirection.UP);
    }

    /**
     * Attempts to move to the next tile in the direction it's facing.
     * If it reaches an edge, FlyingAssassin will be rotated 180 degrees
     */
    @Override
    public void tryMove() {
        if (EdgeReached()) {
            currentDirection = Direction.turnAround(this.currentDirection);
        }
        //get the next coords in the direction that the assassin is facing
        Coords nextCoords = ;
        this.move(nextCoords);

    }

    /**
     * Checks whether the Assassin has reached the edge of the board
     */

    private boolean EdgeReached() {
        if ((currentDirection == Direction.LEFT && this.coords.x() == MIN_WIDTH)) {
            return true;
        } else if (currentDirection == Direction.RIGHT && this.coords.x() == MAX_WIDTH) {
            return true;
        } else if (currentDirection == Direction.UP && this.coords.y() == MAX_HEIGHT) {
            return true;
        } else if (currentDirection == Direction.DOWN && this.coords.y() == MIN_HEIGHT) {
            return true;
        } else {
            return false;
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
                "%s %s %s ",
                this.getClass().getSimpleName(),
                this.coords.serialise(),
                this.currentDirection
        );
    }

}





