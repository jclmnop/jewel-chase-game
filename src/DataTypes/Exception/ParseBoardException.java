package DataTypes.Exception;

/**
 * Exception resulting from being unable to parse a board from a string due to
 * invalid values.
 * @author Jonny
 * @version 1.0
 */
public class ParseBoardException extends IllegalArgumentException {
    public static final String INCORRECT_HEIGHT =
        "Expected height: %s, actual height is %s";
    public static final String INCORRECT_WIDTH =
        "Expected width: %s, actual width is %s on row %s";

    /**
     * Construct exception with a message including expected height of parsed
     * board and actual height.
     * @param expectedHeight Expected height of parsed board.
     * @param actualHeight Actual height of parsed board.
     */
    public ParseBoardException(int expectedHeight, int actualHeight) {
        super(String.format(INCORRECT_HEIGHT, expectedHeight, actualHeight));
    }

    /**
     * Construct exception with a message including expected width of parsed
     * board, actual width of parsed board, and the row on which this exception
     * occurred.
     * @param expectedWidth Expected width of parsed board.
     * @param actualWidth Actual width of parsed board.
     * @param row Row number (starting from 0) where width was different to
     *            the expected width.
     */
    public ParseBoardException(int expectedWidth, int actualWidth, int row) {
        super(String.format(INCORRECT_WIDTH, expectedWidth, actualWidth, row));
    }
}
