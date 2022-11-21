package Entities.Characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import DataTypes.CollisionType;

public class SmartThief extends Character {
    public SmartThief() {
        super(CollisionType.THIEF, true);
    }

    /**
     * Calculates the shortest path to an item using A*.
     * @param hm The graph of tiles.
     * @param start The tile the smart thief is currently on.
     * @param goal The tile containing the desired item.
     */
    private void findShortestPath(HashMap<Node, ArrayList<Node>> hm, Node start, Node goal) {

        // Queues nodes to be examined.
        PriorityQueue<Node> frontier = new PriorityQueue<Node>(new heuristicComparator());
        start.distance = 0;
        start.heuristic = Math.abs(start.xCoord - goal.xCoord) + Math.abs(start.yCoord - goal.yCoord);
        frontier.add(start);

        // Loop terminates when the goal is reached and therefore the shortest path has been found.
        while (frontier.peek() != goal) {
            Node current = frontier.poll();
            for (Node neighbour : hm.get(current)) {
                int cost = current.distance + 1; // Movement to the next node is always 1.
                // Checks if shorter path to neighbouring node has been found.
                if (cost < neighbour.distance) {
                    neighbour.parent = current;
                    neighbour.distance = cost;
                    if (!frontier.contains(neighbour)) {
                        neighbour.heuristic = Math.abs(neighbour.xCoord - goal.xCoord) + Math.abs(neighbour.yCoord - goal.yCoord);
                        frontier.add(neighbour);
                    }
                }
            }
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
