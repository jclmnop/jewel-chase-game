package Entities.Characters.smartThiefTest;

public class Node {
    
    // TODO make fields private and add getters and setters.
    public Node parent;
    public int distance = Integer.MAX_VALUE;
    // Manhattan distance from the goal.
    public int heuristic;
    public int xCoord;
    public int yCoord;

    public Node (int x, int y) {
        xCoord = x;
        yCoord = y;
    }
}
