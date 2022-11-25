package DataTypes;

import Entities.Characters.FloorFollowingThief;
import Entities.Characters.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.SmartThief;
import Entities.Entity;
import Entities.Items.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollisionEventTest {
    @Test
    public void testCollisionEvents() {
        // TODO: these tests will break when new constructor parameters are added
        //       to the classes, so keep an eye on them.
        var player = new Player();
        var smartThief = new SmartThief();
        var floorThief = new FloorFollowingThief();
        var flyingAss = new FlyingAssassin();
        var gate = new Gate();
        var clock = new Clock();
        var lever = new Lever();
        var loot = new Loot();
        var door = new Door();
        var bomb = new Bomb();

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
        Assertions.assertEquals(
            CollisionEvent.DETONATE,
            simulateCollision(bomb, player)
        );
        Assertions.assertEquals(
            CollisionEvent.DETONATE,
            simulateCollision(bomb, smartThief)
        );
        Assertions.assertEquals(
            CollisionEvent.NOTHING,
            simulateCollision(bomb, flyingAss)
        );
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
