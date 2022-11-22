package Utils;
import DataTypes.Colour;
import DataTypes.Colours;
import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import Game.Tile;
import TestCases.Boards;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class BoardLoaderTest {
    @Test
    public void testLoadBoard() throws ParseBoardException, ParseTileColourException, ClassNotFoundException {
        var testString = Boards.CASE_1.BOARD_STR;
        var targetBoard = Boards.CASE_1.TARGET_BOARD;

        Tile[][] resultBoard = BoardLoader.loadBoard(testString);

        for (int y = 0; y < targetBoard.length; y++) {
            for (int x = 0; x < targetBoard[0].length; x++) {
                Assertions.assertEquals(targetBoard[y][x].getColours(), resultBoard[y][x].getColours());
            }
        }
    }
}
