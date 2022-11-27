package App;

import DataTypes.GameParams;
import Entities.Characters.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Game.Game;

import java.io.IOException;
import java.util.Objects;

public class App extends Application{
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final String MENU_FXML_PATH = "fxml/menu.fxml";
    public static final String GAME_FXML_PATH = "fxml/game.fxml";
    private static Stage stage;

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
        this.changeScene(MENU_FXML_PATH);
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
