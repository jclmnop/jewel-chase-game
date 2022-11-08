package DataTypes;

import java.util.Objects;

/**
 * Represents position of an Entity in the level. Immutable once constructed
 * to ensure an instance is not modified accidentally. If new Coords are
 * required then a new instance must be instantiated.
 */
public class Coords {
    private final int x;
    private final int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // TODO: throw errors for movement or not? Probably don't need to bc
    //       we'll only be moving to valid Tiles.
    public Coords moveUp() {
        return new Coords(this.x, this.y + 1);
    }

    public Coords moveDown() {
        return new Coords(this.x, this.y - 1);
    }

    public Coords moveLeft() {
        return new Coords(this.x - 1, this.y);
    }

    public Coords moveRight() {
        return new Coords(this.x + 1, this.y);
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
