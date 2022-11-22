package Entities.Characters.smartThiefTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConstructPath {
    
    public static void main (String[] args) {
        Node n1 = new Node(0, 0);
        Node n2 = new Node(1, 0);
        Node n3 = new Node(1, 1);
        Node n4 = new Node(2, 0);
        Node n5 = new Node(2, 1);
        Node n6 = new Node(3, 0);
        Node n7 = new Node(3, 1);

        HashMap<Node, List<Node>> hm = new HashMap<Node, List<Node>>();
        hm.put(n1, Arrays.asList(n2));
        hm.put(n2, Arrays.asList(n1, n3, n4));
        hm.put(n3, Arrays.asList(n2, n4, n5));
        hm.put(n4, Arrays.asList(n2, n3, n5, n6, n7));
        hm.put(n5, Arrays.asList(n3, n4, n7));
        hm.put(n6, Arrays.asList(n4));
        hm.put(n7, Arrays.asList(n4, n5));

        System.out.println(SmartThief.findShortestPath(hm, n1, n3).size());
    }
}
