package Entities.Characters.Npc;

import DataTypes.Coords;
import Entities.Characters.Character;
import Interfaces.Handleable;

/**
 * Represents a non-player character which must be controlled by the game itself.
 *
 * @author Jonny
 * @version 1.1
 */
public abstract class Npc extends Character implements Handleable {

    /**
     * Construct an NPC object with given parameters.
     * @param collisionType The enum type to be used when calculating the outcome
     *                      of a collision between this NPC and another entity.
     * @param blocking Whether this NPC stops other entities from occupying
     *                 the same coordinate.
     * @param coords Coordinates of the tile to spawn this NPC on.
     * @param ticksPerMove Numbers of ticks this NPC must wait for between
     *                     moves.
     */
    public Npc(
        CollisionType collisionType,
        boolean blocking,
        Coords coords,
        int ticksPerMove
    ) {
        super(collisionType, blocking, coords, ticksPerMove);
    }

    /**
     * Called by Game class to perform any necessary actions per tick for this
     * entity.
     */
    @Override
    public void handle() {
        this.tryMove();
    }

    protected abstract void tryMove();
}
