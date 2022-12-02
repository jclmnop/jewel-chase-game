package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;
import Game.Tile;

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

    protected boolean move(Coords nextCoords) {
        if (this.ticksSinceLastMove >= this.speed) {
            this.ticksSinceLastMove = 0;
            this.currentDirection = this.coords.directionTo(nextCoords);
            Tile.move(this, this.coords, nextCoords);
            return true;
        } else {
            return false;
        }
    }
}
