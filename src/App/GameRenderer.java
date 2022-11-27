package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import Game.Game;

import java.io.IOException;

public class GameRenderer {
    private static GameRenderer gameRenderer;
    @FXML
    private Text score;
    @FXML
    private Text time;
    @FXML
    private GridPane boardGridPane;
    @FXML
    private StackPane loseScreen;
    @FXML
    private StackPane victoryScreen;

    public GameRenderer() {
        GameRenderer.gameRenderer = this;
    }

    public static GameRenderer getGameRenderer() {
        return GameRenderer.gameRenderer;
    }

    public static void render() {
        gameRenderer.renderBoard();
        gameRenderer.renderEntities();
        gameRenderer.updateText();
        System.out.println("Rendered.");
    }

    public static void renderLose() {
        gameRenderer.loseScreen.visibleProperty().set(true);
    }

    public static void renderWin() {
        gameRenderer.victoryScreen.visibleProperty().set(true);
    }

    public void saveGameButton(ActionEvent actionEvent) {};

    public void quitGameButton(ActionEvent actionEvent) throws IOException {
        Game.quitGame();
    };

    private void renderBoard() {
        // TODO:
    }

    private void renderEntities() {
        // TODO:
    }

    private void updateText() {
        this.score.setText(String.format("Score: %s", Game.getScore()));
        this.time.setText(String.format("Time: %s", Game.getTimeRemaining()));
    }
}
