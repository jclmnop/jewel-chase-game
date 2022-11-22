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
    // Adjacency map for SmartThief and Player
    private static HashMap<Coords, AdjacentTiles> multiColourAdjacencyMap;
    // Adjacency map for FlyingAssassin
    private static HashMap<Coords, AdjacentTiles> noColourAdjacencyMap;
    private static int height;
    private static int width;

    // Instance attributes //
    private final Colours colours;
    private ArrayList<Entity> entities;

    public Tile(Colours colours) {
        this.colours = colours;
    }

    // Static methods //
    public static AdjacentTiles getSingleColourAdjacentTiles(Coords coords,
                                                             Colour colour) {
        return AdjacentTiles.singleColourAdjacentTiles(
            noColourAdjacencyMap.get(coords),
            colour
        );
    }

    public static AdjacentTiles getMultiColourAdjacentTiles(Coords coords) {
        return multiColourAdjacencyMap.get(coords);
    }

    public static AdjacentTiles getNoColourAdjacentTiles(Coords coords) {
        return noColourAdjacencyMap.get(coords);
    }

    public static Tile getTile(Coords coords) {
        return board[coords.y()][coords.x()];
    }

    public static boolean isValidCoords(Coords coords) {
        return Tile.isValidX(coords.x()) && Tile.isValidY(coords.y());
    }

    public static void move(Entity entity, Coords from, Coords to) {
        var fromTile = Tile.getTile(from);
        var toTile = Tile.getTile(to);
        fromTile.removeEntity(entity);
        toTile.addEntity(entity);
        entity.setCoords(to);
    }

    // TODO: buildMultiColourAdjacencyMap

    public static void buildNoColourAdjacencyMap() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile up = null;
                Tile down = null;
                Tile left = null;
                Tile right = null;

                Coords currentCoords = new Coords(col, row);
                Coords upCoords = new Coords(col, row - 1);
                Coords downCoords = new Coords(col, row + 1);
                Coords leftCoords = new Coords(col - 1, row);
                Coords rightCoords = new Coords(col + 1, row);

                // Leave tile as null if coords are invalid
                if (Tile.isValidCoords(upCoords)) {
                    up = Tile.getTile(upCoords);
                }

                if (Tile.isValidCoords(downCoords)) {
                    down = Tile.getTile(downCoords);
                }

                if (Tile.isValidCoords(leftCoords)) {
                    left = Tile.getTile(leftCoords);
                }

                if (Tile.isValidCoords(rightCoords)) {
                    right = Tile.getTile(rightCoords);
                }

                AdjacentTiles adjacentTiles = new AdjacentTiles(up, down, left, right);
                Tile.noColourAdjacencyMap.put(currentCoords, adjacentTiles);
            }
        }
    }

    public static void newBoard(Tile[][] board, int height, int width) {
        Tile.height = height;
        Tile.width = width;
        Tile.board = board;
    }

    public static void clearBoard() {
        Tile.board = new Tile[][]{};
    }

    public static void removeEntityFromBoard(Entity entity) {
        var entityTile = Tile.getTile(entity.getCoords());
        entityTile.removeEntity(entity);
        entity.setCoords(null);
        // TODO: remove from entities static list in Entity class
    }

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

    private static boolean isValidX(int x) {
        return x >= 0 && x < width;
    }

    private static boolean isValidY(int y) {
        return y >= 0 && y < height;
    }
}
