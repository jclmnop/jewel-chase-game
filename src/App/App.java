package App;

import DataTypes.GameParams;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Game.Game;

import java.io.IOException;
import java.util.Objects;

public class App extends Application{
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.stage = primaryStage;
        App.stage.setResizable(false);
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("fxml/menu.fxml"))
        );
        Scene menuScene = new Scene(root);
        App.stage.setScene(menuScene);
        App.stage.show();
    }
}
