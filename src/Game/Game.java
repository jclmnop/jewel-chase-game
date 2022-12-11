package Game;

import App.App;
import App.GameRenderer;
import DataTypes.Direction;
import DataTypes.GameParams;
import DataTypes.PlayerInput;
import Entities.Characters.Player;
import Entities.Entity;
import Interfaces.Handleable;
import Utils.GameFileHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Handles the game loop and all associated logic while ensuring everything runs
 * in the correct order.
 *
 * Cannot be instantiated.
 *
 * @author Jonny
 * @version 1.6
 */
public class Game {
    /** Milliseconds per game tick. */
    public static final long MILLI_PER_TICK = 100; //TODO: figure out reasonable value
    /** Milliseconds in a second. */
    public static final long MILLI_PER_SECOND = 1000;
    /** Maximum number of concurrent players that can be in the game. */
    public static final int MAX_PLAYERS = 2;
    private static final HashMap<KeyCode, Direction> PLAYER1_MOVEMENT_KEYS = new HashMap<>();
    private static final HashMap<KeyCode, Direction> PLAYER2_MOVEMENT_KEYS = new HashMap<>();
    private static final HashMap<KeyCode, PlayerInput> playerMovementKeys = new HashMap<>();
    private static final HashMap<Player, Direction> currentMovementInputs = new HashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static int score = 0;
    private static int timeRemainingMilli = 0;
    private static int currentLevelNumber;
    private static boolean running = false;
    private static boolean paused = false;
    private static boolean headless = false;
    private static PlayerProfile playerProfile;
    private static Timeline tickTimeline;

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

    /**
     * @return The current score.
     */
    public static int getScore() {
        return Game.score;
    }

    /**
     * @return The current time remaining in seconds.
     */
    public static int getTimeRemaining() {
        double preciseTimeSeconds =
            (double) Game.timeRemainingMilli / (double) MILLI_PER_SECOND;
        if (preciseTimeSeconds < 0) {
            return 0;
        } else {
            return (int) Math.ceil(preciseTimeSeconds);
        }
    }

    /**
     * @return Whether the game is currently running.
     */
    public static boolean isRunning() {
        return Game.running;
    }

    /**
     * @return Number of the level currently being played.
     */
    public static int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    /**
     * Adjusts the current score
     * @param scoreChange Positive int to increase score, negative int to reduce
     *                    score.
     */
    public static void adjustScore(int scoreChange) {
        Game.score += scoreChange;
        if (Game.score < 0) {
            Game.score = 0;
        }
    }

    /**
     * Adjusts timeRemaining.
     * @param timeChange Positive int to increase time, negative int to reduce
     *                   timeRemaining. Value is in seconds.
     */
    public static void adjustTime(int timeChange) {
        Game.timeRemainingMilli += timeChange * MILLI_PER_SECOND;
        if (Game.timeRemainingMilli < 0) {
            Game.timeRemainingMilli = 0;
        }
    }

    /**
     * Set whether the game loop is paused.
     * Pausing the game loop temporarily freezes execution until it's unpaused.
     * @param paused true to pause, false to unpause.
     */
    public static void setPaused(boolean paused) {
        Game.paused = paused;
    }

