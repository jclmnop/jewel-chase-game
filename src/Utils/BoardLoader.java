package Utils;

import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import Game.Tile;

/**
 * Utility class for loading a board from a string.
 *
 * @author Jonny
 * @version 1.0
 */
public class BoardLoader {
    /**
     * Load a 2D Tile array from a string.
     * @param boardString The string representing the board to be loaded.
     * @return The deserialised 2D Tile array.
     * @throws ParseBoardException If there's a discrepancy between the expected
     *                             height/width and the actual height/width.
     * @throws NumberFormatException If any strings cannot be parsed to integers.
     * @throws ParseTileColourException If any colour characters cannot be parsed
     *                                  to a Colour enum.
     */
    public static Tile[][] loadBoard(String boardString)
    throws ParseBoardException, NumberFormatException, ParseTileColourException {
        var lines = boardString.lines().toList();
        var it = lines.iterator();
        var dimensionsStrings = it.next().split(" ");
        lines = lines.subList(1, lines.size());
        var width = Integer.parseInt(dimensionsStrings[0]);
        var height = Integer.parseInt(dimensionsStrings[1]);
        var board = new Tile[height][width];

        if (lines.size() != height) {
            throw new ParseBoardException(height, lines.size());
        }

        for (int i = 0; i < height; i++) {
            var row = lines.get(i).split(" ");
            if (row.length != width) {
                throw new ParseBoardException(width, row.length, i);
            }

            for (int k = 0; k < width; k++) {
                var deserialisedTile =  Deserialiser.deserialiseObject(row[k]);
                if (deserialisedTile instanceof Tile) {
                    board[i][k] = (Tile) deserialisedTile;
                } else {
                    throw new ClassCastException(
                        "Expected Tile, found " + deserialisedTile.getClass().getName()
                    );
                }
            }
        }

        return board;
    }
}
