package DataTypes;

/**
 * Represents an immutable tuple of 4 Colours (Java doesn't have tuples).
 */
public class Colours {
    private final Colour c1;
    private final Colour c2;
    private final Colour c3;
    private final Colour c4;

    public Colours(Colour c1, Colour c2, Colour c3, Colour c4) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    public Colour getC1() {
        return c1;
    }

    public Colour getC2() {
        return c2;
    }

    public Colour getC3() {
        return c3;
    }

    public Colour getC4() {
        return c4;
    }

    @Override
    public String toString() {
        return "" + c1 + c2 + c3 + c4;
    }
}
