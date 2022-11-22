package DataTypes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DirectionTest {
    // TODO: turnLeft, turnRight etc return correct values
    @Test
    public void testTurnAround() {
        Assertions.assertEquals(Direction.DOWN, Direction.turnAround(Direction.UP));
        Assertions.assertEquals(Direction.UP, Direction.turnAround(Direction.DOWN));
        Assertions.assertEquals(Direction.LEFT, Direction.turnAround(Direction.RIGHT));
        Assertions.assertEquals(Direction.RIGHT, Direction.turnAround(Direction.LEFT));
    }

    @Test
    public void testTurnLeft() {
        Assertions.assertEquals(Direction.LEFT, Direction.turnLeft(Direction.UP));
        Assertions.assertEquals(Direction.DOWN, Direction.turnLeft(Direction.LEFT));
        Assertions.assertEquals(Direction.RIGHT, Direction.turnLeft(Direction.DOWN));
        Assertions.assertEquals(Direction.UP, Direction.turnLeft(Direction.RIGHT));
    }

    @Test
    public void testTurnRight() {
        Assertions.assertEquals(Direction.RIGHT, Direction.turnRight(Direction.UP));
        Assertions.assertEquals(Direction.DOWN, Direction.turnRight(Direction.RIGHT));
        Assertions.assertEquals(Direction.LEFT, Direction.turnRight(Direction.DOWN));
        Assertions.assertEquals(Direction.UP, Direction.turnRight(Direction.LEFT));
    }
}
