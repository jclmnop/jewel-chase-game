package App;

import Utils.GameFileHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the player profile menu.
 * @author Jonny
 * @version 1.1
 * @see Game.PlayerProfile
 * @see GameFileHandler
 */
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

    /**
     * Set up the player profile controller and the load the menu.
     */
    public void initialize() {
        this.loadProfileMenu();
    }

    /**
     * Return to main menu.
     * @throws IOException If there's an I/O error while switching scenes.
     */
    public void mainMenu() throws IOException {
        App.returnToMainMenu();
    }

    /**
     * Create a new profile.
     * @throws IOException If there's an I/O error while saving the new profile.
     */
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
            try {
                GameFileHandler.deletePlayerProfile(playerName);
                this.loadProfileMenu();
                this.deleteMode.setSelected(false);
            } catch (Exception anyException) {
                anyException.printStackTrace();
                this.loadProfileMenu();
                throw new RuntimeException("Failed to delete file.");
            }
        } else {
            try {
                GameFileHandler.loadPlayerProfile(playerName);
                App.returnToMainMenu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                throw new RuntimeException("IO Error when trying to load profile.");
            } catch (Exception anyOtherException) {
                anyOtherException.printStackTrace();
                throw new RuntimeException(
                    "Failed to load player profile. File may be corrupt."
                );
            }
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
