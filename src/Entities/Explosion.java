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
 */
public class Explosion extends Entity implements Handleable {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "explosion.png";
    private static final int MAX_DURATION_TICKS = 2; // Explosion is present on screen for 1 tick
    private int currentDurationTicks = 0;

    /**
     * Loads an explosion with the specified number of ticks passed.
     * @param coords Coordinates for explosion.
     * @param currentDurationTicks Duration that explosion has existed for, in
     *                             ticks.
     */
    public Explosion(Coords coords, int currentDurationTicks) {
        super(CollisionType.EXPLOSION, false, coords);
        this.currentDurationTicks = currentDurationTicks;
        this.imagePath = IMAGE_PATH;
    }

    public Explosion(Coords coords) {
        this(coords, 0);
    }

    /**
     * Checks if explosion should be present on board. If so, it destroys certain items.
     */
    @Override
    public void handle() {
        if (currentDurationTicks < MAX_DURATION_TICKS) {
            destroy();
            currentDurationTicks++;
        } else {
            Entity.removeEntity(this);
        }
    }

    /**
     * Removes any item that is not a door or gate.
     */
    public void destroy() {
        for (Entity e : Tile.getTile(coords).getEntities()) {
            if (!(e instanceof Door) && !(e instanceof Gate)) {
                // If explosion collides with bomb, that bomb explodes.
                if (e instanceof Bomb) {
                    ((Bomb) e).explode();
                } else {
                    Entity.removeEntity(e);
                }
            }
        }
    }

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
