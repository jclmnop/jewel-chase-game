package Game;

import DataTypes.Coords;
import DataTypes.GameParams;
import Entities.Characters.Player;
import Entities.Entity;
import TestCases.Boards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class GameTest {
    // TODO: loads new game
    // TODO: clears all static instances when game ends
    public static final long ACCEPTABLE_EXCESS_RUN_TIME_MILLI = 100;

    @Test
    public void testGameLoopRunsAndEndsGracefully() {
        // Run for 1 second
        int runTimeSeconds = 1;
        int runTimeMilli = runTimeSeconds * 1000;
        GameParams gameParams = new GameParams(runTimeMilli, 0, true);
        Entity.clearEntities();
        Tile.newBoard(Boards.CASE_2.TARGET_BOARD, 5, 3);
        // At least one player required to stop game from ending prematurely
        Player player = new Player(new Coords(0, 0), 1);
        Assertions.assertEquals(1, Entity.getEntities().size());

        Instant start = Instant.now();
        Game.startGame(gameParams);
        Instant end = Instant.now();
        long actualRunTimeMilli = end.toEpochMilli() - start.toEpochMilli();
        System.out.println(actualRunTimeMilli);
        long expectedRunTimeMilli = runTimeSeconds * Game.MILLI_PER_SECOND;
        long unacceptableRunTimeMilli = expectedRunTimeMilli + ACCEPTABLE_EXCESS_RUN_TIME_MILLI;
        boolean runTimeLongerThanExpected = actualRunTimeMilli > expectedRunTimeMilli;
        boolean runTimeAcceptable = actualRunTimeMilli < unacceptableRunTimeMilli;
        Assertions.assertTrue(runTimeLongerThanExpected);
        Assertions.assertTrue(runTimeAcceptable);
    }
}
