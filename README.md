# BSc Comp Sci 2nd Year Group Project
# Group 18 - LiamQuest
## Run

VM args:
```bash
--module-path /path/to/javafx/javafx-sdk-19/lib 
--add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media
```
Replace module path with the path to your JavaFx lib folder. 

You should now be able to right-click `src/App/App.java` in your IDE and then 
select the `Run 'App'` option. This will run the application and start the game. 

If this is not the case, please see the **IDE Setup** section below.

## Java Version

We're using [Java 17.0.5](https://www.oracle.com/java/technologies/downloads/#java17) (link to download) because it's supposed to have a few quality of life improvements compared to 8.

## JavaFx Version

We're using the one Liam recommended because it's free to download: [JavaFx 19](https://gluonhq.com/products/javafx/)
(download the SDK for your machine)

The VM args you need to add to your IDE are:

```bash
--module-path /path/to/javafx/javafx-sdk-19/lib 
--add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media
```

Replace `/path/to/javafx/javafx-sdk-19/lib` with the path to the lib folder in your javafx installation.

I had to do a bit of manual setup as well to be able to run the JavaFx app straight from my IDE which 
I'll cover below in IDE Setup.

## IDE Setup

I only really use IntelliJ for Java so if people have advice for setup in other IDEs they can put it here.

Make sure you installed [Java 17.0.5](https://www.oracle.com/java/technologies/downloads/#java17) first.

### IntelliJ

Clone the repo, and then go to `File -> New -> Project from existing source...` and select the directory where you cloned the repo.

There is also a `Project from version control...` which I'm _assuming_ lets you skip the cloning step and create a project directly from the repo, but I've never used it so idk.

When it asks you what kind of project it is, just select Java without selecting any fancy stuff. Choose Java 17.0.5 as your project SDK/JDK.

After it's all loaded up, click `Build -> Build Project` to make sure it all compiles fine.

### JavaFx

Steps will probably be a bit different for other IDEs

I had to do the following to set up JavaFx in IntelliJ, but it's not always this much hassle, 
so you might be lucky: 
1. Go to `Run -> Edit Configurations...`
2. Add a new configuration (click the `+` button)
3. Pick the "Application" type
4. Enter "App" in the main class name section
5. Click "modify options"
6. Paste all the arguments from above into the VM Options field that pops up 
```bash
--module-path /path/to/javafx/javafx-sdk-19/lib 
--add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media
```
After doing all this, you should be able to right-click the `src/App/App.java` file and a 
`Run 'App'` option will be there. 

## Project Structure
```
├── lib
│       ... (just stuff for junit to run in github workflows)
├── src
│   ├── App
│   │   ├── App.java
│   │   ├── GameRenderer.java
│   │   ├── HighScoreTableController.java
│   │   ├── LevelSelectController.java
│   │   ├── LoadGameMenuController.java
│   │   ├── MessageOfTheDay.java
│   │   ├── PlayerProfileController.java
│   │   ├── fxml
│   │   │   ├── game.fxml
│   │   │   ├── highScoreTable.fxml
│   │   │   ├── levelSelect.fxml
│   │   │   ├── loadGameMenu.fxml
│   │   │   ├── menu.fxml
│   │   │   └── playerProfiles.fxml
│   │   └── resources
│   │       ├── anaconda.mp3
│   │       ├── brodyquest.mp3
│   │       ├── characters
│   │       │   ├── faron.png
│   │       │   ├── faron.webp
│   │       │   ├── faron_fancy.png
│   │       │   ├── liam_face.png
│   │       │   ├── liam_stick.gif
│   │       │   ├── oliver_snail.png
│   │       │   ├── stuart_bat.gif
│   │       │   └── stuart_face.png
│   │       ├── items
│   │       │   ├── bomb
│   │       │   │   ├── 0.png
│   │       │   │   ├── 1.png
│   │       │   │   ├── 2.png
│   │       │   │   ├── 3.png
│   │       │   │   └── 4.png
│   │       │   ├── clock.gif
│   │       │   ├── clock.png
│   │       │   ├── coffee.gif
│   │       │   ├── door.png
│   │       │   ├── explosion.png
│   │       │   ├── gate_BLUE.png
│   │       │   ├── gate_GREEN.png
│   │       │   ├── gate_RED.png
│   │       │   ├── gate_YELLOW.png
│   │       │   ├── key_BLUE.gif
│   │       │   ├── key_GREEN.gif
│   │       │   ├── key_RED.gif
│   │       │   ├── key_YELLOW.gif
│   │       │   ├── lever_BLUE.png
│   │       │   ├── lever_GREEN.png
│   │       │   ├── lever_RED.png
│   │       │   ├── lever_YELLOW.png
│   │       │   ├── loot_1.png
│   │       │   ├── loot_2.png
│   │       │   ├── loot_3.png
│   │       │   ├── loot_4.png
│   │       │   ├── mirror.png
│   │       │   ├── mushroom.png
│   │       │   └── star.png
│   │       ├── tiles
│   │       │   ├── blue.png
│   │       │   ├── cyan.png
│   │       │   ├── green.png
│   │       │   ├── magenta.png
│   │       │   ├── red.png
│   │       │   └── yellow.png
│   │       └── volume.png
│   ├── DataTypes
│   │   ├── AdjacentCoords.java
│   │   ├── Collision.java
│   │   ├── CollisionEvent.java
│   │   ├── Colour.java
│   │   ├── Colours.java
│   │   ├── Coords.java
│   │   ├── Direction.java
│   │   ├── Exception
│   │   │   ├── DeserialiseException.java
│   │   │   ├── ParseBoardException.java
│   │   │   └── ParseTileColourException.java
│   │   ├── GameParams.java
│   │   └── PlayerInput.java
│   ├── Entities
│   │   ├── Characters
│   │   │   ├── Character.java
│   │   │   ├── Npc
│   │   │   │   ├── FloorFollowingThief.java
│   │   │   │   ├── FlyingAssassin.java
│   │   │   │   ├── Npc.java
│   │   │   │   └── SmartThief.java
│   │   │   └── Player.java
│   │   ├── Entity.java
│   │   ├── Explosion.java
│   │   └── Items
│   │       ├── Bomb.java
│   │       ├── Collectable
│   │       │   ├── Clock.java
│   │       │   ├── Coffee.java
│   │       │   ├── Collectable.java
│   │       │   ├── Key.java
│   │       │   ├── Loot.java
│   │       │   └── Mirror.java
│   │       ├── Door.java
│   │       ├── Gate.java
│   │       └── Item.java
│   ├── Game
│   │   ├── Game.java
│   │   ├── HighScoreTable.java
│   │   ├── PlayerProfile.java
│   │   ├── Tile.java
│   │   └── files
│   │       ├── cache
│   │       │   └── currentProfile.txt
│   │       ├── highscores
│   │       ├── levels
│   │       │   ├── 0.txt
│   │       │   ├── 1.txt
│   │       │   ├── 2.txt
│   │       │   ├── 3.txt
│   │       │   ├── 4.txt
│   │       │   └── 5.txt
│   │       ├── playerProfiles
│   │       │   ├── shouldbreak.txt
│   │       │   └── test.txt
│   │       └── saveGames
│   │           └── test
│   │               ├── shouldbreak.txt
│   │               └── test\ save.txt
│   ├── Interfaces
│   │   ├── Handleable.java
│   │   ├── Renderable.java
│   │   └── Serialisable.java
│   └── Utils
│       ├── BoardLoader.java
│       ├── Deserialiser.java
│       └── GameFileHandler.java
└── test
    ├── DataTypes
    │   ├── AdjacentCoordsTest.java
    │   ├── CollisionEventTest.java
    │   └── DirectionTest.java
    ├── Entities
    │   ├── Characters
    │   │   └── Npc
    │   │       ├── FloorFollowingThiefTest.java
    │   │       └── SmartThiefTest.java
    │   └── EntityTest.java
    ├── Game
    │   ├── GameTest.java
    │   └── TileTest.java
    ├── TestCases
    │   └── Boards.java
    └── Utils
        ├── BoardLoaderTest.java
        └── GameFileHandlerTest.java
```
