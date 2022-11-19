package Utils;

import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import Game.Tile;

public class BoardLoader {
    public static Tile[][] loadBoard(String boardString) throws ParseBoardException, NumberFormatException, ParseTileColourException, ClassNotFoundException {
        var lines = boardString.lines().toList();
        var it = lines.iterator();
        var dimensionsStrings = it.next().split(" ");
        it.remove();
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
