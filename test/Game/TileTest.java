package Game;

import DataTypes.AdjacentTiles;
import DataTypes.Coords;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TileTest {
    // TODO: test all public methods
    // TODO: test all public static methods

    @Test
    public void testMultiColourAdjacencyMap() {
        Tile.newBoard(Boards.CASE_2.TARGET_BOARD, 5, 3);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                Coords coords = new Coords(x, y);
                AdjacentTiles multiColourAdjacentTiles = Tile.getMultiColourAdjacentTiles(coords);
                System.out.printf("%s: \n", coords);
                System.out.printf("\t%s\n", multiColourAdjacentTiles);
            }
        }
        var coords = new Coords(0, 0);
        var adjacent = Tile.getMultiColourAdjacentTiles(coords);
        Assertions.assertEquals(Tile.getTile(new Coords(0, 2)), adjacent.down());
        Tile.clearBoard();
    }
}
