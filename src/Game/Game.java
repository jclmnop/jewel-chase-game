package Game;

import DataTypes.Direction;
import Entities.Characters.Npc.Npc;
import Entities.Characters.Player;
import Entities.Entity;

import java.util.ArrayList;

public class Game {
    public static final int TICKS_PER_SECOND = 100; //TODO: figure out reasonable value
    private static int score = 0;
    private static int timeRemaining = 0;
    private static boolean running = false;

    private Game() {};

    public static int getScore() {
        return Game.score;
    }

    public static int getTimeRemaining() {
        return Game.timeRemaining;
    }

    public static boolean isRunning() {
        return Game.running;
    }

    /**
     * Adjusts the current score
     * @param scoreChange Positive int to increase score, negative int to reduce
     *                    score.
     */
    public static void adjustScore(int scoreChange) {
        Game.score += scoreChange;
    }

    /**
     * Adjusts timeRemaining
     * @param timeChange Positive int to increase time, negative int to reduce
     *                   timeRemaining.
     */
    public static void adjustTime(int timeChange) {
        Game.timeRemaining += timeChange;
    }

    //TODO: -- public --
    //TODO: startGame()
    //TODO: endGame()
    //TODO: quit()
    //TODO: win()
    //TODO: lose()

    //TODO: -- maybe private idk --
    //TODO: loadGame()
    //TODO: saveGame()

    //TODO: -- private --
    //TODO: tick()
    private static void moveNpcs() {
        ArrayList<Npc> npcs = Entity.filterEntitiesByType(
            Npc.class,
            Entity.getEntities()
        );
        npcs.forEach(Npc::tryMove);
    }

    private static void movePlayer(Player player, Direction direction) {
        //TODO: implement player.tryMove(direction)
    }

    private static void resetGame() {
        Game.timeRemaining = 0;
        Game.score = 0;
        Game.running = false;
        Entity.clearEntities();
        Tile.clearBoard();
    }
}
