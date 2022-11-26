package Game;

import DataTypes.Direction;
import Entities.Characters.Npc.Npc;
import Entities.Characters.Player;
import Entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;

import java.time.Instant;

public class Game {
    public static final long MILLI_PER_TICK = 100; //TODO: figure out reasonable value
    private static int score = 0;
    private static int timeRemaining = 0;
    private static boolean running = false;
    private static long lastTickTime;

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
    //TODO: loadGame()
    //TODO: saveGame()

    private static void gameLoop() {
        while (Game.running) {
            //TODO: check for menu inputs (save, quit, etc.)
            HashMap<Player, Direction> playerInputs = new HashMap<>();
            //TODO: check for player movement inputs, add to playerInputs
            Game.tick(playerInputs);
            Game.delayNextTick();
        }
    }

    private static void tick(HashMap<Player, Direction> playerInputs) {
        // TODO: call every function that needs to be called per tick
        Game.moveNpcs();
        // TODO: figure out how to get keyboard inputs passed to this function
        //          - will also need a way to make sure player can't move *every* tick
        //              - probably best to implement movement speed etc in player.tryMove()
        //                then this function can just blindly pass keyboards inputs etc
        //          - maybe parseInput() function?
        // TODO: if we do co-op mode, need to differentiate between player1&2 keyboard inputs
        for (Player player : playerInputs.keySet()) {
            Game.movePlayer(player, playerInputs.get(player));
        }
        Entity.processCollisions(); // Collisions must be processed after movements

        Game.updateLastTickTime();
    }

    private static void updateLastTickTime() {
        Game.lastTickTime = Instant.now().toEpochMilli();
    }

    private static void delayNextTick() {
        var now = Instant.now().toEpochMilli();
        var timeSinceLastTick = now - Game.lastTickTime;
        var timeUntilNextTick = Game.MILLI_PER_TICK - timeSinceLastTick;
        try {
            Thread.sleep(timeUntilNextTick);
        } catch (InterruptedException e) {
            // This shouldn't happen because we only have one thread
            System.out.println(e.getMessage());
        }
    }

    private static void moveNpcs() {
        ArrayList<Npc> npcs = Entity.filterEntitiesByType(
            Npc.class,
            Entity.getEntities()
        );
        // TODO: implement tryMove for each npc() + make sure they can only move
        //       a certain number of times per second based on speed
        npcs.forEach(Npc::tryMove);
    }

    private static void movePlayer(Player player, Direction direction) {
        //TODO: implement .tryMove() for player
        player.tryMove(direction);
    }

    private static void resetGame() {
        Game.timeRemaining = 0;
        Game.score = 0;
        Game.running = false;
        Entity.clearEntities();
        Tile.clearBoard();
    }
}
