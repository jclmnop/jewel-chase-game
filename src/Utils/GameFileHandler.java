package Utils;

import Game.PlayerProfile;
import Game.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for handling game files such as saves, levels, profiles, etc.
 * @author Jonny
 * @version 1.0
 */
public class GameFileHandler {
    public static final String GAME_FILES_PATH = "src/Game/files/";
    public static final String SAVE_GAME_PATH = GAME_FILES_PATH + "saveGames/";
    public static final String LEVEL_FILES_PATH = GAME_FILES_PATH + "levels/";
    public static final String HIGH_SCORES_PATH = GAME_FILES_PATH + "highscores/";
    public static final String PLAYER_PROFILES_PATH = GAME_FILES_PATH + "playerProfiles/";

    private GameFileHandler() {}

    // TODO: move to unit tests
    public static void main(String[] args) throws IOException {
        GameFileHandler.loadPlayerProfile("test");
        var levels = GameFileHandler.getAvailableLevels(Game.getPlayerProfile());
        System.out.println(levels);
    }

    public static void loadPlayerProfile(String playerName) throws IOException {
        Path profilePath = Path.of(PLAYER_PROFILES_PATH + playerName + ".txt");
        String profileFile = Files.readString(profilePath);
        System.out.println(profileFile);
        PlayerProfile profile = PlayerProfile.fromString(profileFile);
        Game.setPlayerProfile(profile);
    }

    public static ArrayList<String> getAvailableLevels(PlayerProfile playerProfile) throws RuntimeException {
        int playerMaxLevel = playerProfile.getMaxLevel();
        File[] levelFiles = new File(LEVEL_FILES_PATH).listFiles();

        if (levelFiles == null) {
            throw new RuntimeException(
                "COULD NOT LOAD LEVEL FILES FROM " + LEVEL_FILES_PATH
            );
        }

        ArrayList<String> levelFileNames = Stream.of(levelFiles)
            .filter(File::isFile)
            .map(File::getName)
            .collect(Collectors.toCollection(ArrayList::new));

        if (levelFileNames.isEmpty()) {
            throw new RuntimeException("NO LEVEL FILES IN " + LEVEL_FILES_PATH);
        }

        return levelFileNames.stream()
            .sorted()
            .map(f -> f.split("\\.")[0])
            .filter(f -> Integer.parseInt(f) <= playerMaxLevel)
            .collect(Collectors.toCollection(ArrayList::new));
    }
    //TODO: getAvailableSaves(playerProfile)
    //TODO: getAvailableProfiles()
    //TODO: loadLevelFile(levelName)
    //TODO: loadSaveFile(saveFileName, playerProfile)
    //TODO: saveGame(saveFileName, playerProfile)
    //TODO: loadHighScoreTable(levelName)
    //TODO: savePlayerProfile(playerProfile)
    //TODO: newPlayerProfile(playerName)
}
