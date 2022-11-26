package Game;

import DataTypes.Direction;
import DataTypes.GameParams;
import Entities.Characters.Npc.Npc;
import Entities.Characters.Player;
import Entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;

import java.time.Instant;

public class Game {
    //TODO: public static startGame(GameParams gameParams)
    //TODO: public/private static quit()
    //TODO: public/private static loadGame()
    //TODO: public/private static saveGame()

    public static final long MILLI_PER_TICK = 100; //TODO: figure out reasonable value
    public static final long MILLI_PER_SECOND = 1000;
    private static int score = 0;
    private static int timeRemaining = 0;
    private static boolean running = false;
    private static long lastTickTime = 0;
    private static long lastCountdownTime = 0;

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
        if (Game.timeRemaining < 0) {
            Game.timeRemaining = 0;
        }
    }

    public static void startGame(GameParams gameParams) {
        // TODO: This should take path to level/save file instead,
        //       then call load method and get GameParams from that
        //       (load method will also ensure board etc is all set up)
        Game.score = gameParams.startScore();
        Game.timeRemaining = gameParams.startTime();
        Game.gameLoop();
    }

    public static void win() {
        Game.endGame();
        // TODO: save highscore
        // TODO: update playerProfile?
        // TODO: victory screen
    }

    public static void lose() {
        Game.endGame();
        // TODO: lose screen
        // TODO: i think the spec says to save highscore when player loses but
        //       that makes no sense to me? if we confirm it's in the spec though
        //       probably best to implement it anyway
    }


    private static void endGame() {
        Game.running = false;
    }

    private static void gameLoop() {
        while (Game.isRunning()) {
            //TODO: check for menu inputs (save, quit, etc.)
            HashMap<Player, Direction> playerInputs = new HashMap<>();
            //TODO: check for player movement inputs, add to playerInputs
            Game.tick(playerInputs);
            if (Game.getTimeRemaining() <= 0) {
                Game.lose();
            }
        }
    }

    private static void tick(HashMap<Player, Direction> playerInputs) {
        // TODO: call every function that needs to be called per tick
        Game.moveNpcs();
        // TODO: figure out how to get keyboard inputs passed to this function
        //          - will also need a way to make sure player can't move *every* tick
        //              - probably best to implement movement speed etc in player.tryMove()
        //                then this function can just blindly player inputs etc
        //          - maybe getInputs() and/or parseInput() methods?
        // TODO: for co-op power-up item, need to differentiate between player1&2 keyboard inputs
        Game.processPlayerInputs(playerInputs);

        // Collisions must be processed *after* movements
        Entity.processCollisions();

        // Update lastTickTime, so we can delay until MILLI_PER_TICK has passed
        Game.updateLastTickTime();
        Game.delayNextTick();

        // Countdown the timer if MILLI_PER_SECOND has passed since last timer
        // decrement
        Game.timerCountdown();
    }

    private static void processPlayerInputs(HashMap<Player, Direction> playerInputs) {
        for (Player player : playerInputs.keySet()) {
            Game.movePlayer(player, playerInputs.get(player));
        }
    }

    private static void updateLastTickTime() {
        Game.lastTickTime = Instant.now().toEpochMilli();
    }

    private static void delayNextTick() {
        var now = Instant.now().toEpochMilli();
        var timeSinceLastTick = now - Game.lastTickTime;
        var timeUntilNextTick = MILLI_PER_TICK - timeSinceLastTick;
        try {
            Thread.sleep(timeUntilNextTick);
        } catch (InterruptedException e) {
            // This shouldn't happen because we only have one thread
            System.out.println(e.getMessage());
        }
    }

    private static void timerCountdown() {
        long now = Instant.now().toEpochMilli();
        long millisSinceLastCountdown = now - Game.lastCountdownTime;
        if (millisSinceLastCountdown >= MILLI_PER_SECOND) {
            Game.adjustTime(-1);
            Game.lastCountdownTime = now;
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
