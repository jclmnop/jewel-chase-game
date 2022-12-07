package Entities.Items;

import java.util.ArrayList;
import java.util.Arrays;

import DataTypes.AdjacentCoords;
import DataTypes.Coords;
import Entities.Entity;
import Entities.Characters.Npc.Npc;
import Game.Tile;

public class Bomb extends Item {

    private int state = 4;
    private Coords[] trigZones = Tile.getNoColourAdjacentTiles(coords).toArray();
    private boolean triggered = false;

    public Bomb(Coords coords) {
        super(CollisionType.BOMB, false, coords);
    }

    /**
     * Checks whether bomb is waiting to be triggered or is in the process of exploding.
     */
    public void check() {
        if (!triggered) {
            detect();
        } else {
            state--;
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

    /* TODO:    Spawn instances of explosion going vertical and horizontal from bomb.
     *          Remove bomb from the board.    
    */
    private void explode() {

    }

    /**
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
