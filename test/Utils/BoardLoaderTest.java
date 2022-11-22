package Utils;
import DataTypes.Colour;
import DataTypes.Colours;
import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import Game.Tile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Array;

public class BoardLoaderTest {
    @Test
    public void testLoadBoard() throws ParseBoardException, ParseTileColourException, ClassNotFoundException {
        var testString = """
                         5 3
                         YYYY YYYY YYYY YYYY YGRG
                         RRCR RRRR RRRR RRRR RRYY
                         RCCC CMMC MCCM CCCC CGCG
                         """;
        var YYYY = new Colours(Colour.YELLOW, Colour.YELLOW, Colour.YELLOW, Colour.YELLOW);
        var YGRG = new Colours(Colour.YELLOW, Colour.GREEN, Colour.RED, Colour.GREEN);
        var RRCR = new Colours(Colour.RED, Colour.RED, Colour.CYAN, Colour.RED);
        var RRRR = new Colours(Colour.RED, Colour.RED, Colour.RED, Colour.RED);
        var RRYY = new Colours(Colour.RED, Colour.RED, Colour.YELLOW, Colour.YELLOW);
        var RCCC = new Colours(Colour.RED, Colour.CYAN, Colour.CYAN, Colour.CYAN);
        var CMMC = new Colours(Colour.CYAN, Colour.MAGENTA, Colour.MAGENTA, Colour.CYAN);
        var MCCM = new Colours(Colour.MAGENTA, Colour.CYAN, Colour.CYAN, Colour.MAGENTA);
        var CCCC = new Colours(Colour.CYAN, Colour.CYAN, Colour.CYAN, Colour.CYAN);
        var CGCG = new Colours(Colour.CYAN, Colour.GREEN, Colour.CYAN, Colour.GREEN);

        Tile[][] targetBoard = new Tile[3][5];
        targetBoard[0] = new Tile[]{
            new Tile(YYYY), new Tile(YYYY), new Tile(YYYY), new Tile(YYYY), new Tile(YGRG)
        };
        targetBoard[1] = new Tile[]{
            new Tile(RRCR), new Tile(RRRR), new Tile(RRRR), new Tile(RRRR), new Tile(RRYY)
        };
        targetBoard[2] = new Tile[]{
            new Tile(RCCC), new Tile(CMMC), new Tile(MCCM), new Tile(CCCC), new Tile(CGCG)
        };

        Tile[][] resultBoard = BoardLoader.loadBoard(testString);

        for (int y = 0; y < targetBoard.length; y++) {
            for (int x = 0; x < targetBoard[0].length; x++) {
                Assertions.assertEquals(targetBoard[y][x].getColours(), resultBoard[y][x].getColours());
            }
        }
    }
}
