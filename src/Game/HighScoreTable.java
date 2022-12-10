package Game;

import Interfaces.Serialisable;

import java.util.ArrayList;

/**
 * Holds up to 10 high score entries for a specific level.
 *
 * @author Jonny
 * @version 1.0
 */
public class HighScoreTable implements Serialisable {
    private ArrayList<HighScoreEntry> highScoreEntries;
    private final int levelNumber;

    /**
     * Load an existing high score table.
     * @param levelNumber Level number.
     * @param highScoreEntries Entries in the high score table.
     */
    public HighScoreTable(int levelNumber, ArrayList<HighScoreEntry> highScoreEntries) {
        this.levelNumber = levelNumber;
        this.highScoreEntries = highScoreEntries;
        this.sortAndPrune();
    }

    /**
     * Create a new high score table with no entries.
     * @param levelNumber Level number.
     */
    public HighScoreTable(int levelNumber) {
        this(levelNumber, new ArrayList<>());
    }

    /**
     * Contains information for an entry into the high score table.
     *
     * @param playerName Name of the player.
     * @param score Score by the player for the level associated with the high
     *              score table.
     */
    public record HighScoreEntry(String playerName, int score) implements Serialisable {
        /**
         * Used to sort an arraylist by scores in descending order.
         * @param otherEntry Other high score entry to compare to.
         * @return -1 If this is larger than the other entry. +1 if this is
         *         smaller. 0 if they're equal.
         */
        public int compare(HighScoreEntry otherEntry) {
            return Integer.compare(otherEntry.score(), this.score);
        }

        /**
         * Serialises the Object into a String.
         *
         * @return Serialised string for `this` Object.
         */
        @Override
        public String serialise() {
            return String.format(
                "%s %s",
                this.playerName,
                this.score
            );
        }
    }

    /**
     * @return Level number.
     */
    public int getLevelNumber() {
        return this.levelNumber;
    }

    /**
     * @return High score entries.
     */
    public ArrayList<HighScoreEntry> getHighScores() {
        return this.highScoreEntries;
    }

    /**
     * Add a new entry to high score table if it's eligible.
     * @param newEntry High score entry to be added.
     */
    public void addNewEntry(HighScoreEntry newEntry) {
        this.highScoreEntries.add(newEntry);
        this.sortAndPrune();
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.levelNumber).append("\n");
        for (HighScoreEntry entry : this.highScoreEntries) {
            builder.append(entry.serialise()).append("\n");
        }
        return builder.toString();
    }

    private void sortAndPrune() {
        this.highScoreEntries.sort(HighScoreEntry::compare);
        if (this.highScoreEntries.size() > 10) {
            this.highScoreEntries = new ArrayList<>(this.highScoreEntries.subList(0, 10));
        }
    }
}
