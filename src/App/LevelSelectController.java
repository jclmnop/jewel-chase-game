package App;

import DataTypes.GameParams;
import Utils.GameFileHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import Game.Game;
import Game.HighScoreTable;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the level selection menu.
 * @author Jonny
 * @version 1.0
 */
public class LevelSelectController {
    private static LevelSelectController levelSelectController;
    @FXML
    private VBox levelMenu;
    private int selectedLevel;

    public LevelSelectController() {
        LevelSelectController.levelSelectController = this;
    }

    /**
     * Load the level menu.
     */
    public void initialize() {
        this.loadLevelMenu();
        LevelSelectController.levelSelectController = this;
    }

    /**
     * @return The current instantiation of LevelSelectController
     */
    public static LevelSelectController getLevelSelectController() {
        return levelSelectController;
    }

    /**
     * @return The currently selected level.
     */
    public int getSelectedLevel() {
        return selectedLevel;
    }

    private void loadLevelMenu() {
        this.levelMenu.setFillWidth(true);
        this.levelMenu.setAlignment(Pos.TOP_CENTER);
        this.levelMenu.getChildren().clear();
        ArrayList<String> availableLevels = App.isHighScoreMenu()
            ? GameFileHandler.getAllLevels()
            : GameFileHandler.getAvailableLevels(Game.getPlayerProfile());
        for (String level : availableLevels) {
            this.addLevelButton(level);
        }
    }

    private void addLevelButton(String level) {
        Button newButton = new Button("Level " + level);
        if (App.isHighScoreMenu()) {
            newButton.setOnAction(
                (actionEvent) -> {
                    try {
                        this.loadSelectedHighScoreTable(level);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        throw new RuntimeException(
                            "I/O Error when loading high scores for level " + level
                        );
                    } catch (Exception anyOtherException) {
                        anyOtherException.printStackTrace();
                        throw new RuntimeException(
                            "Failed to load high scores. File may be corrupt."
                        );
                    }
                }
            );
        } else {
            newButton.setOnAction(
                (actionEvent) -> {
                    try {
                        this.loadSelectedLevel(level);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        throw new RuntimeException(
                            "I/O Error when loading level " + level
                        );
                    } catch (Exception anyOtherException) {
                        anyOtherException.printStackTrace();
                        throw new RuntimeException(
                            "Failed to load level. File may be corrupt."
                        );
                    }
                }
            );
        }

        this.levelMenu.getChildren().add(newButton);
        double menuWidth = this.levelMenu.getPrefWidth();
        double buttonWidth = menuWidth - 20;
        double buttonHeight = 100;
        newButton.setMinWidth(buttonWidth);
        newButton.setMinHeight(buttonHeight);
        newButton.setAlignment(Pos.CENTER);
        newButton.setTextAlignment(TextAlignment.CENTER);
        newButton.setFont(Font.font("Roboto Mono", 30));

    }

    @FXML
    private void mainMenu() throws IOException {
        App.returnToMainMenu();
    }

    private void loadSelectedLevel(String level) throws IOException {
        GameParams gameParams = GameFileHandler.loadLevelFile(
            Integer.parseInt(level), Game.getPlayerProfile()
        );
        try {
            App.getApp().newGame(gameParams);
        } catch (Exception gameStartError) {
            gameStartError.printStackTrace();
            throw new RuntimeException("Failed to start new game.");
        }
    }

    private void loadSelectedHighScoreTable(String level) throws IOException {
        this.selectedLevel = Integer.parseInt(level);
        App.getApp().changeScene(App.HIGH_SCORE_FXML_PATH);
    }
}
