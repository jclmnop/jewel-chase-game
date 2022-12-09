package Entities;

import DataTypes.Coords;
import Entities.Items.Bomb;
import Entities.Items.Door;
import Game.Tile;

/**
 * Destroys any item it collides with. Instances are created from bomb.
 */
public class Explosion extends Entity {

    private final int MAX_DURATION_TICKS = 1000; // Explosion is present on screen for 1 second
    private int currentDurationTicks = 0;

    public Explosion(CollisionType collisionType, boolean blocking, Coords coords) {
        super(collisionType, blocking, coords);
    }

    /**
     * Checks if explosion should be present on board. If so, it destroys certain items.
     */
    public void check() {
        if (currentDurationTicks < MAX_DURATION_TICKS) {
            destroy();
            currentDurationTicks += Game.MILLI_PER_TICK;
        } else {
            Entity.removeEntity(this);
        }
    }

    /**
     * Removes any item that is not a door.
     */
    public void destroy() {
        for (Entity e : Tile.getTile(coords).getEntities()) {
            if (!(e instanceof Door)) {
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
        // TODO Auto-generated method stub
        return null;
    }
    
}
