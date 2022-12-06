package App;

import DataTypes.GameParams;
import Game.Game;
import Utils.GameFileHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;

public class LoadGameMenuController {
    private static LoadGameMenuController loadGameMenuController;

    @FXML
    private VBox saveGameMenu;

    public LoadGameMenuController() throws IOException {
        LoadGameMenuController.loadGameMenuController = this;
    }

    public void initialize() throws IOException {
        this.loadSaveFileMenu();
    }

    public void mainMenu() throws IOException {
        App.returnToMainMenu();
    }

    private void loadSaveFileMenu() throws IOException {
        this.saveGameMenu.setFillWidth(true);
        this.saveGameMenu.setAlignment(Pos.TOP_CENTER);
        this.saveGameMenu.getChildren().clear();
        ArrayList<String> availableSaveFiles =
            GameFileHandler.getAvailableSaveFiles(Game.getPlayerProfile());
        for (String save : availableSaveFiles) {
            this.addSaveFileButton(save);
        }
    }

    private void addSaveFileButton(String save) {
        Button newButton = new Button("Save: " + save);
        newButton.setOnAction(
            (actionEvent) -> {
                try {
                    loadSelectedSaveFile(save);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(
                        "I/O Error when loading save " + save
                    );
                }
            }
        );
        this.saveGameMenu.getChildren().add(newButton);

        double menuWidth = this.saveGameMenu.getPrefWidth();
        double buttonWidth = menuWidth - 20;
        double buttonHeight = 100;
        newButton.setMinWidth(buttonWidth);
        newButton.setMinHeight(buttonHeight);
        newButton.setAlignment(Pos.CENTER);
        newButton.setTextAlignment(TextAlignment.CENTER);
        newButton.setFont(Font.font("Roboto Mono", 30));

    }

    private void loadSelectedSaveFile(String level) throws IOException {
        GameParams gameParams = GameFileHandler.loadSaveFile(
            level, Game.getPlayerProfile()
        );
        App.getApp().newGame(gameParams);
    }

}
