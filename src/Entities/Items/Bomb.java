package Entities.Items;

import DataTypes.Coords;
import Entities.Characters.Character;
import Entities.Entity;
import Entities.Explosion;
import Game.Tile;
import Game.Game;
import Interfaces.Handleable;
import javafx.scene.image.Image;

public class Bomb extends Item implements Handleable {
    public static final int INITIAL_STATE = 4000;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "bomb/";
    private static final int FRAME_LENGTH_MILLI = 1000;
    private final Coords[] trigZones;
    private int state;
    private boolean triggered = false;
    private Image imageFrameOne;
    private Image imageFrameTwo;
    private Image imageFrameThree;
    private Image imageFrameFour;

    public Bomb(Coords coords) {
        super(CollisionType.BOMB, true, coords);
        this.imagePath = IMAGE_PATH;
        this.trigZones = Tile.getNoColourAdjacentTiles(this.coords).toArray();
        this.state = INITIAL_STATE;
    }

    public Bomb(Coords coords, boolean triggered, int state) {
        this(coords);
        this.state = state;
        this.triggered = triggered;
    }
    /**
     * Checks whether bomb is waiting to be triggered or is in the process of exploding.
     */
    @Override
    public void handle() {
        if (!triggered) {
            detect();
        } else {
            state -= Game.MILLI_PER_TICK;
            if (state <= 0) {
                explode();
            }
        }
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.triggered,
            this.state
        ) ;
    }

    /**
     * Load the image(s) and compute the correct image for the current frame.
     * @return The current image.
     */
    @Override
    public Image toImage() {
        if (this.image == null) {
            this.image = new Image(this.imagePath + "0.png");
            this.imageFrameOne = new Image(this.imagePath + "1.png");
            this.imageFrameTwo = new Image(this.imagePath + "2.png");
            this.imageFrameThree = new Image(this.imagePath + "3.png");
            this.imageFrameFour = new Image(this.imagePath + "4.png");
        }
        // Only god can judge me.
        if (!this.triggered) {
            return this.image;
        } else if (this.state >= INITIAL_STATE - FRAME_LENGTH_MILLI) {
            return this.imageFrameOne;
        } else if (this.state >= INITIAL_STATE - 2 * FRAME_LENGTH_MILLI) {
            return this.imageFrameTwo;
        } else if (this.state >= INITIAL_STATE - 3 * FRAME_LENGTH_MILLI) {
            return this.imageFrameThree;
        } else {
            return this.imageFrameFour;
        }
    }

    /**
     * Detects if any npcs are vertically or hozirontally next to it. If so, the bomb triggers.
     */
    private void detect() {
        final int NUM_ZONES = 4;
        int i = 0;
        while (!triggered && i < NUM_ZONES) {
            Coords zone = trigZones[i];
            if (zone != null) {
                if (!Tile.getEntitiesOfTypeByCoords(Character.class, zone).isEmpty()) {
                    triggered = true;
                }
            }
            i++;
        }
    }

    /**
     * Spawns explosions and removes the bomb from the board.
     */
    public void explode() {
        // Spawns explosions in lines up, down, left and right from the bomb.
        for (int i = 0; i < 4; i++) {
            spawnExplosions(trigZones, i);
        }
        new Explosion(this.coords);
        Entity.removeEntity(this);
    }

    /**
     * Used to trigger chain reaction from another bomb's explosion
     */
    public void chainReaction() {
        this.state = (int) (Game.MILLI_PER_TICK - 1);
        this.triggered = true;
    }

    /**
     * Recursive method spawns explosions in a line from the bomb.
     * @param zones Array of coords adjacent to explosion zone
     * @param i The index of the current direction.
     */
    private void spawnExplosions(Coords[] zones, int i) {
        // If null, the edge of the board has been reached so no more spawns.
        if (zones[i] != null) {
            new Explosion(zones[i]);
            spawnExplosions(Tile.getNoColourAdjacentTiles(zones[i]).toArray(), i);
        }
    }
}
