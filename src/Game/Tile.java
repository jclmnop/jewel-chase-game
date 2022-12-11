package Game;

import DataTypes.*;
import Entities.Entity;
import Interfaces.Serialisable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a tile on the board and contains all the entities currently on
 * that tile.
 *
 * @author Jonny
 * @version 1.3
 */
public class Tile implements Serialisable {
    // Static attributes //
    private static Tile[][] board;
    private static HashMap<Coords, AdjacentCoords> multiColourAdjacencyMap = new HashMap<>();
    private static HashMap<Coords, AdjacentCoords> noColourAdjacencyMap = new HashMap<>();
    private static int height;
    private static int width;

    // Instance attributes //
    private final Colours colours;
    private final ArrayList<Entity> entities;

    /**
     * Construct a fresh tile with the given colours.
     * @param colours Colours to create tile with.
     */
    public Tile(Colours colours) {
        this.colours = colours;
        this.entities = new ArrayList<>();
    }

    // Static methods //

    /**
     * Get adjacent coords for a given coord that match the assigned colour.
     * @param coords Coords to retrieve adjacent coords for.
     * @param colour Colour assigned to colour-bound entity.
     * @return Adjacent coords.
     */
    public static AdjacentCoords getSingleColourAdjacentTiles(Coords coords,
                                                              Colour colour) {
        return AdjacentCoords.singleColourAdjacentTiles(
            noColourAdjacencyMap.get(coords),
            colour
        );
    }

    /**
     * Get adjacent coords for a given coord using the standard pathfinding
     * method (used for SmartThief and Player).
     * @param coords Coords to retrieve adjacent coords for.
     * @return Adjacent coords.
     */
    public static AdjacentCoords getMultiColourAdjacentTiles(Coords coords) {
        return multiColourAdjacencyMap.get(coords);
    }

    /**
     * Get absolute adjacent tiles without taking colour into account.
     * @param coords Coords to retrieve adjacent coords for.
     * @return Adjacent coords.
     */
    public static AdjacentCoords getAdjacentCoords(Coords coords) {
        return noColourAdjacencyMap.get(coords);
    }

    /**
     * Get a tile located at the provided coordinates.
     * @param coords Coordinates of tile.
     * @return Tile at coordinates.
     */
    public static Tile getTile(Coords coords) {
        if (coords != null) {
            return board[coords.y()][coords.x()];
        } else {
            return null;
        }
    }

    /**
     * @return Height of the current board.
     */
    public static int getHeight() {
        return Tile.height;
    }

    /**
     * @return Width of the current board.
     */
    public static int getWidth() {
        return Tile.width;
    }

    /**
     * Check whether the tile located at these coordinates is currently blocked.
     * @param coords Coordinates for the tile to check.
     * @return Whether tile is blocked.
     */
    public static boolean isBlockedCoords(Coords coords) {
        return Tile.getTile(coords).isBlocked();
    }

    /**
     * Check whether coords are valid for the current board.
     * @param coords Coords to check.
     * @return Whether coords are valid.
     */
    public static boolean isValidCoords(Coords coords) {
        if (coords == null) {
            return false;
        } else {
            return Tile.isValidX(coords.x()) && Tile.isValidY(coords.y());
        }
    }

    /**
     * Get all entities on a tile which are instances of the given Class.
     * Can also be used to get entities which implement an interface.
     * @param c Class or Interface.
     * @param coords Coordinates of the Tile to check.
     * @param <T> Type to return.
     * @return Entities of the given type.
     */
    public static <T extends Entity> ArrayList<T> getEntitiesOfTypeByCoords(
        Class<T> c,
        Coords coords
    ) {
        Tile tile = Tile.getTile(coords);
        return tile.getEntitiesOfType(c);
    }

    /**
     * Move an entity from one tile to another.
     * @param entity Entity to be moved.
     * @param from Coords to move from.
     * @param to Coords to move to.
     */
    public static void move(Entity entity, Coords from, Coords to) {
        var fromTile = Tile.getTile(from);
        var toTile = Tile.getTile(to);
        fromTile.removeEntity(entity);
        toTile.addEntity(entity);
        entity.setCoords(to);
    }

