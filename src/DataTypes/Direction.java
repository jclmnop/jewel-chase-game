package DataTypes;

/**
 * Used to represent direction on the map. Primarily for current direction a
 * Character object is facing.
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction turnLeft(Direction direction) {
        return switch (direction) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };
    }

    public static Direction turnAround(Direction direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    public static Direction turnRight(Direction direction) {
        return turnLeft(turnAround(direction));
    }
}
