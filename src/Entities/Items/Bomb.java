package Entities.Items;

import DataTypes.Coords;
import Entities.Characters.Character;
import Entities.Entity;
import Entities.Explosion;
import Game.Tile;
import Game.Game;
import Interfaces.Handleable;
import javafx.scene.image.Image;

/**
 * Bomb which begins to count down and explode if a character occupies any of
 * its adjacent tiles.
 *
 * @author Dillon
 * @version 1.1
 * @see Interfaces.Handleable
 * @see Entities.Items.Item
 */
public class Bomb extends Item implements Handleable {
    public static final int INITIAL_STATE = 4000;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "bomb/";
    private static final int FRAME_LENGTH_MILLI = 1000;
    private final Coords[] trigZones;
    private int timeLeftMilli;
    private boolean triggered = false;
    private Image imageFrameOne;
    private Image imageFrameTwo;
    private Image imageFrameThree;
    private Image imageFrameFour;

    /**
     * Create bomb at specified coordinates.
     * @param coords Coordinates of bomb.
     */
    public Bomb(Coords coords) {
        super(CollisionType.BOMB, true, coords);
        this.imagePath = IMAGE_PATH;
        this.trigZones = Tile.getAdjacentCoords(this.coords).toArray();
        this.timeLeftMilli = INITIAL_STATE;
    }

    /**
     * Create bomb with specified parameters.
     *
     * Used to load from a deserialised save game string.
     *
     * @param coords Coordinates of bomb.
     * @param triggered Whether the bomb has been triggered and is currently
     *                  counting down.
     * @param timeLeftMilli Time left on the bomb's counter in milliseconds.
     */
    public Bomb(Coords coords, boolean triggered, int timeLeftMilli) {
        this(coords);
        this.timeLeftMilli = timeLeftMilli;
        this.triggered = triggered;
    }

    /**
     * Checks whether bomb is waiting to be triggered or is in the process
     * of exploding and handles accordingly. .
     */
    @Override
    public void handle() {
        if (!triggered) {
            detect();
        } else {
            timeLeftMilli -= Game.MILLI_PER_TICK;
            if (timeLeftMilli <= 0) {
                explode();
            }
        }
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     * @see Interfaces.Serialisable
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.triggered,
            this.timeLeftMilli
        ) ;
    }

    /**
     * Load the image(s) and compute the correct image for the current state
     * of the bomb.
     * @return The current image.
     * @see Interfaces.Renderable
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
        } else if (this.timeLeftMilli >= INITIAL_STATE - FRAME_LENGTH_MILLI) {
            return this.imageFrameOne;
        } else if (this.timeLeftMilli >= INITIAL_STATE - 2 * FRAME_LENGTH_MILLI) {
            return this.imageFrameTwo;
        } else if (this.timeLeftMilli >= INITIAL_STATE - 3 * FRAME_LENGTH_MILLI) {
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
        this.timeLeftMilli = (int) (Game.MILLI_PER_TICK - 1);
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
            spawnExplosions(Tile.getAdjacentCoords(zones[i]).toArray(), i);
        }
    }
}
