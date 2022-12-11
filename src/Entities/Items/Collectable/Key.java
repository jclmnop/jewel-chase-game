package Entities.Items.Collectable;

import DataTypes.Colour;
import DataTypes.Coords;
import Entities.Entity;
import Entities.Items.Gate;
import Entities.Items.Item;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Opens all Gates with the same colour.
 *
 * @author Luke
 * @version 1.0
 * @see Entities.Items.Collectable.Collectable
 */
public class Key extends Collectable {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "key";
    private final Colour colour;

    /**
     * Construct a key with the given parameters.
     * @param coords Coordinates of key.
     * @param colour Colour associated with key. This key will open all gates
     *               that are also associated with this colour.
     */
    public Key(Coords coords, Colour colour) {
        super(CollisionType.KEY, false, coords);
        if (colour == Colour.CYAN || colour == Colour.MAGENTA) {
            throw new RuntimeException("Invalid Gate colour");
        }
        this.colour = colour;
        this.imagePath = String.format("%s_%s.gif", IMAGE_PATH, colour);
    }

    /**
     * Construct a key with the given parameters.
     * @param coords Coordinates of key.
     */
    public Key(Coords coords) {
        this(coords, Colour.YELLOW);
    }

    /**
     * Opens all gates with the same colour as this lever.
     */
    public void openGates() {
        ArrayList<Gate> gates = Entity.getEntitiesOfType(Gate.class)
            .stream()
            .filter(gate -> gate.getColour() == this.colour)
            .collect(Collectors.toCollection(ArrayList::new));
        for (Gate gate : gates) {
            Entity.removeEntity(gate);
        }
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
