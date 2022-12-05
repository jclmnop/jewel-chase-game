package DataTypes;

import Interfaces.Serialisable;

/**
 * Represents parameters either for a new game or for loading a previously
 * saved game.
 *
 * Contains a headless option for running without JavaFx.
 *
 * @author Jonny
 * @version 1.2
 * @param isHeadless Whether to start in headless mode.
 * @param levelNumber Number of the level being loaded.
 * @param startScore Starting score if loading from save game.
 * @param startTime Start time if loading from level/save file.
 */
public record GameParams(int startTime, int startScore, boolean isHeadless, int levelNumber) implements Serialisable {


    /**
     * Create a GameParams object with isHeadless set to false and a custom
     * starting score. Intended for loading from saved games.
     * @param startTime Start time for timer.
     * @param startScore Start score, usually retrieved from save game file.
     */
    public GameParams(int startTime, int startScore, int levelNumber) {
        this(startTime, startScore, false, levelNumber);
    }


    /**
     * @param startTime Start time for timer.
     * @param startScore Start score, usually retrieved from save game file.
     * @param isHeadless Whether to start in headless mode without JavaFx
     */
    public GameParams(int startTime, int startScore, boolean isHeadless) {
        this(startTime, startScore, isHeadless, 0);
    }


    /**
     * Create a GameParams object with score set to zero and
     * isHeadless set to false. Intended for loading new games using the
     * JavaFx App.
     * @param startTime Start time for timer.
     */
    public GameParams(int startTime) {
        this(startTime, 0, false, 0);
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
            this.startTime,
            this.startScore
        );
    }
}
