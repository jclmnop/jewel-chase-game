package DataTypes;

import Game.Tile;

public class AdjacentTiles {
    private final Tile up;
    private final Tile down;
    private final Tile left;
    private final Tile right;

    public AdjacentTiles(Tile up, Tile down, Tile left, Tile right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Tile getUp() {
        return up;
    }

    public Tile getDown() {
        return down;
    }

    public Tile getLeft() {
        return left;
    }

    public Tile getRight() {
        return right;
    }

}
