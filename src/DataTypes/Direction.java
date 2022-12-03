package DataTypes;

/**
 * Used to represent direction on the map. Primarily for current direction a
 * Character object is facing.
 *
 * @author Jonny
 * @version 1.1
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * Turn left by 90 degrees from the given direction.
     * @param direction Direction to turn from.
     * @return Direction after turning left.
     */
    public static Direction turnLeft(Direction direction) {
        return switch (direction) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };
    }

    /**
     * Turn by 180 degrees from given direction.
     * @param direction Direction to turn from.
     * @return Direction after turning 180 degrees.
     */
    public static Direction turnAround(Direction direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    /**
     * Turn right by 90 degrees from given direction.
     * @param direction Direction to turn from.
     * @return Direction after turning right.
     */
    public static Direction turnRight(Direction direction) {
        return turnLeft(turnAround(direction));
    }

    /**
     * Compute the numerical representation of a direction in degrees, assuming
     * clockwise orientation with RIGHT as 0 degrees.
     * @return Direction in degrees.
     */
    public double toDegrees() {
        return switch (this) {
            case UP -> 270;
            case DOWN -> 90;
            case LEFT -> 180;
            case RIGHT -> 0;
        };
    }
}
