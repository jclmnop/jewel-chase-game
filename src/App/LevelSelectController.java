package App;

import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import DataTypes.GameParams;
import Utils.GameFileHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import Game.Game;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;

public class LevelSelectController {
    private static LevelSelectController levelSelectController;
    @FXML
    private VBox levelMenu;

    public LevelSelectController() {
        LevelSelectController.levelSelectController = this;
    }

    public void initialize() {
        this.loadLevelMenu();
    }

    private void loadLevelMenu() {
        this.levelMenu.setFillWidth(true);
        this.levelMenu.setAlignment(Pos.TOP_CENTER);
        this.levelMenu.getChildren().clear();
        ArrayList<String> availableLevels =
            GameFileHandler.getAvailableLevels(Game.getPlayerProfile());
        for (String level : availableLevels) {
            this.addLevelButton(level);
        }
    }

    private void addLevelButton(String level) {
        Button newButton = new Button("Level " + level);
        newButton.setOnAction(
            (actionEvent) -> {
                try {
                    loadSelectedLevel(level);
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

}
