package Entities.Characters.Npc;

import java.util.HashSet;
import java.util.LinkedList;

import DataTypes.*;
import Entities.Characters.Character;
import Entities.Items.*;
import Entities.Items.Collectable.Collectable;
import Game.Tile;

public class SmartThief extends Npc {
    // TODO: method to check if current path is blocked at any point
    // TODO: method to check that final tile in path still contains a collectable item

    // Current desired item.
    private Item item;

    // Current path to item
    private LinkedList<Coords> path;

    public SmartThief() {
        super(CollisionType.THIEF, true);
    }
    
    /**
     * Moves the smart thief by setting its coordinates to the head of the path queue.
     * If path queue is empty or desired item is no longer available, the findPath method is called to construct a new path.
     */
    public void tryMove() {
        /* 
            TODO - write static methods in Item class to return lists of available items.
                 - examples here are default getItems and getCollectables which just includes loot and levers.
                 - loot and levers can be subclasses of a new class Collectable.
        */
        if (path.isEmpty() || !Item.getItems().contains(item)) {
            // If no collectables are available, smart thief will move to the nearest.
            if (Collectable.getCollectables().isEmpty()) {
                findPath(Door.class);
            } else {
                findPath(Collectable.class);
            }
        }
        Tile.move(this, this.coords, path.poll());
    }

    /**
     * Finds shortest path to nearest item with BFS.
     * @param itemType item type to search for.
     */
    private void findPath(Class itemType) {

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
        queue.add(new Node(coords));

        // Stores coords that of nodes that have been examined, so that don't get added to the queue again.
        HashSet<Coords> foundCoords = new HashSet<Coords>();

        // TODO: add some checks to prevent null pointer exceptions with queue.peek()
        // Run BFS until the node with a required item is found.
        while (Tile.getTile(queue.peek().coords).getEntitiesOfType(itemType).isEmpty()) {
            Node currNode = queue.poll();
            foundCoords.add(currNode.coords);
            AdjacentCoords adjCoords = Tile.getMultiColourAdjacentTiles(currNode.coords);
            Coords[] coordsArr = {
                                    adjCoords.getCoordsInDirection(Direction.UP),
                                    adjCoords.getCoordsInDirection(Direction.DOWN),
                                    adjCoords.getCoordsInDirection(Direction.LEFT),
                                    adjCoords.getCoordsInDirection(Direction.RIGHT)
                                };

            // Add adjacent nodes to the queue and set parent pointers.
            for (Coords c : coordsArr) {
                if (c != null && !foundCoords.contains(c)) {
                    Node n = new Node(c);
                    n.parent = currNode;
                    queue.add(n);
                }
            }
        }

        // Backtrack using parent pointers to construct the SmartThief's path.
        Node n = queue.poll();
        while (n.parent != null) {
            path.addFirst(n.coords);
            n = n.parent;
        }
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
