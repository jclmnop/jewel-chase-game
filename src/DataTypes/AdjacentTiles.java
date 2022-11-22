package DataTypes;

import Game.Tile;

/**
 * Contains adjacent tiles for a specific coordinate. If value is null, then
 * coordinate has no adjacent tiles in that direction.
 */
public record AdjacentTiles(Tile up, Tile down, Tile left, Tile right) {
    public static AdjacentTiles singleColourAdjacentTiles(AdjacentTiles adjacentTiles,
                                                          Colour colour) {
        Tile up = (adjacentTiles.up.hasColour(colour)) ? adjacentTiles.up : null;
        Tile down = (adjacentTiles.down.hasColour(colour)) ? adjacentTiles.down : null;
        Tile left = (adjacentTiles.left.hasColour(colour)) ? adjacentTiles.left : null;
        Tile right = (adjacentTiles.right.hasColour(colour)) ? adjacentTiles.right : null;

        return new AdjacentTiles(up, down, left, right);
    }

    public Tile getTileInDirection(Direction direction) {
        return switch (direction) {
            case UP -> this.up;
            case DOWN -> this.down;
            case LEFT -> this.left;
            case RIGHT -> this.right;
        };
    }

    @Override
    public String toString() {
        String upStr = (up != null) ? up.serialise() : "null";
        String downStr = (down != null) ? down.serialise() : "null";
        String leftStr = (left != null) ? left.serialise() : "null";
        String rightStr = (right != null) ? right.serialise() : "null";
        return "AdjacentTiles{" +
            "up=" + upStr +
            ", down=" + downStr +
            ", left=" + leftStr +
            ", right=" + rightStr +
            '}';
    }
}
