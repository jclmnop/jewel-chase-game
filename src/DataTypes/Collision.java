package DataTypes;

import java.util.Arrays;
import java.util.Comparator;

import Entities.Entity;

/**
 * Represents a collision between to Entity objects and contains relevant
 * information about the collision.
 * @author Jonny
 * @version 1.1
 */
public class Collision {
    private final Coords coords;
    private final Entity entityOne;
    private final Entity entityTwo;

    /**
     * Construct a Collision object, ordering of the Entities is not important.
     * @param coords Coordinates of Tile where collision took place.
     * @param entityOne First Entity involved in collision.
     * @param entityTwo Second Entity involved in collision.
     */
    public Collision(Coords coords, Entity entityOne, Entity entityTwo) {
        this.coords = coords;
        Entity[] sortedByCollisionType = this.sortByCollisionType(entityOne, entityTwo);
        this.entityOne = sortedByCollisionType[0];
        this.entityTwo = sortedByCollisionType[1];
    }

    /**
     * Get coordinates of collision.
     * @return Coordinates of collision.
     */
    public Coords getCoords() {
        return coords;
    }

    /**
     * Get first entity involved in collision.
     * @return First entity involved in collision.
     */
    public Entity getEntityOne() {
        return entityOne;
    }

    /**
     * Get second entity involved in collision.
     * @return Second entity involved in collision.
     */
    public Entity getEntityTwo() {
        return entityTwo;
    }

    /**
     * Compute whether a collision is still valid, and needs to be processed,
     * or whether it's expired and should be discarded.
     * @return true if it's valid, false if not.
     */
    public boolean isValid() {
        return this.coords.equals(this.entityOne.getCoords())
            && this.coords.equals(this.entityTwo.getCoords());
    }

    private Entity[] sortByCollisionType(Entity entityOne, Entity entityTwo) {
        Entity[] entities = {entityOne, entityTwo};
        Arrays.sort(entities, Comparator.comparing(Entity::getCollisionType));
        return entities;
    }
}
