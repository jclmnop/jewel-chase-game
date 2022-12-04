package Game;

import App.App;
import App.GameRenderer;
import DataTypes.Direction;
import DataTypes.GameParams;
import DataTypes.PlayerInput;
import Entities.Characters.Npc.Npc;
import Entities.Characters.Player;
import Entities.Entity;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.time.Instant;
import java.util.Timer;


public class Game {
    //TODO: public static startGame(GameParams gameParams)
    //TODO: public/private static quit()
    //TODO: public/private static loadGame()
    //TODO: public/private static saveGame()

    public static final long MILLI_PER_TICK = 100; //TODO: figure out reasonable value
    public static final long MILLI_PER_SECOND = 1000;
    public static final int MAX_PLAYERS = 2;
    private static final HashMap<KeyCode, Direction> PLAYER1_MOVEMENT_KEYS = new HashMap<>();
    private static final HashMap<KeyCode, Direction> PLAYER2_MOVEMENT_KEYS = new HashMap<>();
    private static final HashMap<KeyCode, PlayerInput> playerMovementKeys = new HashMap<>();
    private static final HashMap<Player, Direction> currentMovementInputs = new HashMap<>();
    private static int score = 0;
    private static int timeRemaining = 0;
    private static boolean running = false;
    private static boolean headless = false;
    private static long lastTickTime = 0;
    private static long lastCountdownTime = 0;
    private static PlayerProfile playerProfile;

    static {
        Game.PLAYER1_MOVEMENT_KEYS.put(KeyCode.UP, Direction.UP);
        Game.PLAYER1_MOVEMENT_KEYS.put(KeyCode.DOWN, Direction.DOWN);
        Game.PLAYER1_MOVEMENT_KEYS.put(KeyCode.LEFT, Direction.LEFT);
        Game.PLAYER1_MOVEMENT_KEYS.put(KeyCode.RIGHT, Direction.RIGHT);
        Game.PLAYER2_MOVEMENT_KEYS.put(KeyCode.W, Direction.UP);
        Game.PLAYER2_MOVEMENT_KEYS.put(KeyCode.S, Direction.DOWN);
        Game.PLAYER2_MOVEMENT_KEYS.put(KeyCode.A, Direction.LEFT);
        Game.PLAYER2_MOVEMENT_KEYS.put(KeyCode.D, Direction.RIGHT);
    }

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

    /**
     * Get the current player profile that's being used to load levels and
     * save high scores.
     * @return Current player profile.
     */
    public static PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    /**
     * Set the current player profile which will be used to load levels and
     * save high scores.
     * @param playerProfile Profile to use.
     */
    public static void setPlayerProfile(PlayerProfile playerProfile) {
        Game.playerProfile = playerProfile;
    }

    /**
     * Add a new player to the game and assign movement keys.
     * @param player Player to be added to the game.
     */
    public static void addPlayer(Player player) {
        int KEYS_PER_PLAYER = 4;
        // Only add new player if maximum number of players hasn't been reached.
        if (playerMovementKeys.size() < MAX_PLAYERS * KEYS_PER_PLAYER) {
            HashMap<KeyCode, Direction> movementKeys =
                playerMovementKeys.isEmpty()
                    ? PLAYER1_MOVEMENT_KEYS
                    : PLAYER2_MOVEMENT_KEYS;
            movementKeys.forEach(
                (k, d) -> playerMovementKeys.put(k, new PlayerInput(player, d))
            );
        }
    }

    /**
     * Remove a player from the game and de-assign movement keys.
     * @param player Player to be removed from the game.
     */
    public static void removePlayer(Player player) {
        playerMovementKeys.forEach(
            (key, playerInput) -> {
                if (playerInput.player() == player) {
                    playerMovementKeys.remove(key);
                }
            }
        );
    }

