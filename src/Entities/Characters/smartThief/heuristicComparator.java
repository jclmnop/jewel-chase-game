package Entities.Characters.smartThief;

import java.util.Comparator;

// Simple comparator for queue implemented in A*
public class heuristicComparator implements Comparator<Node>{

    @Override
    public int compare(Node n1, Node n2) {
        return (n1.distance + n1.heuristic) - (n2.distance + n2.heuristic);
    }
    
}
