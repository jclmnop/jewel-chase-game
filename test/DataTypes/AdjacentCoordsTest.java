package DataTypes;

import Game.Tile;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdjacentCoordsTest {
    @Test
    public void testToArray() {
        Tile.newBoard(Boards.CASE_1.TARGET_BOARD, 5, 3);
        var adj = Tile.getMultiColourAdjacentTiles(new Coords(0, 0));
        var adjArr = adj.toArray();
        Assertions.assertEquals(adj.up(), adjArr[0]);
        Assertions.assertEquals(adj.down(), adjArr[1]);
        Assertions.assertEquals(adj.left(), adjArr[2]);
        Assertions.assertEquals(adj.right(), adjArr[3]);
    }
}
