package Entities.Characters.Npc;

import DataTypes.Colour;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;
import Game.Tile;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FloorFollowingThiefTest {
    @BeforeEach
    public void setup() {
        Entity.clearEntities();
    }

    @Test
    public void testTryMove_1() {
        Tile.newBoard(Boards.CASE_1.TARGET_BOARD, 5, 3);
        FloorFollowingThief thief = new FloorFollowingThief(
            new Coords(0, 0),
            1,
            Colour.YELLOW,
            Direction.UP
        );
        final Coords[] EXPECTED_PATH = {
            new Coords(1, 0),
            new Coords(2, 0),
            new Coords(3, 0),
            new Coords(4, 0),
            new Coords(4, 1),
            new Coords(4, 0),
            new Coords(3, 0),
            new Coords(2, 0),
            new Coords(1, 0),
            new Coords(0, 0),
            new Coords(1, 0)
        };
        simulateTicks(thief, EXPECTED_PATH);
    }

    private static void simulateTicks(
        FloorFollowingThief thief,
        Coords[] expectedPath
    ) {
        for (Coords nextCoord : expectedPath) {
            thief.handle();
            Assertions.assertEquals(nextCoord, thief.getCoords());
        }
    }
}
