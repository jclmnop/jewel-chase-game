package TestCases;

import DataTypes.Colour;
import DataTypes.Colours;
import Game.Tile;

public class Boards {
    public static final class CASE_1 {
        public static final String BOARD_STR = """
                                               5 3
                                               YYYY YYYY YYYY YYYY YGRG
                                               RRCR RRRR RRRR RRRR RRYY
                                               RCCC CMMC MCCM CCCC CGCG
                                               """;
        public static final Colours YYYY = new Colours(Colour.YELLOW, Colour.YELLOW, Colour.YELLOW, Colour.YELLOW);
        public static final Colours YGRG = new Colours(Colour.YELLOW, Colour.GREEN, Colour.RED, Colour.GREEN);
        public static final Colours RRCR = new Colours(Colour.RED, Colour.RED, Colour.CYAN, Colour.RED);
        public static final Colours RRRR = new Colours(Colour.RED, Colour.RED, Colour.RED, Colour.RED);
        public static final Colours RRYY = new Colours(Colour.RED, Colour.RED, Colour.YELLOW, Colour.YELLOW);
        public static final Colours RCCC = new Colours(Colour.RED, Colour.CYAN, Colour.CYAN, Colour.CYAN);
        public static final Colours CMMC = new Colours(Colour.CYAN, Colour.MAGENTA, Colour.MAGENTA, Colour.CYAN);
        public static final Colours MCCM = new Colours(Colour.MAGENTA, Colour.CYAN, Colour.CYAN, Colour.MAGENTA);
        public static final Colours CCCC = new Colours(Colour.CYAN, Colour.CYAN, Colour.CYAN, Colour.CYAN);
        public static final Colours CGCG = new Colours(Colour.CYAN, Colour.GREEN, Colour.CYAN, Colour.GREEN);
        public static final Tile[][] TARGET_BOARD = {
            new Tile[]{
                new Tile(YYYY), new Tile(YYYY), new Tile(YYYY), new Tile(YYYY), new Tile(YGRG)
            },
            new Tile[]{
                new Tile(RRCR), new Tile(RRRR), new Tile(RRRR), new Tile(RRRR), new Tile(RRYY)
            },
            new Tile[]{
                new Tile(RCCC), new Tile(CMMC), new Tile(MCCM), new Tile(CCCC), new Tile(CGCG)
            }
        };
    }
}
