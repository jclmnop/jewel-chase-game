package Entities.Characters.Npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import DataTypes.CollisionType;
import Entities.Characters.Character;

public class SmartThief extends Npc {
    public SmartThief() {
        super(CollisionType.THIEF, true);
    }

    @Override
    public void tryMove() {
        //TODO: implement
    }

    /**
     * Calculates the shortest path to an item using A*.
     * @param hm The graph of tiles.
     * @param start The tile the smart thief is currently on.
     * @param goal The tile containing the desired item.
     */
    private void findShortestPath(HashMap<Object, ArrayList<Object>> hm, Object start, Object goal) {

        // Queues nodes to be examined.
        PriorityQueue frontier = new PriorityQueue<Object>();
        frontier.add(start);

        // Loop terminates when the goal is reached and therefore the shortest path has been found.
        while (frontier.peek() != goal) {
            Object current = frontier.poll();
            for (Object neighbour : hm.get(current)) {
                int cost = current.distance + 1; // Movement to the next node is always 1.
                // Checks if shorter path to neighbouring node has been found.
                if (cost < neighbour.distance) {
                    neighbour.distance = cost;
                    neighbour.parent = current;
                    if (!frontier.contains(neighbour)) {
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
