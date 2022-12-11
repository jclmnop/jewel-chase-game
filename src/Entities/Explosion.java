package Entities;

import DataTypes.Coords;
import Entities.Items.Bomb;
import Entities.Items.Door;
import Entities.Items.Gate;
import Entities.Items.Item;
import Game.Tile;
import Interfaces.Handleable;

/**
 * Destroys any item it collides with. Instances are created from bomb.
 *
 * @author Dillon
 * @version 1.1
 * @see Interfaces.Handleable
 * @see Interfaces.Renderable
 * @see Interfaces.Serialisable
 * @see Entities.Entity
 */
public class Explosion extends Entity implements Handleable {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "explosion.png";
    private static final int MAX_DURATION_TICKS = 5; // Explosion is present on screen for 1 tick
    private int currentDurationTicks;

    /**
     * Creates an explosion with the specified number of ticks passed.
     * @param coords Coordinates for explosion.
     * @param currentDurationTicks Duration that explosion has existed for, in
     *                             ticks.
     */
    public Explosion(Coords coords, int currentDurationTicks) {
        super(CollisionType.EXPLOSION, false, coords);
        this.currentDurationTicks = currentDurationTicks;
        this.imagePath = IMAGE_PATH;
    }

    /**
     * Creates an explosion with a default duration value of 0;
     * @param coords Coordinates of explosion.
     */
    public Explosion(Coords coords) {
        this(coords, 0);
    }

    /**
     * Checks if explosion should be present on board. If so, it destroys certain items.
     */
    @Override
    public void handle() {
        if (currentDurationTicks < MAX_DURATION_TICKS) {
//            destroy();
            currentDurationTicks++;
        } else {
            Entity.removeEntity(this);
        }
    }

    /**
     * Serialise object into a string representation.
     * @return Serialised string representation.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.currentDurationTicks
        );
    }
    
}
