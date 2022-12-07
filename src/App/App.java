package App;

import DataTypes.Exception.ParseBoardException;
import DataTypes.Exception.ParseTileColourException;
import DataTypes.GameParams;
import Utils.GameFileHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
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
    public static final String LOAD_SAVE_FILE_FXML_PATH = "fxml/loadGameMenu.fxml";
    public static final String LEVEL_SELECT_FXML_PATH = "fxml/levelSelect.fxml";
    public static final String PLAYER_PROFILES_FXML_PATH = "fxml/playerProfiles.fxml";
    public static final String RESOURCES_PATH = "src/App/resources/";
    public static final String BRODYQUEST_MP3_PATH = RESOURCES_PATH + "brodyquest.mp3";
    public static final String ANACONDA_MP3_PATH = RESOURCES_PATH + "anaconda.mp3";
    public static final String[] TRACKS = {BRODYQUEST_MP3_PATH, ANACONDA_MP3_PATH};
    public static final double DEFAULT_VOLUME = 0.3; // 30%
    private static int currentAudioTrack = 0;
    private static Stage stage;
    private static MediaPlayer musicPlayer;
    private static App app;
    @FXML
    private Text messageOfTheDay;
    @FXML
    private Text currentPlayerProfile;

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
        App.updateMessageOfTheDay();
    }

    public static void showAlert(String textToDisplay) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(textToDisplay);
        alert.show();
    }

    /**
     * Display prompt text within a next dialog box and wait for user to provide
     * input.
     *
     * String must be smaller than character limit and contain only alphanumeric
     * characters otherwise user will be prompted to retry input.
     * @param promptText Text to display to user.
     * @param characterLimit Character limit for input string.
     * @return The input string, or null if user cancels the action.
     */
    public static String getUserInput(String promptText, int characterLimit) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText(promptText);
        inputDialog.showAndWait();
        String inputResult = inputDialog.getResult();
        if (inputResult == null) {
            return null;
        }else if (inputResult.isBlank()) {
            promptText = "Must enter something...";
            return getUserInput(promptText, characterLimit);
        } else if (inputResult.length() > characterLimit) {
            promptText = String.format(
                "Can't be longer than %s characters.", characterLimit
            );
            return getUserInput(promptText, characterLimit);
        } else if (App.containsIllegalChars(inputResult)) {
            promptText = "You some sort of hacker or something?";
            return getUserInput(promptText, characterLimit);
        }
        return inputResult;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(App::errorPopup);
        App.stage = primaryStage;
        App.stage.setResizable(false);
        App.playMusic();
        this.changeScene(MENU_FXML_PATH);
        // Load cached profile if it exists
    }

    public void initialize() throws IOException {
        GameFileHandler.loadPlayerProfile("");
        this.updateCurrentPlayerProfie();
        App.updateMessageOfTheDay();
    }

    /**
     * Display error message popup to user. Auto-hides after losing focus.
     * @param currentThread Thread from which exception was thrown.
     * @param exception Exception to be displayed.
     */
    public static void errorPopup(Thread currentThread, Throwable exception) {
        final double FONT_SIZE = 20;
        final double Y_OFFSET = 28;
        while (exception.getCause() != null) {
            exception = exception.getCause();
        }
        String errorMsg =
            exception.getMessage() == null
                ? "Something went wrong"
                : exception.getMessage();
        Popup errorNotification = new Popup();
        errorNotification.setAutoHide(true);

        Label errorLabel = new Label(errorMsg);
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setStyle("-fx-background-color: black; -fx-text-fill: red;");
        errorLabel.setFont(new Font("Roboto Mono", FONT_SIZE));
        errorLabel.setMinWidth(App.stage.getWidth());

        errorNotification.getContent().add(errorLabel);
        double xPos = App.stage.getX();
        double yPos = App.stage.getY() + Y_OFFSET;
        errorNotification.show(App.stage, xPos, yPos);

    }

    /**
     * Volume can only be increased. Working as intended.
     */
    public void increaseVolume() {
        App.volume();
    }

    /**
     * Switches to a new scene from an FXML file associated with input URL
     * @param fxmlPath Path to FXML file relative to App package,
     *                 e.g. `"fxml/menu.fxml"`
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
        GameParams gameParams
    ) throws IOException, ParseBoardException, ParseTileColourException {
        //TODO: change scene to level select, then load selected level from there
        this.changeScene(GAME_FXML_PATH);
        App.stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        App.stage.getScene().setOnKeyPressed(
            (key) -> {
                Game.getLock().writeLock().lock();
                Game.registerNewMovementInput(key.getCode());
                Game.getLock().writeLock().unlock();
            }
        );

        Thread gameThread = Game.startGame(gameParams);
        gameThread.setPriority(6);
    }

    public void loadSaveFile() throws IOException {
        if (Game.getPlayerProfile() == null) {
            App.showAlert("Select a profile first.");
        } else {
            this.changeScene(LOAD_SAVE_FILE_FXML_PATH);
        }
    }

    public void levelSelect() throws IOException {
        if (Game.getPlayerProfile() == null) {
            App.showAlert("Select a profile first.");
        } else {
            this.changeScene(LEVEL_SELECT_FXML_PATH);
        }
    }

    public void selectProfile() throws IOException {
        this.changeScene(PLAYER_PROFILES_FXML_PATH);
    }

    private static boolean containsIllegalChars(String userInput) {
        for (char ch : userInput.toCharArray()) {
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
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
        Media music = new Media(new File(TRACKS[currentAudioTrack]).toURI().toString());
        currentAudioTrack = currentAudioTrack + 1 % TRACKS.length;
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

    private void updateCurrentPlayerProfie() {
        if (Game.getPlayerProfile() != null) {
            this.currentPlayerProfile.setText(
                String.format(
                    "Player: %s",
                    Game.getPlayerProfile().getPlayerName()
                )
            );
        }
    }
}
