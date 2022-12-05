package App;

import DataTypes.Colours;
import DataTypes.Coords;
import Entities.Entity;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import Game.Game;
import Game.Tile;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ConcurrentModificationException;

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
    private boolean isBoardRendered;

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
        double tileDimensions = Math.min(
            BOARD_WIDTH / Tile.getWidth(), BOARD_WIDTH / Tile.getHeight()
        );
        if (Game.isRunning()) {
            if (!gameRenderer.isBoardRendered) {
                gameRenderer.renderBoard(tileDimensions); //TODO: only need to render board once
            }
            gameRenderer.renderEntityGrid(tileDimensions);
//            System.out.println("Rendered.");
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

    private void renderBoard(double tileDimensions) {
        // Calculate size of tiles

        for (int row = 0; row < Tile.getHeight(); row++) {
            for (int col = 0; col < Tile.getWidth(); col++) {
                this.tileGridPane.add(
                    this.renderTile(Tile.getTile(new Coords(col, row)), tileDimensions),
                    col, row
                );

            }
        }
        this.isBoardRendered = true;
    }

    private GridPane renderTile(Tile tile, double tileDimensions) {
        GridPane tileGrid = new GridPane();
        tileGrid.setLayoutX(tileDimensions);
        tileGrid.setLayoutY(tileDimensions);
        double padding = tileDimensions / 100;
        tileDimensions -= padding * 2;
        tileGrid.setPadding(new Insets(padding));
        tileGrid.backgroundProperty().set(
            new Background(new BackgroundFill(Color.BLACK, null, null))
        );
        Colours tileColours = tile.getColours();
        double tileSegmentDimensions = tileDimensions / 2;
        ImageView t1 = new ImageView(tileColours.c1().toImage());
        t1.setFitHeight(tileSegmentDimensions);
        t1.setFitWidth(tileSegmentDimensions);
        ImageView t2 = new ImageView(tileColours.c2().toImage());
        t2.setFitHeight(tileSegmentDimensions);
        t2.setFitWidth(tileSegmentDimensions);
        ImageView t3 = new ImageView(tileColours.c3().toImage());
        t3.setFitHeight(tileSegmentDimensions);
        t3.setFitWidth(tileSegmentDimensions);
        ImageView t4 = new ImageView(tileColours.c4().toImage());
        t4.setFitHeight(tileSegmentDimensions);
        t4.setFitWidth(tileSegmentDimensions);

        tileGrid.addRow(0, t1, t2);
        tileGrid.addRow(1, t3, t4);
        return tileGrid;
    }

    private void renderEntityGrid(double tileDimensions) {
        this.entityGridPane.getChildren().clear();

        for (int row = 0; row < Tile.getHeight(); row++) {
            for (int col = 0; col < Tile.getWidth(); col++) {
                Tile currentTile = Tile.getTile(new Coords(col, row));
                this.entityGridPane.add(
                    this.renderEntities(currentTile, tileDimensions),
                    col, row
                );
            }
        }
    }

    private StackPane renderEntities(Tile tile, Double tileDimensions) {
        StackPane entityGridCell = new StackPane();
        entityGridCell.setMaxSize(tileDimensions, tileDimensions);
        entityGridCell.setMinSize(tileDimensions, tileDimensions);
        entityGridCell.setAlignment(Pos.CENTER);

        try {
            for (Entity entity : tile.getEntities()) {
                entityGridCell.getChildren().add(
                    this.renderEntity(entity, tileDimensions)
                );
            }
        } catch (ConcurrentModificationException exception) {
            System.out.println("No time for thread safety");
            return this.renderEntities(tile, tileDimensions);
        }

        return entityGridCell;
    }

    private ImageView renderEntity(Entity entity, double tileDimensions) {
        ImageView image = new ImageView(entity.toImage());
        image.setFitWidth(tileDimensions);
        image.setFitHeight(tileDimensions);
        return image;
    }

    private void updateText() {
        this.score.setText(String.format("Score: %s", Game.getScore()));
        this.time.setText(String.format("Time: %s", Game.getTimeRemaining()));
    }
}
