package DataTypes;

public enum Colour {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    CYAN,
    MAGENTA;

    public static Colour fromChar(char c) {
        //TODO: replace null with error
        return switch (c) {
            case 'R' -> RED;
            case 'G' -> GREEN;
            case 'B' -> BLUE;
            case 'Y' -> YELLOW;
            case 'C' -> CYAN;
            case 'M' -> MAGENTA;
            default  -> null;
        };
    }

    public char toChar() {
        return switch (this) {
            case RED -> 'R';
            case GREEN -> 'G';
            case BLUE -> 'B';
            case YELLOW -> 'Y';
            case CYAN -> 'C';
            case MAGENTA -> 'M';
        };
    }
}
