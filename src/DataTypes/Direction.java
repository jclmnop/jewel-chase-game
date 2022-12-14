package DataTypes;

import Interfaces.Serialisable;

import java.util.Locale;

/**
 * Used to represent direction on the map. Primarily for current direction a
 * Character object is facing.
 *
 * @author Jonny
 * @version 1.1
 */
public enum Direction implements Serialisable {
    /** Up/North */
    UP,
    /** Down/South */
    DOWN,
    /** Left/West */
    LEFT,
    /** Right/East */
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

    /**
     * Serialise Direction enum into a String.
     *
     * e.g. Direction.UP -> "UP"
     * @return Serialised string
     */
    @Override
    public String serialise() {
        return this.toString();
    }

    /**
     * Deserialise a Direction enum from a string.
     * @param str String to deserialise.
     * @return Deserialised direction enum.
     * @throws IllegalArgumentException If string cannot be parsed to direction.
     */
    public static Direction fromString(String str) throws IllegalArgumentException {
        return switch (str.toUpperCase()) {
            case "UP" -> UP;
            case "DOWN" -> DOWN;
            case "LEFT" -> LEFT;
            case "RIGHT" -> RIGHT;
            default -> throw new IllegalArgumentException(
                "Cannot deserialise '" + str + "' to direction"
            );
        };
    }
}
