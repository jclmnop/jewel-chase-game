package Entities.Characters.Npc;

import java.util.*;
import java.util.stream.Collectors;

import DataTypes.*;
import Entities.Characters.Character;
import Entities.Items.*;
import Entities.Items.Collectable.Collectable;
import Game.Tile;

/**
 * Finds the shortest path to nearest collectable items and steals them. If no
 * items are left it will attempt to find the shortest path to the door instead.
 *
 * In the absence of a valid path, the smart thief moves randomly.
 *
 * @author Dillon
 * @version 1.2
 * @see Entities.Characters.Npc.Npc
 */
public class SmartThief extends Npc {
    private static final String IMAGE_PATH = Character.RESOURCES_PATH + "faron_fancy.png";
    // Current desired item.
    private Item item;
    // Current path to item
    private LinkedList<Coords> path;

    /**
     * Spawn smart thief with given parameters.
     * @param coords Coordinates of smart thief.
     * @param ticksPerMove How many ticks need to pass between each movement.
     * @param direction Direction the thief is facing.
     */
    public SmartThief(Coords coords, int ticksPerMove, Direction direction) { //TODO: edit Character contructor to take direction
        super(CollisionType.THIEF, true, coords, ticksPerMove);
        this.currentDirection = direction;
        this.path = new LinkedList<>();
        this.imagePath = IMAGE_PATH;
    }

    /**
     * Spawn smart thief with given parameters.
     * @param coords Coordinates of smart thief.
     * @param ticksPerMove How many ticks need to pass between each movement.
     */
    public SmartThief(Coords coords, int ticksPerMove) {
        this(coords, ticksPerMove, Direction.UP);
    }

    /**
     * Spawn smart thief with given parameters. Used to load smart thief from
     * a serialised string.
     * @param coords Coordinates of smart thief.
     * @param ticksPerMove How many ticks need to pass between each movement.
     * @param direction Direction the thief is facing.
     * @param ticksSinceLastMove Number of ticks that have passed since the
     *                           last movement.
     */
    public SmartThief(Coords coords, int ticksPerMove, Direction direction, int ticksSinceLastMove) {
        this(coords, ticksPerMove, direction);
        this.ticksSinceLastMove = ticksSinceLastMove;
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        // TODO
        return String.format(
            "%s %s %s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.ticksPerMove,
            this.currentDirection,
            this.ticksSinceLastMove
        );
    }

    /**
     * Tries to move SmartThief to next tile in its path.
     *
     * If path is complete, desired item is no longer available or path is blocked,
     * then a new path is calculated first.
     *
     * If a new path cannot be calculated, moves in a random direction.
     */
    protected void tryMove() {
        if (this.needNewPath()) {
            // If no collectables are available, smart thief will move to the nearest door.
            if (Collectable.getCollectables().isEmpty()) {
                findPath(Door.class);
            } else {
                findPath(Collectable.class);
            }
        }

        if (this.path != null && !this.path.isEmpty()) {
            Coords nextCoords = this.path.peek();
            if (this.move(nextCoords)) {
                this.path.poll();
            };
        } else {
            // A new path could not be calculated
            this.moveRandomly();
        }
    }

    private void moveRandomly() {
        AdjacentCoords adjacentCoords = Tile.getMultiColourAdjacentTiles(this.coords);
        ArrayList<Coords> notNullCoords =
            Arrays.stream(adjacentCoords.toArray())
                .filter(Objects::nonNull)
                .filter(c -> !Tile.isBlockedCoords(c))
                .collect(Collectors.toCollection(ArrayList::new));

        if (!notNullCoords.isEmpty()) {
            int selectedIndex = new Random().nextInt(notNullCoords.size());
            Coords nextCoords = notNullCoords.get(selectedIndex);
            this.move(nextCoords);
        }
    }

    private boolean needNewPath() {
        boolean isPathComplete = this.path == null || this.path.isEmpty();
        boolean isTargetItemGone = !Item.getItems().contains(this.item);
        boolean isPathBlocked = this.isPathBlocked();
        return isPathComplete || isTargetItemGone || isPathBlocked;
    }

    private boolean isPathBlocked() {
        if (this.path != null) {
            for (Coords tileCoords : this.path) {
                if (Tile.isBlockedCoords(tileCoords)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds shortest path to nearest item with BFS.
     * @param itemType item type to search for.
     */
    private <T extends Item> void findPath(Class<T> itemType) {
        // Instance represents a node in a BFS tree.
        class Node {
            public Coords coords;
            public Node parent;

            public Node (Coords c) {
                coords = c;
            }
        }
        this.path.clear();
        // Implementation of queue in BFS algorithm.
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(new Node(this.coords));

        // Stores coords that of nodes that have been examined, so that don't get added to the queue again.
        HashSet<Coords> foundCoords = new HashSet<Coords>();

        // Run BFS until the node with a required item is found.
        while (queue.peek() != null && !this.isSearchFinished(itemType, queue.peek().coords)) {
            Node currNode = queue.poll();
            // This shouldn't ever be null because we check in loop condition
            foundCoords.add(currNode.coords);
            AdjacentCoords adjCoords = Tile.getMultiColourAdjacentTiles(currNode.coords);
            Coords[] coordsArr = adjCoords.toArray();

            // Add adjacent nodes to the queue and set parent pointers.
            for (Coords c : coordsArr) {
                if (c != null && !foundCoords.contains(c) && !Tile.isBlockedCoords(c)) {
                    Node n = new Node(c);
                    n.parent = currNode;
                    queue.add(n);
                    foundCoords.add(c);
                }
            }
        }

        // Backtrack using parent pointers to construct the SmartThief's path.
        Node n = queue.poll();
        while (n != null && n.parent != null) {
            path.addFirst(n.coords);
            n = n.parent;
        }
    }

    private <T extends Item> boolean isSearchFinished(Class<T> itemType, Coords nextCoords) {
        ArrayList<T> tileItems = Tile.getEntitiesOfTypeByCoords(itemType, nextCoords);
        if (!tileItems.isEmpty()) {
            this.item = tileItems.get(0);
            return true;
        } else {
            return false;
        }
    }
}