    /**
     * Toggle whether the game is paused
     */
    public static void togglePaused() {
        Game.paused = !Game.paused;
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
        HashMap<KeyCode, PlayerInput> clonedPlayerMovementKeys =
            (HashMap<KeyCode, PlayerInput>) Game.playerMovementKeys.clone();
        clonedPlayerMovementKeys.forEach(
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
            // Update the movement input for this player if it hasn't
            // been updated during this tick
            Game.currentMovementInputs.computeIfAbsent(
                playerInput.player(), k -> playerInput.direction()
            );
        } else if (keyPressed == KeyCode.SPACE) {
            Game.togglePaused();
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
     */
    public static void startGame(GameParams gameParams) {
        Game.score = gameParams.startScore();
        Game.timeRemainingMilli = gameParams.startTime();
        Game.headless = gameParams.isHeadless();
        Game.currentLevelNumber = gameParams.levelNumber();
        Game.running = true;

        if (Game.headless) {
            try {
                Game.headlessGameLoop();
            } catch (InterruptedException e) {
                throw new RuntimeException("Headless loop interrupted");
            }
        } else {
            Game.tickTimeline = new Timeline(
                new KeyFrame(
                    Duration.millis(MILLI_PER_TICK),
                    event -> Game.tick()
                )
            );
            Game.tickTimeline.setCycleCount(Animation.INDEFINITE);
            Game.tickTimeline.play();
        }

    }

    /**
     * Win the game, calculate final score, unlock next level if necessary and
     * add score to high score table for this level if it's high enough.
     *
     * Also triggers the victory screen.
     */
    public static void win() {
        Game.endGame();
        Game.adjustScore(+Game.getTimeRemaining());
        if (!Game.headless) {
            GameRenderer.renderWin();
        }
        if (Game.currentLevelNumber == Game.playerProfile.getMaxLevel()) {
            Game.playerProfile.increaseMaxLevel();
            try {
                GameFileHandler.savePlayerProfile(Game.playerProfile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                throw new RuntimeException("Error updating player profile");
            }
        }
        try {
            Game.addHighScore();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("Error updating high score table");
        }
    }

    /**
     * Lose the game and show the loss screen.
     */
    public static void lose() {
        Game.endGame();
        if (!Game.headless) {
            GameRenderer.renderLose();
        }
    }

    /**
     * Stop the game and return to main menu.
     * @throws IOException If there's an I/O error when returning to main menu.
     */
    public static void quitGame() throws IOException {
        Game.endGame();
        Game.resetGame();
        App.returnToMainMenu();
    }

    private static void endGame() {
        Game.running = false;
    }

    private static void headlessGameLoop() throws InterruptedException {
        while (Game.running) {
            Game.tick();
            Thread.sleep(Game.MILLI_PER_TICK);
        }
    }

    private static void tick() {
        if (Game.isRunning()) {
            if (!Game.paused) {
                Game.handleEvents();
            }
            if (!Game.headless) {
                GameRenderer.render();
            }
            Game.checkForLoss();
        } else {
            System.out.println("gameLoop ended.");
            if (headless) {
                Game.resetGame();
            }
            Game.tickTimeline.stop();
        }
    }

    private static void handleEvents() {
        Game.handleEntities();
        Game.processPlayerInputs();
        // Collisions must be processed *after* movements
        Entity.processCollisions();
        Game.timerCountdown();
    }

    private static void processPlayerInputs() {
        for (Player player : Game.currentMovementInputs.keySet()) {
            Game.movePlayer(player, currentMovementInputs.get(player));
        }
        Game.currentMovementInputs.clear();
    }

    private static void timerCountdown() {
        Game.timeRemainingMilli -= MILLI_PER_TICK;
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

    private static void handleEntities() {
        ArrayList<Handleable> handleableEntities =
            Handleable.getHandleable(Entity.getEntities());
        handleableEntities.forEach(Handleable::handle);
    }

    private static void movePlayer(Player player, Direction direction) {
        //TODO: implement .tryMove() for player
        player.tryMove(direction);
    }

    private static void resetGame() {
        Game.timeRemainingMilli = 0;
        Game.score = 0;
        Game.headless = false;
        Game.currentLevelNumber = 0;
        Game.currentMovementInputs.clear();
        Game.playerMovementKeys.clear();
        Entity.clearEntities();
        Tile.clearBoard();
    }

    private static void addHighScore() throws IOException {
        HighScoreTable highScoreTable =
            GameFileHandler.loadHighScoreTable(Game.currentLevelNumber);
        highScoreTable.addNewEntry(new HighScoreTable.HighScoreEntry(
            Game.playerProfile.getPlayerName(),
            Game.score
        ));
        GameFileHandler.updateHighScoreTable(highScoreTable);
    }
}
