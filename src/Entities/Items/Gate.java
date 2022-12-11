package Entities.Items;

import DataTypes.Colour;
import DataTypes.Coords;

/**
 * A gate which blocks character movement and can only be opened with keys
 * of the same colour.
 *
 * @author Luke
 * @version 1.0
 * @see Entities.Items.Item
 */
public class Gate extends Item {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "gate";
    private final Colour colour;

    /**
     * Construct a gate with the given parameters.
     * @param coords Coordinates of gate.
     * @param colour Colour associated with gate.
     */
    public Gate(Coords coords, Colour colour) {
        super(CollisionType.GATE, true, coords);
        if (colour == Colour.CYAN || colour == Colour.MAGENTA) {
            throw new RuntimeException("Invalid Gate colour");
        }
        this.colour = colour;
        this.imagePath = String.format("%s_%s.png", IMAGE_PATH, colour);
    }

    /**
     * Construct a gate with the given parameters. Defaults to colour YELLOW.
     * @param coords Coordinates of gate.
     */
    public Gate(Coords coords) {
        this(coords, Colour.YELLOW);
    }

    /**
     * @return Colour associated with this gate.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.colour
        );
    }
}
