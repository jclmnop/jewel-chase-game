package Entities.Items;

import DataTypes.Colour;
import DataTypes.Coords;

public class Gate extends Item {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "gate";
    private final Colour colour;

    public Gate(Coords coords, Colour colour) {
        super(CollisionType.GATE, true, coords);
        if (colour == Colour.CYAN || colour == Colour.MAGENTA) {
            throw new RuntimeException("Invalid Gate colour");
        }
        this.colour = colour;
        this.imagePath = String.format("%s_%s.png", IMAGE_PATH, colour);
    }

    public Gate(Coords coords) {
        this(coords, Colour.YELLOW);
    }

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
