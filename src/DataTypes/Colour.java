package DataTypes;

import DataTypes.Exception.ParseTileColourException;
import javafx.scene.image.Image;

public enum Colour {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    CYAN,
    MAGENTA;

    public static final String IMAGE_PATH = "App/resources/tileImages/";

    public static Colour fromChar(char c) throws ParseTileColourException {
        return switch (c) {
            case 'R' -> RED;
            case 'G' -> GREEN;
            case 'B' -> BLUE;
            case 'Y' -> YELLOW;
            case 'C' -> CYAN;
            case 'M' -> MAGENTA;
            default  -> {
                throw new ParseTileColourException(c);
            }
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

    public Image toImage() {
        return switch (this) {
            case RED     -> new Image(IMAGE_PATH + "redTile.png");
            case GREEN   -> new Image(IMAGE_PATH + "greenTile.png");
            case BLUE    -> new Image(IMAGE_PATH + "blueTile.png");
            case YELLOW  -> new Image(IMAGE_PATH + "yellowTile.png");
            case CYAN    -> new Image(IMAGE_PATH + "cyanTile.png");
            case MAGENTA -> new Image(IMAGE_PATH + "magentaTile.png");
        };
    }
}
