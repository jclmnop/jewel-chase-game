package Interfaces;

/**
 * Interface for classes that need to be "handled" each tick.
 * e.g: NPC movement, counting down a bomb, etc.
 *
 * @author Jonny
 * @version 1.0
 */
public interface Handleable {
    void handle();
}
