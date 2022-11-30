package DataTypes;

import DataTypes.Exception.ParseTileColourException;
import javafx.scene.paint.Color;

public enum Colour {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    CYAN,
    MAGENTA;

    public static Colour fromChar(char c) throws ParseTileColourException {
        //TODO: replace null with error
        return switch (c) {
            case 'R' -> RED;
            case 'G' -> GREEN;
            case 'B' -> BLUE;
            case 'Y' -> YELLOW;
            case 'C' -> CYAN;
            case 'M' -> MAGENTA;
            default  -> {throw new ParseTileColourException(c);}
        };
    }

    public char toChar() {
        return switch (this) {
            case RED     -> 'R';
            case GREEN   -> 'G';
            case BLUE    -> 'B';
            case YELLOW  -> 'Y';
            case CYAN    -> 'C';
            case MAGENTA -> 'M';
        };
    }

    public Color toJfxColour() {
        return switch (this) {
            case RED     -> Color.RED;
            case GREEN   -> Color.GREEN;
            case BLUE    -> Color.BLUE;
            case YELLOW  -> Color.YELLOW;
            case CYAN    -> Color.CYAN;
            case MAGENTA -> Color.MAGENTA;
        };

    }
}
