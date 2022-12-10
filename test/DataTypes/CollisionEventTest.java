package DataTypes;

import Entities.Characters.Npc.FloorFollowingThief;
import Entities.Characters.Npc.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.Npc.SmartThief;
import Entities.Entity;
import Entities.Items.*;
import Entities.Items.Collectable.Clock;
import Entities.Items.Collectable.Key;
import Entities.Items.Collectable.Loot;
import Game.Tile;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollisionEventTest {
    @Test
    public void testCollisionEvents() {
        // TODO: these tests will break when new constructor parameters are added
        //       to the classes, so keep an eye on them.
        Tile.newBoard(Boards.CASE_2.TARGET_BOARD, 5, 3);
        var player = new Player(new Coords(0, 0), 1);
        var smartThief = new SmartThief(new Coords(0, 0), 1);
        var floorThief = new FloorFollowingThief(new Coords(0, 0), 1);
        var flyingAss = new FlyingAssassin(new Coords(0, 0), 1);
        var gate = new Gate(new Coords(0, 0));
        var clock = new Clock(new Coords(0, 0));
        var lever = new Key(new Coords(0, 0));
        var loot = new Loot(new Coords(0, 0));
        var door = new Door(new Coords(0, 0));
        var bomb = new Bomb(new Coords(0, 0));

        Assertions.assertEquals(
            CollisionEvent.LOOT_STOLEN,
            simulateCollision(smartThief, loot)
        );
        Assertions.assertEquals(
            CollisionEvent.LOOT_STOLEN,
            simulateCollision(floorThief, loot)
        );
        Assertions.assertEquals(
            CollisionEvent.LOOT_COLLECTED,
            simulateCollision(player, loot)
        );
        Assertions.assertEquals(
            CollisionEvent.NOTHING,
            simulateCollision(flyingAss, loot)
        );
        Assertions.assertEquals(
            CollisionEvent.CLOCK_COLLECTED,
            simulateCollision(player, clock)
        );
        Assertions.assertEquals(
            CollisionEvent.CLOCK_STOLEN,
            simulateCollision(smartThief, clock)
        );
        Assertions.assertEquals(
            CollisionEvent.CLOCK_STOLEN,
            simulateCollision(floorThief, clock)
        );
        Assertions.assertEquals(
            CollisionEvent.NOTHING,
            simulateCollision(flyingAss, clock)
        );
        Assertions.assertEquals(
            CollisionEvent.LEVER_TRIGGERED,
            simulateCollision(player, lever)
        );
        Assertions.assertEquals(
            CollisionEvent.LEVER_TRIGGERED,
            simulateCollision(smartThief, lever)
        );
        Assertions.assertEquals(
            CollisionEvent.LEVER_TRIGGERED,
            simulateCollision(floorThief, lever)
        );
        Assertions.assertEquals(
            CollisionEvent.LOSE,
            simulateCollision(floorThief, door)
        );
        Assertions.assertEquals(
            CollisionEvent.LOSE,
            simulateCollision(smartThief, door)
        );
        Assertions.assertEquals(
            CollisionEvent.WIN,
            simulateCollision(player, door)
        );
        Assertions.assertEquals(
            CollisionEvent.NOTHING,
            simulateCollision(flyingAss, door)
        );
//        Assertions.assertEquals(
//            CollisionEvent.DETONATE,
//            simulateCollision(bomb, player)
//        );
//        Assertions.assertEquals(
//            CollisionEvent.DETONATE,
//            simulateCollision(bomb, smartThief)
//        );
//        Assertions.assertEquals(
//            CollisionEvent.NOTHING,
//            simulateCollision(bomb, flyingAss)
//        );
        Assertions.assertEquals(
            CollisionEvent.ASSASSINATION,
            simulateCollision(player, flyingAss)
        );
        Assertions.assertEquals(
            CollisionEvent.ASSASSINATION,
            simulateCollision(smartThief, flyingAss)
        );
        Assertions.assertEquals(
            CollisionEvent.ASSASSINATION,
            simulateCollision(floorThief, flyingAss)
        );
        Assertions.assertEquals(
            CollisionEvent.DOUBLE_ASSASSINATION,
            simulateCollision(flyingAss, flyingAss)
        );
        Assertions.assertEquals(
            CollisionEvent.NOTHING,
            simulateCollision(player, smartThief)
        );

    }

    private static CollisionEvent simulateCollision(Entity entityOne, Entity entityTwo) {
        Collision collisionOne = new Collision(new Coords(0, 0), entityOne, entityTwo);
        Collision collisionTwo = new Collision(new Coords(0, 0), entityTwo, entityOne);

        CollisionEvent collisionEventOne = CollisionEvent.calculateCollisionEvent(collisionOne);
        CollisionEvent collisionEventTwo = CollisionEvent.calculateCollisionEvent(collisionTwo);

        Assertions.assertEquals(collisionEventOne, collisionEventTwo);

        return collisionEventOne;
    }
}
