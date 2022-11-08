package DataTypes;

import java.util.Arrays;
import java.util.Comparator;

import Entities.Entity;

public class Collision {
    private final Coords coords;
    private final Entity entityOne;
    private final Entity entityTwo;

    public Collision(Coords coords, Entity entityOne, Entity entityTwo) {
        this.coords = coords;
        Entity[] sortedByCollisionType = this.sortByCollisionType(entityOne, entityTwo);
        this.entityOne = sortedByCollisionType[0];
        this.entityTwo = sortedByCollisionType[1];
    }

    public Coords getCoords() {
        return coords;
    }

    public Entity getEntityOne() {
        return entityOne;
    }

    public Entity getEntityTwo() {
        return entityTwo;
    }

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
