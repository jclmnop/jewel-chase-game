package Game;

import DataTypes.AdjacentCoords;
import DataTypes.Coords;
import DataTypes.Direction;
import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import Entities.Items.Collectable.Key;
import Entities.Items.Collectable.Loot;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TileTest {
    // TODO: test all public methods
    // TODO: test all public static methods
    // TODO: test that removing an entity from a tile works as intended

    @Test
    public void testMultiColourAdjacencyMap() {
        Tile.newBoard(Boards.CASE_2.TARGET_BOARD, 5, 3);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                // TODO: automatically check all tiles are correct, atm it
                //       just prints them out lol
                Coords coords = new Coords(x, y);
                AdjacentCoords multiColourAdjacentCoords = Tile.getMultiColourAdjacentTiles(coords);
                System.out.printf("%s: \n", coords);
                System.out.printf("\t%s\n", multiColourAdjacentCoords);
            }
        }
        // Check that downwards adjacent tile for (0, 0) is (0, 2) because they both
        // have yellow
        var coords = new Coords(0, 0);
        var adjacent = Tile.getMultiColourAdjacentTiles(coords);
        Assertions.assertEquals(
            Tile.getTile(new Coords(0, 2)),
            adjacent.getTileInDirection(Direction.DOWN)
        );
        Tile.clearBoard();
    }

    @Test
    public void testGetEntitiesOfType() {
        Tile.newBoard(Boards.CASE_2.TARGET_BOARD, 5, 3);
        Tile tile = Tile.getTile(new Coords(0, 0));
        Loot loot = new Loot(new Coords(0, 0));
        new Key(new Coords(0, 0));

        ArrayList<Loot> lootOnly = tile.getEntitiesOfType(Loot.class);
        Assertions.assertEquals(1, lootOnly.size());
        Assertions.assertEquals(loot, lootOnly.get(0));
    }

    @Test
    public void testSerialiseBoard() throws ParseBoardException, ParseTileColourException, ClassNotFoundException {
        var startBoard = Boards.CASE_1.TARGET_BOARD;
        Tile.newBoard(startBoard, 5, 3);
        var serialisedBoard = Tile.serialiseBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                Assertions.assertEquals(
                    startBoard[row][col].serialise(),
                    Tile.getTile(new Coords(col, row)).serialise()
                );
            }
        }
    }
}
