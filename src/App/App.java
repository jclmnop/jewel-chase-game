package App;

import DataTypes.GameParams;
import Entities.Characters.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import Game.Game;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final String MENU_FXML_PATH = "fxml/menu.fxml";
    public static final String GAME_FXML_PATH = "fxml/game.fxml";
    public static final String BRODYQUEST_MP3_PATH = "src/App/resources/brodyquest.mp3";
    public static final String ANACONDA_MP3_PATH = "src/App/resources/anaconda.mp3";
    public static final String[] TRACKS = {BRODYQUEST_MP3_PATH, ANACONDA_MP3_PATH};
    public static final double DEFAULT_VOLUME = 0.3; // 50%
    private static int currentTrack = 0;
    private static Stage stage;
    private static MediaPlayer musicPlayer;

    public App() {
        Game.setApp(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.stage = primaryStage;
        App.stage.setResizable(false);
        App.playMusic();
        this.changeScene(MENU_FXML_PATH);
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

    public void newGame(ActionEvent actionEvent) throws IOException, InterruptedException {
        //TODO: change scene to level select, then load selected level from there
        this.changeScene(GAME_FXML_PATH);
        Thread.sleep(100);
        Player player = new Player(); // TODO: this is just here for testing
        Thread gameThread = Game.startGame(new GameParams(10, 0));
//        gameThread.join();
    }
}
