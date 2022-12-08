package App;

import Game.Game;
import Utils.GameFileHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerProfileController {
    public static final int PLAYER_NAME_CHAR_LIMIT = 20;
    private static PlayerProfileController playerProfileController;
    @FXML
    private Button newProfileButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private VBox profileMenu;
    @FXML
    private RadioButton deleteMode;

    public PlayerProfileController() {
        PlayerProfileController.playerProfileController = this;
    }

    public void initialize() {
        this.loadProfileMenu();
    }

    public void mainMenu() throws IOException {
        App.returnToMainMenu();
    }

    public void newProfile() throws IOException {
        String newProfileName = App.getUserInput(
            "Enter name for new profile",
            PLAYER_NAME_CHAR_LIMIT
        );
        if (newProfileName != null) {
            GameFileHandler.newPlayerProfile(newProfileName);
            this.loadProfileMenu();
        }
    }

    private void profileOnClick(String playerName) throws IOException {
        if (deleteMode.isSelected()) {
            GameFileHandler.deletePlayerProfile(playerName);
            this.loadProfileMenu();
            this.deleteMode.setSelected(false);
        } else {
            GameFileHandler.loadPlayerProfile(playerName);
            App.returnToMainMenu();
        }
    }

    private void loadProfileMenu() {
        this.profileMenu.setFillWidth(true);
        this.profileMenu.setAlignment(Pos.TOP_CENTER);
        this.profileMenu.getChildren().clear();
        ArrayList<String> availableLevels =
            GameFileHandler.getAvailableProfiles();
        for (String playerName : availableLevels) {
            this.addProfileButton(playerName);
        }
    }

    private void addProfileButton(String playerName) {
        Button newButton = new Button(playerName);
        newButton.setOnAction(
            (actionEvent) -> {
                try {
                    this.profileOnClick(playerName);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(
                        "I/O Error when loading/deleting profile: " + playerName
                    );
                }
            }
        );
        this.profileMenu.getChildren().add(newButton);

        double menuWidth = this.profileMenu.getPrefWidth();
        double buttonWidth = menuWidth - 20;
        double buttonHeight = 100;
        newButton.setMinWidth(buttonWidth);
        newButton.setMinHeight(buttonHeight);
        newButton.setAlignment(Pos.CENTER);
        newButton.setTextAlignment(TextAlignment.CENTER);
        newButton.setFont(Font.font("Roboto Mono", 30));
    }
}
