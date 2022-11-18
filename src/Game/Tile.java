package Game;

import DataTypes.AdjacentTiles;
import DataTypes.Colour;
import DataTypes.Colours;
import DataTypes.Coords;
import Interfaces.Serialisable;

import java.util.HashMap;

public class Tile implements Serialisable {
    // Static attributes
    private static Tile[][] board;
    // Adjacency map for FloorFollowingThief
    private static HashMap<Coords, AdjacentTiles> singleColourAdjacencyMap;
    // Adjacency map for SmartThief and Player
    private static HashMap<Coords, AdjacentTiles> multiColourAdjacencyMap;
    // Adjacency map for FlyingAssassin
    private static HashMap<Coords, AdjacentTiles> NoColourAdjacencyMap;

    // Instance attributes
    private final Colours colours;

    public Tile(Colours colours) {
        this.colours = colours;
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
