package DataTypes;

import DataTypes.Exception.ParseTileColourException;
import Interfaces.Renderable;
import javafx.scene.image.Image;

public enum Colour implements Renderable {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    CYAN,
    MAGENTA;

    public static final String IMAGE_PATH = "App/resources/tiles/";
    private Image image = null;

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
        if (this.image == null) {
            this.image = switch (this) {
                case RED     -> new Image(IMAGE_PATH + "red.png");
                case GREEN   -> new Image(IMAGE_PATH + "green.png");
                case BLUE    -> new Image(IMAGE_PATH + "blue.png");
                case YELLOW  -> new Image(IMAGE_PATH + "yellow.png");
                case CYAN    -> new Image(IMAGE_PATH + "cyan.png");
                case MAGENTA -> new Image(IMAGE_PATH + "magenta.png");
            };
        }
        return this.image;
    }
}
