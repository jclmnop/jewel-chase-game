package DataTypes;

import DataTypes.Exception.ParseTileColourException;
import Interfaces.Renderable;
import javafx.scene.image.Image;

/**
 * Enum representing Colour of a Tile.
 * @author Jonny
 * @version 1.3
 */
public enum Colour implements Renderable {
    /** Red */
    RED,
    /** Green */
    GREEN,
    /** Blue */
    BLUE,
    /** Yellow */
    YELLOW,
    /** Cyan */
    CYAN,
    /** Magenta */
    MAGENTA;

    /** Path to directory containing image files for each colour. */
    public static final String IMAGE_PATH = "App/resources/tiles/";
    private Image image = null;

    /**
     * Parse a single character into a Colour.
     * @param c Character to parse.
     * @return The parsed Colour value.
     * @throws ParseTileColourException if input can't be parsed to a valid colour.
     */
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

    /**
     * Convert this colour to its corresponding character value.
     * @return The character value.
     */
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

    /**
     * Load and return the Image object for this colour. Required to render
     * a Tile object.
     * @return Image object for this colour.
     */
    @Override
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
