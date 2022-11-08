package Entities.Characters;

import DataTypes.CollisionType;

public class Player extends Character {
    public Player() {
        super(CollisionType.PLAYER, true);
    }
}
