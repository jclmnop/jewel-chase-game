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
}
