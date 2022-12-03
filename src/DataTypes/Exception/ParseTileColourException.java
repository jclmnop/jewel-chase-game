package DataTypes.Exception;

/**
 * Exception resulting from attempting to parse an invalid character into
 * a Colour value.
 * @author Jonny
 * @version 1.0
 */
public class ParseTileColourException extends IllegalArgumentException {
    /**
     * Construct error along with its error message by including the character
     * which could not be parsed.
     * @param colourChar Character which could not be parsed.
     */
    public ParseTileColourException(char colourChar) {
        super(String.format("%s could not be parsed as a valid colour", colourChar));
    }
}
