package Game;

import DataTypes.AdjacentTiles;
import DataTypes.Colour;
import DataTypes.Colours;
import DataTypes.Coords;
import Entities.Entity;
import Interfaces.Serialisable;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile implements Serialisable {
    // Static attributes //
    private static Tile[][] board;
    // Adjacency map for FloorFollowingThief
    private static HashMap<Coords, AdjacentTiles> singleColourAdjacencyMap;
    // Adjacency map for SmartThief and Player
    private static HashMap<Coords, AdjacentTiles> multiColourAdjacencyMap;
    // Adjacency map for FlyingAssassin
    private static HashMap<Coords, AdjacentTiles> NoColourAdjacencyMap;

    // Instance attributes //
    private final Colours colours;
    private ArrayList<Entity> entities;

    public Tile(Colours colours) {
        this.colours = colours;
    }

    // Static methods //
    // TODO: getSingleColourAdjacentTiles(coords)
    // TODO: getMultiColourAdjacentTiles(coords)
    // TODO: getNoColourAdjacentTiles(coords)
    // TODO: move(entity, from, to)
    // TODO: getTile(coords)
    // TODO: getEntityCoordinates(entity)
    // TODO: newBoard(board: Tile[][])
    // TODO: clearBoard()

    // Instance Methods //
    public Colours getColours() {
        return colours;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public boolean isBlocked() {
        for (Entity e: entities) {
            if (e.isBlocking()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasColour(Colour colour) {
        return this.colours.c1().equals(colour)
            || this.colours.c2().equals(colour)
            || this.colours.c3().equals(colour)
            || this.colours.c4().equals(colour);
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        // TODO: test that this works in the way I expect it to
        this.entities.remove(entity);
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return this.colours.toString();
    }
}
