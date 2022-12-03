package DataTypes;

/**
 * Represents an immutable tuple of 4 Colours.
 * @author Jonny
 * @version 1.0
 */
public record Colours(Colour c1, Colour c2, Colour c3, Colour c4) {
    /**
     * Convert Colours into string representation.
     * e.g: "RRYY" for (RED, RED, YELLOW, YELLOW
     * @return String representation.
     */
    @Override
    public String toString() {
        return "" + c1.toChar() + c2.toChar() + c3.toChar() + c4.toChar();
    }
}
