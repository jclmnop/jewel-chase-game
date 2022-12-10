package Entities.Characters.Npc;

import DataTypes.Coords;
import Entities.Characters.Character;
import Interfaces.Handleable;

public abstract class Npc extends Character implements Handleable {

    public Npc(
        CollisionType collisionType,
        boolean isBlocking,
        Coords coords,
        int speed
    ) {
        super(collisionType, isBlocking, coords, speed);
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