    /**
     * Load a new board.
     * @param newBoard 2D Tile array to be loaded into the board.
     * @param width Width of the board.
     * @param height Height of the board.
     */
    public static void newBoard(Tile[][] newBoard, int width, int height) {
        Tile.clearBoard();
        Tile.height = height;
        Tile.width = width;
        Tile.board = new Tile[height][width];
        // Copy Tiles to prevent any side effects
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                Tile.board[i][k] = new Tile(newBoard[i][k].getColours());
            }
        }
        Tile.buildMultiColourAdjacencyMap();
        Tile.buildNoColourAdjacencyMap();
    }

    /**
     * Reset the board to an empty array.
     */
    public static void clearBoard() {
        Tile.board = new Tile[][]{};
        Tile.multiColourAdjacencyMap = new HashMap<>();
        Tile.noColourAdjacencyMap = new HashMap<>();
    }

    /**
     * Completely remove an entity from the board.
     * @param entity Entity to be removed.
     */
    public static void removeEntityFromBoard(Entity entity) {
        var entityTile = Tile.getTile(entity.getCoords());
        entityTile.removeEntity(entity);
        entity.setCoords(null);
    }

    /**
     * Serialise the board into a string representation.
     * @return The serialised board string.
     */
    public static String serialiseBoard() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("%s %s\n", Tile.width, Tile.height));
        for (Tile[] row : Tile.board) {
            String[] rowStrs = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                rowStrs[i] = row[i].serialise();
            }
            String rowStr = String.join(" ", rowStrs);
            output.append(rowStr);
            output.append("\n");
        }
        return output.toString();
    }

    // Instance Methods //

    /**
     * @return Colours for this tile.
     */
    public Colours getColours() {
        return colours;
    }

    /**
     * @return Entities on this tile.
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Used to filter Entities on Tile by type.
     * e.g. `tile.getEntitiesOfType(Loot.class)`
     * @param c Class to filter by
     * @param <T> Type of Class c, must extend Entity
     * @return Entities currently on Tile which belong to Class c
     */
    public <T extends Entity> ArrayList<T> getEntitiesOfType(Class<T> c) {
        return Entity.filterEntitiesByType(c, this.entities);
    }

    /**
     * @return Whether this tile is currently blocked.
     */
    public boolean isBlocked() {
        for (Entity e: entities) {
            if (e.isBlocking()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether this tile has a specific colour.
     * @param colour Colour to check for.
     * @return Whether this tile has the colour.
     */
    public boolean hasColour(Colour colour) {
        return this.colours.c1().equals(colour)
            || this.colours.c2().equals(colour)
            || this.colours.c3().equals(colour)
            || this.colours.c4().equals(colour);
    }

    /**
     * Add a new entity to this tile.
     * @param newEntity Entity to be added.
     */
    public void addEntity(Entity newEntity) {
        for (Entity entityOnTile : this.entities) {
            Entity.enqueCollision(
                entityOnTile.getCoords(),
                entityOnTile, newEntity
            );
        }
        this.entities.add(newEntity);
    }

    /**
     * Remove an entity from this tile.
     * @param entity Entity to be removed.
     */
    public void removeEntity(Entity entity) {
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

    private static void buildMultiColourAdjacencyMap() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Coords currentCoords = new Coords(col, row);
                AdjacentCoords adjacentCoords = Tile.findMultiColourAdjacentTiles(currentCoords);
                Tile.multiColourAdjacencyMap.put(currentCoords, adjacentCoords);
            }
        }
    }

    private static void buildNoColourAdjacencyMap() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Coords currentCoords = new Coords(col, row);
                Coords up = new Coords(col, row - 1);
                Coords down = new Coords(col, row + 1);
                Coords left = new Coords(col - 1, row);
                Coords right = new Coords(col + 1, row);

                // Leave tile as null if coords are invalid
                if (!Tile.isValidCoords(up)) {
                    up = null;
                }

                if (!Tile.isValidCoords(down)) {
                    down = null;
                }

                if (!Tile.isValidCoords(left)) {
                    left = null;
                }

                if (!Tile.isValidCoords(right)) {
                    right = null;
                }

                AdjacentCoords adjacentCoords = new AdjacentCoords(up, down, left, right);
                Tile.noColourAdjacencyMap.put(currentCoords, adjacentCoords);
            }
        }
    }

    private static AdjacentCoords findMultiColourAdjacentTiles(Coords tileCoords) {
        var tile = Tile.getTile(tileCoords);

        HashMap<Direction, Coords> adjacentCoords = new HashMap<>();
        adjacentCoords.put(Direction.UP, null);
        adjacentCoords.put(Direction.DOWN, null);
        adjacentCoords.put(Direction.LEFT, null);
        adjacentCoords.put(Direction.RIGHT, null);

        for (Direction direction : adjacentCoords.keySet()) {
            Coords currentCoords = Coords.move(tileCoords, direction);
            while (adjacentCoords.get(direction) == null && Tile.isValidCoords(currentCoords)) {
                var currentTile = Tile.getTile(currentCoords);
                if (Tile.tilesShareColour(tile, currentTile)) {
                    adjacentCoords.put(direction, currentCoords);
                }
                currentCoords = Coords.move(currentCoords, direction);
            }
        }

        return new AdjacentCoords(
            adjacentCoords.get(Direction.UP),
            adjacentCoords.get(Direction.DOWN),
            adjacentCoords.get(Direction.LEFT),
            adjacentCoords.get(Direction.RIGHT)
        );
    }

    private static boolean tilesShareColour(Tile tile, Tile otherTile) {
        var colours = tile.getColours();
        return otherTile.hasColour(colours.c1())
            || otherTile.hasColour(colours.c2())
            || otherTile.hasColour(colours.c3())
            || otherTile.hasColour(colours.c4());
    }
}
