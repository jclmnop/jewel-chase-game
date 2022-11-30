package App;

import DataTypes.Colours;
import DataTypes.Coords;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import Game.Game;
import Game.Tile;

import java.io.IOException;

public class GameRenderer {
    public static final double BOARD_WIDTH = 1000;
    public static final double BOARD_HEIGHT = 734;

    private static GameRenderer gameRenderer;
    @FXML
    private Text score;
    @FXML
    private Text time;
    @FXML
    private StackPane boardPane;
    @FXML
    private StackPane loseScreen;
    @FXML
    private StackPane victoryScreen;

    private GridPane tileGridPane;
    private GridPane entityGridPane;

    public GameRenderer() {
        GameRenderer.gameRenderer = this;
    }

    public void initialize() {
        this.tileGridPane = new GridPane();
        this.entityGridPane = new GridPane();
        this.boardPane.getChildren().addAll(tileGridPane, entityGridPane);
        this.tileGridPane.setAlignment(Pos.CENTER);
        this.entityGridPane.setAlignment(Pos.CENTER);
    }

    /**
     * Only one instance is used at any given time, so a static
     * reference to it is stored in this class.
     * @return The current instance of GameRenderer
     */
    public static GameRenderer getGameRenderer() {
        return GameRenderer.gameRenderer;
    }

    public static void render() {
        if (Game.isRunning()) {
            gameRenderer.renderBoard(); //TODO: only need to render board once
            gameRenderer.renderEntities();
            System.out.println("Rendered.");
        }
        gameRenderer.updateText();
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
        // Calculate size of tiles
        double tileDimensions = Math.min(
            BOARD_WIDTH / Tile.getWidth(), BOARD_WIDTH / Tile.getHeight()
        );

        for (int row = 0; row < Tile.getHeight(); row++) {
            for (int col = 0; col < Tile.getWidth(); col++) {
                this.tileGridPane.add(
                    this.renderTile(Tile.getTile(new Coords(col, row)), tileDimensions),
                    col, row //TODO: which way do x,y go here?
                );
            }
        }
    }

    private GridPane renderTile(Tile tile, double dims) {
//        dims = 50;
        GridPane tileGrid = new GridPane();
        tileGrid.gridLinesVisibleProperty().set(false);
        tileGrid.setLayoutX(dims);
        tileGrid.setLayoutY(dims);
        Colours tileColours = tile.getColours();
        dims = dims / 2;
        Rectangle t1 = new Rectangle(dims, dims);
        Rectangle t2 = new Rectangle(dims, dims);
        Rectangle t3 = new Rectangle(dims, dims);
        Rectangle t4 = new Rectangle(dims, dims);
        t1.setFill(tileColours.c1().toJfxColour());
        t2.setFill(tileColours.c2().toJfxColour());
        t3.setFill(tileColours.c3().toJfxColour());
        t4.setFill(tileColours.c4().toJfxColour());
        tileGrid.addRow(0, t1, t2);
        tileGrid.addRow(1, t3, t4);
        BorderStroke borderStroke = new BorderStroke(
            Color.BLACK,
            new BorderStrokeStyle(StrokeType.CENTERED, null, null, 1, 1, null),
            null,
            null
        );
        Border border = new Border(borderStroke);
        tileGrid.borderProperty().set(border);
        return tileGrid;
    }

    private void renderEntities() {
        // TODO:
    }

    private void updateText() {
        this.score.setText(String.format("Score: %s", Game.getScore()));
        this.time.setText(String.format("Time: %s", Game.getTimeRemaining()));
    }
}
