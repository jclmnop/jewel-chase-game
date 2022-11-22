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
