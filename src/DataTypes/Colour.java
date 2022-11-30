package DataTypes;

import DataTypes.Exception.ParseTileColourException;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public enum Colour {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    CYAN,
    MAGENTA;

    public static final String IMAGE_PATH = "App/resources/tileImages/";

    public static Colour fromChar(char c) throws ParseTileColourException {
        //TODO: replace null with error
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

    private static Image blueTileImage = BLUE.loadImage();
    private static Image cyanTileImage = CYAN.loadImage();
    private static Image greenTileImage = GREEN.loadImage();
    private static Image magentaTileImage = MAGENTA.loadImage();
    private static Image redTileImage = RED.loadImage();
    private static Image yellowTileImage = YELLOW.loadImage();


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

    public Image toImage() {
        return switch (this) {
            case RED     -> redTileImage;
            case GREEN   -> greenTileImage;
            case BLUE    -> blueTileImage;
            case YELLOW  -> yellowTileImage;
            case CYAN    -> cyanTileImage;
            case MAGENTA -> magentaTileImage;
        };

    }

    private Image loadImage() {
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
