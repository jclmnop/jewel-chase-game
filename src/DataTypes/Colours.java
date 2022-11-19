package DataTypes;

/**
 * Represents an immutable tuple of 4 Colours (Java doesn't have tuples).
 */
public record Colours(Colour c1, Colour c2, Colour c3, Colour c4) {
    @Override
    public String toString() {
        return "" + c1.toChar() + c2.toChar() + c3.toChar() + c4.toChar();
    }
}
