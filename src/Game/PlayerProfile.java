package Game;

import Interfaces.Serialisable;

/**
 * Stores information about a player.
 * @author Jonny
 * @version 1.0
 */
public class PlayerProfile implements Serialisable {
    private final String playerName;
    private int maxLevel;

    /**
     * Create a new player profile, max level will be set to 0.
     * @param playerName Name of the new profile.
     */
    public PlayerProfile(String playerName) {
        this.playerName = playerName;
        this.maxLevel = 0;
        //TODO: GameFileHandler.savePlayerProfile(this)
    }

    /**
     * Create a player profile with a custom max level. Used for loading
     * an existing player profile.
     * @param playerName Name of the profile.
     * @param maxLevel Maximum level the player has reached so far.
     */
    public PlayerProfile(String playerName, int maxLevel) {
        this(playerName);
        this.maxLevel = maxLevel;
    }

    /**
     * Name of player who the profile belongs to.
     * @return Name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Maximum level unlocked by the player so far.
     * @return Maximum level unlocked by player.
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Increment the maximum level that the player has unlocked.
     */
    public void increaseMaxLevel() {
        this.maxLevel++;
    }

    /**
     * Serialise player profile into string format, so it can be saved to a .txt
     * file.
     * @return Serialised player profile string.
     */
    @Override
    public String serialise() {
        return this.playerName + " " + this.maxLevel;
    }
}
