package DataTypes;

import Game.Tile;

/**
 * Contains adjacent tiles for a specific coordinate. If value is null, then
 * coordinate has no adjacent tiles in that direction.
 * @author Jonny
 * @version 1.2
 * @param up
 * @param down
 * @param left
 * @param right
 */
public record AdjacentCoords(Coords up, Coords down, Coords left, Coords right) {

    /**
     * Filters out adjacent coordinates based on colour.
     *
     * @param adjacentCoords Coordinates to filter
     * @param colour Colour to filter by
     * @return AdjacentCoords with coordinates not containing the colour set to
     *         null
     */
    public static AdjacentCoords singleColourAdjacentTiles(AdjacentCoords adjacentCoords,
                                                           Colour colour) {
        Coords up = AdjacentCoords.singleColourAdjacentTile(adjacentCoords.up, colour);
        Coords down = AdjacentCoords.singleColourAdjacentTile(adjacentCoords.down, colour);
        Coords left = AdjacentCoords.singleColourAdjacentTile(adjacentCoords.left, colour);
        Coords right = AdjacentCoords.singleColourAdjacentTile(adjacentCoords.right, colour);

        return new AdjacentCoords(up, down, left, right);
    }

    /**
     * Get coordinates in a specified direction.
     * @param direction Direction to fetch.
     * @return Coords in the specified direction or null if there are no
     *         adjacent coordinates in that direction.
     */
    public Coords getCoordsInDirection(Direction direction) {
        return switch (direction) {
            case UP -> this.up;
            case DOWN -> this.down;
            case LEFT -> this.left;
            case RIGHT -> this.right;
        };
    }

    /**
     * Get the Tile object itself in a specified direction.
     * @param direction Direction to fetch.
     * @return Tile object in specified direction, or null if there isn't one.
     */
    public Tile getTileInDirection(Direction direction) {
        Coords coords = this.getCoordsInDirection(direction);
        if (!Tile.isValidCoords(coords)) {
            return null;
        } else {
            return Tile.getTile(this.getCoordsInDirection(direction));
        }
    }

    /**
     * Convert an AdjacentCoords object into an array.
     * @return A Coords[4] array containing adjacent coordinatess (or null values)
     *         in the following order: [up, down, left, right]
     */
    public Coords[] toArray() {
        return new Coords[]{this.up, this.down, this.left, this.right};
    }

    /**
     * Convert AdjacentCoords object into a string representation
     * @return String representation of AdjacentCoords.
     *         e.g: "AdjacentTiles{up=(0,0),down=null. . .}"
     */
    @Override
    public String toString() {
        String upStr = (up != null) ? up.toString() : "null";
        String downStr = (down != null) ? down.toString() : "null";
        String leftStr = (left != null) ? left.toString() : "null";
        String rightStr = (right != null) ? right.toString() : "null";
        return "AdjacentTiles{" +
            "up=" + upStr +
            ", down=" + downStr +
            ", left=" + leftStr +
            ", right=" + rightStr +
            '}';
    }

    private static Coords singleColourAdjacentTile(Coords coords, Colour colour) {
        Tile tile = Tile.getTile(coords);
        if (tile == null) {
            return null;
        } else if (tile.hasColour(colour)) {
            return coords;
        } else {
            return null;
        }
    }
}
