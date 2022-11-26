package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;
import Game.Tile;

public abstract class Character extends Entity {
    protected Direction currentDirection;
    protected Coords coords;
    //TODO: sprite/image file?
    //TODO: death animation?

    public Character(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
    }

    public void kill() {
        // TODO: death animation?
        Entity.removeEntity(this);
    }
}
