package Entities.Items;

import DataTypes.Coords;
import Entities.Entity;
import Entities.Explosion;
import Entities.Characters.Npc.Npc;
import Game.Tile;
import Game.Game;
import javafx.scene.image.Image;

public class Bomb extends Item {
    public static final int INITIAL_STATE = 4000;
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "bomb";
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
    public void check() {
        if (!triggered) {
            detect();
        } else {
            state -= Game.MILLI_PER_TICK;
            if (state == 0) {
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
            this.image = new Image(this.imagePath + ".png");
            this.imageFrameOne = new Image(this.imagePath + "_1.png");
            this.imageFrameTwo = new Image(this.imagePath + "_2.png");
            this.imageFrameThree = new Image(this.imagePath + "_3.png");
            this.imageFrameFour = new Image(this.imagePath + "_4.png");
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
                if (!Tile.getEntitiesOfTypeByCoords(Npc.class, zone).isEmpty()) {
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
        // Spawns bombs in lines up, down, left and right from the bomb.
        for (int i = 0; i < 4; i++) {
            spawnExplosions(trigZones, i);
        }
        Entity.removeEntity(this);
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
