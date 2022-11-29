package Entities.Characters.Npc;

import DataTypes.Coords;
import Entities.Entity;
import Entities.Items.Collectable.Collectable;
import Entities.Items.Collectable.Loot;
import Game.Tile;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SmartThiefTest {
    @BeforeEach
    public void setup() {
        Entity.clearEntities();

    }

    @Test
    public void testTryMove_1() {
        Tile.newBoard(Boards.CASE_1.TARGET_BOARD, 5, 3);
        final Coords TARGET_ITEM_COORDS = new Coords(1, 2);
        Loot target = new Loot(TARGET_ITEM_COORDS);
        Assertions.assertFalse(Collectable.getCollectables().isEmpty());
        SmartThief thief = new SmartThief(new Coords(0, 0), 1);
        int expectedTicks = 11;
        simulateTicks(thief, target.getCoords(), expectedTicks);
    }

    @Test
    public void testTryMove_2() {
        Tile.newBoard(Boards.CASE_2.TARGET_BOARD, 5, 3);
        final Coords TARGET_ITEM_COORDS = new Coords(1, 2);
        Loot target = new Loot(TARGET_ITEM_COORDS);
        Assertions.assertFalse(Collectable.getCollectables().isEmpty());
        SmartThief thief = new SmartThief(new Coords(0, 0), 1);
        int expectedTicks = 2;
        simulateTicks(thief, target.getCoords(), expectedTicks);
    }

    private static void simulateTicks(SmartThief thief, Coords targetCoords, int expectedTicks) {
        int ticks = 0;
        while (!thief.getCoords().equals(targetCoords)) {
            thief.tryMove();
            ticks++;
            if (ticks > expectedTicks) {
                System.out.println(thief.getCoords());
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedTicks, ticks);

    }
}
