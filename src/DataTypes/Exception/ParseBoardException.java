package DataTypes.Exception;

public class ParseBoardException extends Exception {
    public static final String INCORRECT_HEIGHT =
        "Expected height: %s, actual height is %s";
    public static final String INCORRECT_WIDTH =
        "Expected width: %s, actual width is %s on row %s";
    public ParseBoardException(int expectedHeight, int actualHeight) {
        super(String.format(INCORRECT_HEIGHT, expectedHeight, actualHeight));
    }

    public ParseBoardException(int expectedWidth, int actualWidth, int row) {
        super(String.format(INCORRECT_WIDTH, expectedWidth, actualWidth, row));
    }
}
