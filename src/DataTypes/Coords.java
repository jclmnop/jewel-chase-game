package DataTypes;

/**
 * Represents position of an Entity in the level. Immutable once constructed
 * to ensure an instance is not modified accidentally. If new Coords are
 * required then a new instance must be instantiated.
 */
public record Coords(int x, int y) {
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
            if (to.y > this.y) {
                return Direction.UP;
            } else if (to.x < this.x) {
                return Direction.LEFT;
            } else if (to.y < this.y) {
                return Direction.DOWN;
            } else {
                return Direction.RIGHT;
            }
        } else {
            throw new IllegalArgumentException("Direction cannot be diagonal");
        }
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Coords other) {
        if (other == null) {
            return false;
        }
        return this.x == other.x && this.y == other.y;
    }
}
