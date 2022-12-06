package Entities.Items;

import DataTypes.Coords;

public class Door extends Item{
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "door.png";
    public Door(Coords coords) {
        super(CollisionType.DOOR, false, coords);
        this.imagePath = IMAGE_PATH;
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise()
        );
    }
}
