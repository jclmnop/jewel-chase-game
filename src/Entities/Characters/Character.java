package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;

public abstract class Character extends Entity {
    protected Direction currentDirection;
    protected Coords coords;

    public Character(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
    }
}
