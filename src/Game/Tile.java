package Game;

import DataTypes.*;
import Entities.Entity;
import Interfaces.Serialisable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    public Tile(Colours colours) {
        this.colours = colours;
        this.entities = new ArrayList<>();
    }

    // Static methods //
    public static AdjacentCoords getSingleColourAdjacentTiles(Coords coords,
                                                              Colour colour) {
        return AdjacentCoords.singleColourAdjacentTiles(
            noColourAdjacencyMap.get(coords),
            colour
        );
    }

    public static AdjacentCoords getMultiColourAdjacentTiles(Coords coords) {
        return multiColourAdjacencyMap.get(coords);
    }

    public static AdjacentCoords getNoColourAdjacentTiles(Coords coords) {
        return noColourAdjacencyMap.get(coords);
    }

    public static Tile getTile(Coords coords) {
        return board[coords.y()][coords.x()];
    }

    public static boolean isValidCoords(Coords coords) {
        if (coords == null) {
            return false;
        } else {
            return Tile.isValidX(coords.x()) && Tile.isValidY(coords.y());
        }
    }

    public static void move(Entity entity, Coords from, Coords to) {
        var fromTile = Tile.getTile(from);
        var toTile = Tile.getTile(to);
        fromTile.removeEntity(entity);
        toTile.addEntity(entity);
        entity.setCoords(to);
    }


    public static void newBoard(Tile[][] board, int width, int height) {
        Tile.height = height;
        Tile.width = width;
        Tile.board = board;
        Tile.buildMultiColourAdjacencyMap();
        Tile.buildNoColourAdjacencyMap();
    }

    public static void clearBoard() {
        Tile.board = new Tile[][]{};
        Tile.multiColourAdjacencyMap = new HashMap<>();
        Tile.noColourAdjacencyMap = new HashMap<>();
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
