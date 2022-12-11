package App;

import Game.HighScoreTable;
import Utils.GameFileHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

/**
 * Controller class for the high score table.
 *
 * @author Jonny
 * @version 1.0
 * @see App
 * @see LevelSelectController
 * @see HighScoreTable
 */
public class HighScoreTableController {
    @FXML
    private VBox highScores;
    @FXML
    private Text selectedLevelText;

    public HighScoreTableController() {}

    /**
     * Load the high score table.
     */
    public void initialize() throws IOException {
        this.loadHighScoreTable();
        this.highScores.setSpacing(30);
    }

    @FXML
    private void levelSelectMenu() throws IOException {
        App.getApp().changeScene(App.LEVEL_SELECT_FXML_PATH);
    }

    private void loadHighScoreTable() throws IOException {
        int selectedLevel = LevelSelectController.getLevelSelectController().getSelectedLevel();
        this.selectedLevelText.setText("Level: " + selectedLevel);
        HighScoreTable highScoreTable =
            GameFileHandler.loadHighScoreTable(selectedLevel);
        for (
            HighScoreTable.HighScoreEntry highScoreEntry
            : highScoreTable.getHighScores()
        ) {
            this.addHighScoreEntry(highScoreEntry);
        }
    }

    private void addHighScoreEntry(HighScoreTable.HighScoreEntry highScoreEntry) {
        HBox highScoreEntryBox = new HBox();
        highScoreEntryBox.setMaxWidth(this.highScores.getPrefWidth());
        Text playerName = new Text(highScoreEntry.playerName());
        playerName.setTextAlignment(TextAlignment.LEFT);
        playerName.setFont(Font.font("Roboto Mono", 30));
        Text score = new Text(String.format("%s", highScoreEntry.score()));
        score.setTextAlignment(TextAlignment.RIGHT);
        score.setFont(Font.font("Roboto Mono", 30));

        Region spacing = new Region();
        spacing.setPrefWidth(highScoreEntryBox.getWidth());
        HBox.setHgrow(spacing, Priority.ALWAYS);

        highScoreEntryBox.getChildren().add(playerName);
        highScoreEntryBox.getChildren().add(spacing);
        highScoreEntryBox.getChildren().add(score);
        highScoreEntryBox.setBackground(Background.fill(Color.AQUA));

        this.highScores.getChildren().add(highScoreEntryBox);
    }
}
