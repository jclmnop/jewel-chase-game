package App;

import DataTypes.Colour;
import DataTypes.Coords;
import DataTypes.Direction;
import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import DataTypes.GameParams;
import Entities.Characters.Npc.FloorFollowingThief;
import Entities.Characters.Player;
import Utils.BoardLoader;
import Utils.GameFileHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import Game.Game;
import Game.Tile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final String MENU_FXML_PATH = "fxml/menu.fxml";
    public static final String GAME_FXML_PATH = "fxml/game.fxml";
    public static final String RESOURCES_PATH = "src/App/resources/";
    public static final String BRODYQUEST_MP3_PATH = RESOURCES_PATH + "brodyquest.mp3";
    public static final String ANACONDA_MP3_PATH = RESOURCES_PATH + "anaconda.mp3";
    public static final String[] TRACKS = {BRODYQUEST_MP3_PATH, ANACONDA_MP3_PATH};
    public static final double DEFAULT_VOLUME = 0.3; // 50%
    private static int currentTrack = 0;
    private static Stage stage;
    private static MediaPlayer musicPlayer;
    private static App app;
    @FXML
    private Text messageOfTheDay;

    //TODO: for testing only, remember to remove
    public static final String BOARD_STR = """
                                           5 3
                                           YYYY YYYY YYYY YYYY YGRG
                                           RRCR RRRR RRRR RRRR RRYY
                                           RCCY CMMC MCCM CCCC CGCG
                                           """;

    public App() {
        App.app = this;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static App getApp() {
        return App.app;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void returnToMainMenu() throws IOException {
        App.app.changeScene(MENU_FXML_PATH);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.stage = primaryStage;
        App.stage.setResizable(false);
        App.playMusic();
        this.changeScene(MENU_FXML_PATH);
        App.updateMessageOfTheDay();
    }

    /**
     * Volume can only be increased. Working as intended.
     */
    public void increaseVolume() {
        App.volume();
    }

    private static void volume() {
        App.musicPlayer.setVolume(App.musicPlayer.getVolume() + 0.1);
        System.out.println(App.musicPlayer.getVolume());
    }

    private static void playMusic() {
        App.musicPlayer = new MediaPlayer(App.loadNextTrack());
        App.musicPlayer.setVolume(DEFAULT_VOLUME);
        musicPlayer.setOnEndOfMedia(() -> {
            App.musicPlayer.dispose();
            App.musicPlayer = new MediaPlayer(App.loadNextTrack());
            App.musicPlayer.setVolume(DEFAULT_VOLUME);
            App.musicPlayer.play();
        });
        App.musicPlayer.play();
    }

    private static Media loadNextTrack() {
        Media music = new Media(new File(TRACKS[currentTrack]).toURI().toString());
        currentTrack = currentTrack + 1 % TRACKS.length;
        return music;
    }

    private static void updateMessageOfTheDay() {
        try {
            App.app.messageOfTheDay.setText(MessageOfTheDay.getMessageOfTheDay());
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            App.app.messageOfTheDay.setText("There is only pain.");
        }
    }

    /**
     * Switches to a new scene from an FXML file associated with input URL
     * @param fxmlPath Path to FXML file relative to App package,
     *            e.g. `"fxml/menu.fxml"`
     * @throws IOException if fxmlPath is invalid
     */
    public void changeScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource(fxmlPath))
        );
        Scene menuScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        App.stage.setScene(menuScene);
        App.stage.show();
    }

    public void newGame(
        ActionEvent actionEvent
    ) throws IOException, ParseBoardException, ParseTileColourException, ClassNotFoundException {
        //TODO: change scene to level select, then load selected level from there
        this.changeScene(GAME_FXML_PATH);
        App.stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        // TODO: this is just here for testing without level file menu
        GameFileHandler.loadPlayerProfile("test");
        GameParams gameParams = GameFileHandler.loadLevelFile(
            0, Game.getPlayerProfile()
        );
        //
        App.stage.getScene().setOnKeyPressed(
            (key) -> Game.registerNewMovementInput(key.getCode())
        );

        Thread gameThread = Game.startGame(gameParams);
        gameThread.setPriority(6);
    }
}
