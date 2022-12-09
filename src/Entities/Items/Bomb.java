package Entities.Items;

import java.util.ArrayList;
import java.util.Arrays;

import DataTypes.AdjacentCoords;
import DataTypes.Coords;
import Entities.Entity;
import Entities.Explosion;
import Entities.Characters.Npc.Npc;
import Game.Tile;

public class Bomb extends Item {

    private int state = 4000;
    private Coords[] trigZones = Tile.getNoColourAdjacentTiles(coords).toArray();
    private boolean triggered = false;

    public Bomb(Coords coords) {
        super(CollisionType.BOMB, false, coords);
    }
public Bomb(Coords coords, int state) {
    this(coords);
    this.state = state;
}
    /**
     * Checks whether bomb is waiting to be triggered or is in the process of exploding.
     */
    public void check() {
        if (!triggered) {
            detect();
        } else {
            state - Game.MILLI_PER_TICK;
            if (state == 0) {
                explode();
            }
        }
    }

    /**
     * Detects if any npcs are vertically or hozirontally next to it. If so, the bomb triggers.
     */
    private void detect() {
        final int NUM_ZONES = 4;
        int i = 0;
        while (!triggered || i < NUM_ZONES) {
            Coords zone = trigZones[i];
            if (zone != null) {
                if (!Tile.getEntitiesOfTypeByCoords(Npc.class, zone).isEmpty()) {
                    triggered = true;
                }
            }
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
            new Explosion(collisionType, blocking, zones[i]);
            spawnExplosions(Tile.getNoColourAdjacentTiles(zones[i]).toArray(), i);
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
            "%s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.state
        ) ;
    }
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        // TODO
        return null;
    }
}
