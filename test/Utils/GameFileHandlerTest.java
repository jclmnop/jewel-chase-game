package Utils;

import DataTypes.Coords;
import DataTypes.Exception.DeserialiseException;
import DataTypes.GameParams;
import Entities.Characters.Npc.FloorFollowingThief;
import Entities.Characters.Npc.SmartThief;
import Entities.Characters.Player;
import Entities.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Game.Game;
import Game.HighScoreTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GameFileHandlerTest {
    public static final String PLAYER_NAME = "test";
    public static final String TEST_SAVE_NAME = "test save";

    @Test @BeforeEach
    public void testLoadPlayerProfile() throws IOException {
        Entity.clearEntities();
        GameFileHandler.loadPlayerProfile(PLAYER_NAME);
        Assertions.assertEquals(PLAYER_NAME, Game.getPlayerProfile().getPlayerName());
    }

    @Test
    public void testLoadLevelNoExceptions() throws IOException {
        Assertions.assertEquals(PLAYER_NAME, Game.getPlayerProfile().getPlayerName());
        ArrayList<String> levels = GameFileHandler.getAvailableLevels(Game.getPlayerProfile());

        Assertions.assertEquals(
            Game.getPlayerProfile().getMaxLevel(),
            Integer.parseInt(levels.get(levels.size()-1))
        );

        try {
            GameParams gameParams = GameFileHandler.loadLevelFile(
                Integer.parseInt(levels.get(0)),
                Game.getPlayerProfile()
            );
            Assertions.assertEquals(0, gameParams.levelNumber());
            Assertions.assertEquals(100000, gameParams.startTime());
            Assertions.assertEquals(0, gameParams.startScore());
        } catch (DeserialiseException deserialiseException) {
            System.out.println(deserialiseException.getMessage());
            Exception originalException = deserialiseException.getOriginalException();
            if (originalException.getMessage() == null) {
                System.out.println(originalException);
            } else {
                System.out.println(originalException.getMessage());
            }
            throw deserialiseException;
        }

        // Three entities are loaded
//        Assertions.assertEquals(6, Entity.getEntities().size());
        // Entities have correct coordinates and appear in same order as file
        Assertions.assertEquals(new Coords(0, 0), Entity.getEntities().get(0).getCoords());
        Assertions.assertEquals(new Coords(5, 5), Entity.getEntities().get(1).getCoords());
        Assertions.assertEquals(new Coords(3, 4), Entity.getEntities().get(2).getCoords());
        // Entities have correct types
        Assertions.assertTrue(Entity.getEntities().get(0) instanceof Player);
        Assertions.assertTrue(Entity.getEntities().get(1) instanceof SmartThief);
        Assertions.assertTrue(Entity.getEntities().get(2) instanceof FloorFollowingThief);

        GameFileHandler.saveGame(TEST_SAVE_NAME, Game.getPlayerProfile());
    }

    @Test
    public void testSaveAndLoad() throws IOException {
        GameFileHandler.loadLevelFile(0, Game.getPlayerProfile());
        ArrayList<Entity> entities = (ArrayList<Entity>) Entity.getEntities().clone();
        GameFileHandler.saveGame(TEST_SAVE_NAME, Game.getPlayerProfile());
        Entity.clearEntities();
        GameFileHandler.loadSaveFile(TEST_SAVE_NAME, Game.getPlayerProfile());

        for (int i = 0; i < entities.size(); i++) {
            Assertions.assertEquals(
                entities.get(i).serialise(),
                Entity.getEntities().get(i).serialise()
            );
        }

        var saveFiles = GameFileHandler.getAvailableSaveFiles(
            Game.getPlayerProfile()
        );
        var profileFiles = GameFileHandler.getAvailableProfiles();

        Assertions.assertTrue(saveFiles.contains(TEST_SAVE_NAME));
        Assertions.assertTrue(profileFiles.contains(PLAYER_NAME));
    }

    @Test
    public void testCantLoadLevelHigherThanMax() {
        Assertions.assertThrows(
            RuntimeException.class,
            () -> {
                GameFileHandler.loadLevelFile(
                    Game.getPlayerProfile().getMaxLevel() + 1, Game.getPlayerProfile()
                );
            }
        );
    }

    @Test
    public void testLoadSaveHighScoreTable() throws IOException {
        if (!Files.exists(Path.of(GameFileHandler.HIGH_SCORES_PATH))) {
            new File(GameFileHandler.HIGH_SCORES_PATH).mkdir();
        }
        HighScoreTable highScoreTable = GameFileHandler.loadHighScoreTable(0);
        String oldHighScoreTableStr = highScoreTable.serialise();
        var newEntry = new HighScoreTable.HighScoreEntry("poop", 10000000);
        highScoreTable.addNewEntry(newEntry);
        GameFileHandler.updateHighScoreTable(highScoreTable);
        highScoreTable = GameFileHandler.loadHighScoreTable(0);
        Assertions.assertTrue(highScoreTable.getHighScores().contains(newEntry));
        HighScoreTable oldHighScoreTable = Deserialiser.deserialiseHighScoreTable(oldHighScoreTableStr);
        GameFileHandler.updateHighScoreTable(oldHighScoreTable);
    }
}
