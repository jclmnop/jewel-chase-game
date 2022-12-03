package DataTypes;

/**
 * Position of an Entity in the level, represented as (x, y) starting from
 * top left of the board.
 *
 * e.g: (1, 0)
 * @author Jonny
 * @version 1.2
 */
public record Coords(int x, int y) {
    /**
     * Get the coordinate in the specified direction from the starting coordinate.
     * @param coords The starting coordinate.
     * @param direction The direction to "travel".
     * @return The coordinate in the given direction.
     */
    public static Coords move(Coords coords, Direction direction) {
        return switch (direction) {
            case UP    -> moveUp(coords);
            case DOWN  -> moveDown(coords);
            case LEFT  -> moveLeft(coords);
            case RIGHT -> moveRight(coords);
        };
    }

    /**
     * Calculate the direction one would need to travel to reach a tile from
     * this one. Does not work for diagonal directions.
     * @param to Destination tile.
     * @return Direction needed to travel in order to reach destination tile.
     * @throws IllegalArgumentException if `to` is diagonal from current tile.
     */
    public Direction directionTo(Coords to) {
        if (to.x != this.x || to.y != this.y) {
            if (to.y < this.y) {
                return Direction.UP;
            } else if (to.x < this.x) {
                return Direction.LEFT;
            } else if (to.y > this.y) {
                return Direction.DOWN;
            } else {
                return Direction.RIGHT;
            }
        } else {
            throw new IllegalArgumentException("Direction cannot be diagonal");
        }
    }

    /**
     * String representation of coords, e.g: "(1, 0)"
     * @return String representation.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compare this Coords object with another, checking whether their x and y
     * values are equal to each other.
     * @param other The other Coords object to compare with.
     * @return true if they're equal, false otherwise.
     */
    public boolean equals(Coords other) {
        if (other == null) {
            return false;
        }
        return this.x == other.x && this.y == other.y;
    }

    private static Coords moveUp(Coords coords) {
        return new Coords(coords.x, coords.y - 1);
    }

    private static Coords moveDown(Coords coords) {
        return new Coords(coords.x, coords.y + 1);
    }

    private static Coords moveLeft(Coords coords) {
        return new Coords(coords.x - 1, coords.y);
    }

    private static Coords moveRight(Coords coords) {
        return new Coords(coords.x + 1, coords.y);
    }

}
