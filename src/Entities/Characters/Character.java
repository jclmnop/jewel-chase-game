package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;
import Game.Tile;

public abstract class Character extends Entity {
    protected Direction currentDirection;
    private int speed;
    private int ticksSinceLastMove;
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
