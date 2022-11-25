package Entities.Characters;

import java.util.HashSet;
import java.util.LinkedList;

import DataTypes.*;
import Entities.Items.Item;
import Game.Tile;

public class SmartThief extends Character {

    // Current path to item
    private LinkedList<Coords> path;

    public SmartThief() {
        super(CollisionType.THIEF, true);
    }
    
    /**
     * Finds shortest path to nearest item with BFS.
     * @param item item type to search for.
     */
    public void findPath(Class<Item> item) {

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

        // Run BFS until the node with a required item is found.
        while (Tile.getTile(queue.peek().coords).getEntitiesOfType(item).isEmpty()) {
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
