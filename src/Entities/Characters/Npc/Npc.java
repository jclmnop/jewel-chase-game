package Entities.Characters.Npc;

import DataTypes.CollisionType;
import DataTypes.Coords;
import Entities.Characters.Character;

public abstract class Npc extends Character {

    public Npc(
        CollisionType collisionType,
        boolean isBlocking,
        Coords coords,
        int speed
    ) {
        super(collisionType, isBlocking, coords, speed);
    }

    public abstract void tryMove();
}
