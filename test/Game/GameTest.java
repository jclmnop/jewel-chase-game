package Game;

import DataTypes.GameParams;
import Entities.Characters.Player;
import Entities.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class GameTest {
    // TODO: loads new game
    // TODO: clears all static instances when game ends
    public static final long ACCEPTABLE_EXCESS_RUN_TIME_MILLI = 100;

    @Test
    public void testGameLoopRunsAndEndsGracefully() throws InterruptedException {
        // Run for 1 second
        int runTimeSeconds = 1;
        GameParams gameParams = new GameParams(runTimeSeconds, 0);
        Entity.clearEntities();
        // At least one player required to stop game from ending prematurely
        Player player = new Player();
        Assertions.assertEquals(1, Entity.getEntities().size());

        Instant start = Instant.now();
        Thread gameThread = Game.startGame(gameParams);
        gameThread.join();
        Instant end = Instant.now();
        long actualRunTimeMilli = end.toEpochMilli() - start.toEpochMilli();
        long expectedRunTimeMilli = runTimeSeconds * Game.MILLI_PER_SECOND;
        long unacceptableRunTimeMilli = expectedRunTimeMilli + ACCEPTABLE_EXCESS_RUN_TIME_MILLI;
        boolean runTimeLongerThanExpected = actualRunTimeMilli > expectedRunTimeMilli;
        boolean runTimeAcceptable = actualRunTimeMilli < unacceptableRunTimeMilli;
        Assertions.assertTrue(runTimeLongerThanExpected);
        Assertions.assertTrue(runTimeAcceptable);
    }
}
