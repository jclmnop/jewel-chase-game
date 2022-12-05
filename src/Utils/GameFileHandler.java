package Utils;

import DataTypes.Exception.DeserialiseException;
import DataTypes.GameParams;
import Entities.Entity;
import Game.PlayerProfile;
import Game.Game;
import Game.Tile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
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

    public static void loadPlayerProfile(String playerName) throws IOException {
        Path profilePath = Path.of(PLAYER_PROFILES_PATH + playerName + ".txt");
        String profileFile = Files.readString(profilePath);
        PlayerProfile profile = PlayerProfile.fromString(profileFile);
        Game.setPlayerProfile(profile);
    }

    public static void savePlayerProfile(PlayerProfile playerProfile) throws IOException {
        Path profilePath = Path.of(
            PLAYER_PROFILES_PATH + playerProfile.getPlayerName() + ".txt"
        );
        Files.writeString(profilePath, playerProfile.serialise());
    }

    public static ArrayList<String> getAvailableLevels(PlayerProfile playerProfile) {
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

    public static GameParams loadLevelFile(int levelNumber, PlayerProfile playerProfile) throws IOException {
        if (levelNumber > playerProfile.getMaxLevel()) {
            throw new RuntimeException(String.format(
                "Max level for %s is %s, but selected level is %s",
                playerProfile.getPlayerName(),
                playerProfile.getMaxLevel(),
                levelNumber
            ));
        }
        Path levelFilePath = Path.of(LEVEL_FILES_PATH + levelNumber + ".txt");
        String levelFileString = Files.readString(levelFilePath);
        return GameFileHandler.loadLevelFromString(levelFileString);
    }

    public static GameParams loadSaveFile(String saveFileName, PlayerProfile playerProfile) throws IOException {
        Path saveFilePath = GameFileHandler.computeSaveGameFilePath(
            saveFileName, playerProfile
        );
        String saveFileString = Files.readString(saveFilePath);
        return GameFileHandler.loadLevelFromString(saveFileString);
    }

    public static void saveGame(String saveFileName, PlayerProfile playerProfile) throws IOException {
        // TODO build string: gameParams, blank line, board, blank line, entities
        StringBuilder saveGameStringBuilder = new StringBuilder();
        GameParams gameParams = new GameParams(
            Game.getTimeRemaining(),
            Game.getScore(),
            false,
            Game.getCurrentLevelNumber()
        );
        saveGameStringBuilder.append(gameParams.serialise()).append("\n");
        saveGameStringBuilder.append("\n");
        saveGameStringBuilder.append(Tile.serialiseBoard()).append("\n");
        for (Entity entity : Entity.getEntities()) {
            saveGameStringBuilder.append(entity.serialise()).append("\n");
        }
        Path saveFilePath = GameFileHandler.computeSaveGameFilePath(
            saveFileName, playerProfile
        );
        Files.writeString(saveFilePath, saveGameStringBuilder.toString());
    }

    private static Path computeSaveGameFilePath(
        String saveFileName,
        PlayerProfile playerProfile
    ) {
       return Path.of(String.format(
            "%s%s/%s.txt",
            SAVE_GAME_PATH,
            playerProfile.getPlayerName(),
            saveFileName
        ));
    }

    //TODO: getAvailableSaves(playerProfile)
    //TODO: getAvailableProfiles()
    //TODO: loadHighScoreTable(levelName)
    //TODO: savePlayerProfile(playerProfile)
    //TODO: newPlayerProfile(playerName)

    private static GameParams loadLevelFromString(String levelString) {
        Iterator<String> levelStringLines = levelString.lines().iterator();
        GameParams gameParams = Deserialiser.deserialiseGameParams(levelStringLines.next());
        levelStringLines.next(); // Skip blank line

        StringBuilder boardStringBuilder = new StringBuilder();
        String nextLine = levelStringLines.next();
        while (!nextLine.isBlank()) {
            boardStringBuilder.append(nextLine).append("\n");
            nextLine = levelStringLines.next();
        }

        Tile[][] board = BoardLoader.loadBoard(boardStringBuilder.toString());
        int height = board.length;
        int width = board[0].length;
        Tile.newBoard(board, width, height);

        while (levelStringLines.hasNext()) {
            nextLine = levelStringLines.next();
            if (!nextLine.isBlank()) {
                Deserialiser.deserialiseObject(nextLine);
            }
        }

        return gameParams;
    }
}
