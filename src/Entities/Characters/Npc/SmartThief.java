package Entities.Characters.Npc;

import java.util.*;
import java.util.stream.Collectors;

import DataTypes.*;
import Entities.Characters.Character;
import Entities.Items.*;
import Entities.Items.Collectable.Collectable;
import Game.Tile;

// TODO: remove javadoc from private methods (can leave this until last minute)
public class SmartThief extends Npc {
    // Current desired item.
    private Item item;

    // Current path to item
    private LinkedList<Coords> path;

    public SmartThief(Coords coords, int speed) {
        super(CollisionType.THIEF, true, coords, speed);
    }
    
    /**
     * Tries to move SmartThief to next tile in its path.
     *
     * If path is complete, desired item is no longer available or path is blocked,
     * then a new path is calculated first.
     *
     * If a new path cannot be calculated, moves in a random direction.
     */
    public void tryMove() {
        if (this.needNewPath()) {
            // If no collectables are available, smart thief will move to the nearest.
            if (Collectable.getCollectables().isEmpty()) {
                findPath(Door.class);
            } else {
                findPath(Collectable.class);
            }
        }
        Coords nextCoords = path.poll();
        if (nextCoords != null) {
            this.move(nextCoords);
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
                .collect(Collectors.toCollection(ArrayList::new));

        if (!notNullCoords.isEmpty()) {
            int selectedIndex = new Random().nextInt(notNullCoords.size());
            Coords nextCoords = notNullCoords.get(selectedIndex);
            this.move(nextCoords);
        }
    }

    private void move(Coords nextCoords) {
        Tile.move(this, this.coords, nextCoords);
    }

    private boolean needNewPath() {
        boolean isPathComplete = this.path.isEmpty();
        boolean isTargetItemGone = !Item.getItems().contains(this.item);
        boolean isPathBlocked = this.isPathBlocked();
        return isPathComplete || isTargetItemGone || isPathBlocked;
    }

    private boolean isPathBlocked() {
        for (Coords tileCoords : this.path) {
            if (Tile.isBlockedCoords(tileCoords)) {
                return true;
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
        return !Tile.getEntitiesOfTypeByCoords(itemType, nextCoords).isEmpty();
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
