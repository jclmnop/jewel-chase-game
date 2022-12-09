package Entities.Items;

import DataTypes.Colour;
import DataTypes.Coords;
import javafx.scene.image.Image;

public class Gate extends Item {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "gate";
    private final Colour colour;

    public Gate(Coords coords, Colour colour) {
        super(CollisionType.GATE, true, coords);
        this.colour = colour;
    }

    public Gate(Coords coords) {
        this(coords, Colour.YELLOW);
    }

    @Override
    public Image toImage() {
        //TODO
        return super.toImage();
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
