## JAVA VERSION

We're using [Java 17.0.5](https://www.oracle.com/java/technologies/downloads/#java17) (link to download) because it's supposed to have a few quality of life improvements compared to 8.

## IDE Setup

I only really use IntelliJ for Java so if people have advice for setup in other IDEs they can put it here.

Make sure you installed [Java 17.0.5](https://www.oracle.com/java/technologies/downloads/#java17) first.

### IntelliJ

Clone the repo, and then go to `File -> New -> Project from existing source...` and select the directory where you cloned the repo.

There is also a `Project from version control...` which I'm _assuming_ lets you skip the cloning step and create a project directly from the repo, but I've never used it so idk.

When it asks you what kind of project it is, just select Java without selecting any fancy stuff. Choose Java 17.0.5 as your project SDK/JDK.

After it's all loaded up, click `Build -> Build Project` to make sure it all compiles fine.

## Project Structure

I've organised everything into packages, which are just fancy Java folders. Game and Tile are currently just at the top level,
but I'll probably make a Utils package for Game, GameFileHandler (loading/saving) and any other static classes we need.

Fuck knows where JavaFX stuff will go. Probably all in its own package.

At the moment most Classes are just empty templates, except for the DataTypes, which are simple custom datatypes/enums etc which we need for other stuff.

```
└── src
    ├── DataTypes
    │   ├── Collision.java
    │   ├── CollisionEvent.java
    │   ├── CollisionType.java
    │   ├── Coords.java
    │   └── Direction.java
    ├── Entities
    │   ├── Characters
    │   │   ├── Character.java
    │   │   ├── FloorFollowingThief.java
    │   │   ├── FlyingAssassin.java
    │   │   ├── Player.java
    │   │   └── SmartThief.java
    │   ├── Entity.java
    │   └── Items
    │       ├── Clock.java
    │       ├── Gate.java
    │       ├── Item.java
    │       ├── Lever.java
    │       └── Loot.java
    ├── Game.java
    └── Tile.java
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

VCS + IntelliJ both have nice GUI integrations for this, so you don't have to use command line if you don't want to.

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
