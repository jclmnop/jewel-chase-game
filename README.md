## JAVA VERSION

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

### SceneBuilder

I've been using [SceneBuilder](https://gluonhq.com/products/scene-builder/) to generate and modify the FXML files, it's much easier than trying
to do it all with code. 

If you want to improve the general look and feel of the JavaFX shit I've done so far you can do 
it in SceneBuilder and as long as you don't touch anything in the `code` section it shouldn't affect functionality. 

You can also add CSS ids to elements in scene builder and then have a .CSS file to style everything so if anyone 
is any good with CSS they could have a go at that. 

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
After doing all this, you should be able to right click the `src/App/App.java` file and a 
`Run 'App'` option will be there. 

Use this to Run the app and make sure it actually starts up. 

## Project Structure

I've organised everything into packages, which are just fancy Java folders. Game and Tile were at the top level,
but I've moved them into a Game package. GameFileHandler (loading/saving), Deserialiser (to turn text into objects) and any other static classes we need
will go in Utils.

JavaFX stuff is all in `src/App`. I should probably move the `src/App/resources` folder into the top level at some point.

The current JavaFX App is nowhere near finished, it's currently just a semi-working
proof of concept which shows the game loop etc all works properly. 

```
├── lib
│       ... (just stuff for junit)
├── src
│   ├── App
│   │   ├── App.java
│   │   ├── GameRenderer.java
│   │   ├── fxml
│   │   │   ├── game.fxml
│   │   │   └── menu.fxml
│   │   └── resources
│   │       ├── anaconda.mp3
│   │       ├── brodyquest.mp3
│   │       └── volume.png
│   ├── DataTypes
│   │   ├── AdjacentCoords.java
│   │   ├── Collision.java
│   │   ├── CollisionEvent.java
│   │   ├── CollisionType.java
│   │   ├── Colour.java
│   │   ├── Colours.java
│   │   ├── Coords.java
│   │   ├── Direction.java
│   │   ├── Exception
│   │   │   ├── ParseBoardException.java
│   │   │   └── ParseTileColourException.java
│   │   └── GameParams.java
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
│   │   └── Items
│   │       ├── Bomb.java
│   │       ├── Collectable
│   │       │   ├── Clock.java
│   │       │   ├── Collectable.java
│   │       │   ├── Lever.java
│   │       │   └── Loot.java
│   │       ├── Door.java
│   │       ├── Gate.java
│   │       └── Item.java
│   ├── Game
│   │   ├── Game.java
│   │   └── Tile.java
│   ├── Interfaces
│   │   └── Serialisable.java
│   └── Utils
│       ├── BoardLoader.java
│       └── Deserialiser.java
└── test
    ├── DataTypes
    │   ├── CollisionEventTest.java
    │   └── DirectionTest.java
    ├── Entities
    │   └── EntityTest.java
    ├── Game
    │   ├── GameTest.java
    │   └── TileTest.java
    ├── TestCases
    │   └── Boards.java
    └── Utils
        └── BoardLoaderTest.java


```

## Contributing

_Ideally_, when making any changes we'll make new branches. So if me and one other person are working on implementing a feature to save/load the game we'd be working on
a branch called something like `feat-save-load`, and when we think it's ready to merge with the `master` branch we'd open a PR (pull request). Then I can set it up
so pull requests automatically merge if everything compiles, and reject it otherwise.

Creating a new branch locally is easy:

```bash
git checkout -b new-branch-name
```

The above command creates a new branch (that's what the `-b` is for) and checks out to it (which just means it switches you over to that new branch). By default it
makes this branch from your current branch, but you can add another name after `new-branch-name` to specify a different branch to make it from.

VS Code + IntelliJ both have nice GUI integrations for this, so you don't have to use command line if you don't want to.

Using the `checkout` command without the `-b` flag will switch you to the branch-name you pass if it exists:

```bash
git checkout master
```

So what about pushing your local branch to the repo?

First, switch back to the new branch:

```bash
git checkout new-branch-name
```

Then, push it with the `-u` arg, followed by `origin new-branch-name`

```bash
git push -u origin new-branch-name
```

You only need to add `-u origin new-branch-name` to the command when you push your local branch for the first time. You're just
telling git to create a new branch with `new-branch-name` in online repo (`origin` is the remote repo on github), and to "track"
that online branch for your local branch.

Subsequent pushes can be done as usual:

```bash
git push
```

Your IDE will probably tell you which branch you're on, but you can also check with `git branch`.

### Working on a remote branch

If you want to pull someone else's branch from the github repo so you can work on it locally, just do the following

```bash
git fetch
git switch branch-name
```

Now you can make changes locally and `git push` them. Obviously if you and someone else are pushing changes to the same branch you can end up with some merge conflicts etc.

## Pull Requests

So you've pushed a branch to the remote repo, and can now see it on github if you select the "branches" dropdown menu.

Making a PR is easy af. Just select your branch on github and a little thing will pop up at the top saying your branch is ahead of `master` by however many commits,
and it will give you a button to create a pull request. Click that shit, add any info you need to the PR, and submit it. If it passes the automated tests (compiles etc)
then you'll be able to click the merge PR button. After successfully merging it will ask if you want to delete the branch, this will only delete the online version
of the branch and your local one will remain untouched. If someone else is working on the branch, and there's more work to do, then don't delete it. But if it's a quick
branch you made to push some minor changes and you don't think you'll use it again, delete it so there isn't as much clutter.

Obviously if there are conflicts it's a bit more of a ballache but we'll cross that bridge when we get to it.

## Other Stuff

`8====D~~`
