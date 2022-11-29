package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;

public abstract class Character extends Entity {
    protected Direction currentDirection;
    protected int speed;
    protected int ticksSinceLastMove;
    //TODO: sprite/image file?
    //TODO: death animation?

    public Character(CollisionType collisionType, boolean isBlocking, Coords coords, int speed) {
        super(collisionType, isBlocking, coords);
        this.speed = speed;
        this.ticksSinceLastMove = speed;
    }

    public void kill() {
        // TODO: death animation?
        Entity.removeEntity(this);
    }
}
