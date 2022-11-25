package Entities.Characters.Npc;

import DataTypes.CollisionType;
import Entities.Characters.Character;

public abstract class Npc extends Character {

    public Npc(CollisionType collisionType, boolean isBlocking) {
        super(collisionType, isBlocking);
    }

    public abstract void tryMove();
}