    /**
     * Check if a keyboard key is associated with a player, and if it is then
     * update the most recent movement input for that player.
     * @param keyPressed The keycode for the key which has been pressed.
     */
    public static void registerNewMovementInput(KeyCode keyPressed) {
        PlayerInput playerInput = Game.playerMovementKeys.get(keyPressed);
        if (playerInput != null) {
            // Update the most recent movement input for this player
            Game.currentMovementInputs.put(playerInput.player(), playerInput.direction());
        }
    }

    /**
     * Runs initialises the game loop and runs it on a separate thread, this
     * allows us to monitor for player inputs on other threads.
     *
     * If headless mode is enabled in gameParams then no methods or classes
     * related to JavaFx will be used. This is primarily intended for test
     * purposes.
     *
     * @param gameParams Parameters to initialise the Game with
     * @return Handle for game loop thread
     */
    public static Thread startGame(GameParams gameParams) {
        // TODO: This should take path to level/save file instead,
        //       then call load method and get GameParams from that
        //       (load method will also ensure board etc is all set up)
        Game.score = gameParams.startScore();
        Game.timeRemaining = gameParams.startTime();
        Game.headless = gameParams.isHeadless();
        Thread gameLoopThread = new Thread(Game::gameLoop);
        gameLoopThread.start();
        return gameLoopThread;
    }

    public static void win() {
        Game.endGame();
        if (!Game.headless) {
            GameRenderer.renderWin();
        }
        // TODO: save highscore
        // TODO: update playerProfile?
    }

    public static void lose() {
        Game.endGame();
        if (!Game.headless) {
            GameRenderer.renderLose();
        }

        // TODO: i think the spec says to save highscore when player loses but
        //       that makes no sense to me? if we confirm it's in the spec though
        //       probably best to implement it anyway
    }

    public static void quitGame() throws IOException {
        Game.endGame();
        App.returnToMainMenu();
    }

    private static void endGame() {
        Game.running = false;
    }

    private static void gameLoop() {
        Game.running = true;
        long now = Instant.now().toEpochMilli();
        Game.lastCountdownTime = now;
        Game.lastTickTime = now;
        System.out.println("gameLoop started.");
        while (Game.isRunning()) {
            //TODO: check for player movement inputs, add to playerInputs
            Game.tick();
            if (!Game.headless) {
                Platform.runLater(GameRenderer::render);
            }
        }
        System.out.println("gameLoop ended.");
        Game.resetGame();
    }

    private static void tick() {
        // TODO: call every function that needs to be called per tick
        Game.moveNpcs();
        // TODO: figure out how to get keyboard inputs passed to this function
        //          - will also need a way to make sure player can't move *every* tick
        //              - probably best to implement movement speed etc in player.tryMove()
        //                then this function can just blindly player inputs etc
        //          - maybe getInputs() and/or parseInput() methods?
        // TODO: for co-op power-up item, need to differentiate between player1&2 keyboard inputs
        Game.processPlayerInputs();

        // Collisions must be processed *after* movements
        Entity.processCollisions();

        // Update lastTickTime, so we can delay until MILLI_PER_TICK has passed
        Game.updateLastTickTime();
        Game.delayNextTick();

        // Countdown the timer if MILLI_PER_SECOND has passed since last timer
        // decrement
        Game.timerCountdown();

        // Check if time has reached zero or all players are dead
        Game.checkForLoss();

//        System.out.println("Ticked.");
    }

    private static void processPlayerInputs() {
        for (Player player : Game.currentMovementInputs.keySet()) {
            Game.movePlayer(player, currentMovementInputs.get(player));
            Game.currentMovementInputs.remove(player);
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

    private static void checkForLoss() {
        boolean noPlayersLeft = Entity.filterEntitiesByType(
            Player.class,
            Entity.getEntities()
        ).isEmpty();

        if (Game.getTimeRemaining() <= 0 || noPlayersLeft) {
            Game.lose();
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
        Game.headless = false;
        Game.currentMovementInputs.clear();
        Game.playerMovementKeys.clear();
        Entity.clearEntities();
        Tile.clearBoard();
    }
}
