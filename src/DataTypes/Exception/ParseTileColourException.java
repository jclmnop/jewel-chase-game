package DataTypes.Exception;

public class ParseTileColourException extends Exception {
    public ParseTileColourException(char colourChar) {
        super(String.format("%s could not be parsed as a valid colour", colourChar));
    }
}
