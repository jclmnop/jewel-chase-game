package Interfaces;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Interface for classes that need to be "handled" each tick.
 * e.g: NPC movement, counting down a bomb, etc.
 *
 * @author Jonny
 * @version 1.0
 */
public interface Handleable {
    static <T> ArrayList<Handleable> getHandleable(ArrayList<T> objects) {
        return objects.stream()
            .filter(e -> e instanceof Handleable)
            .map(e -> (Handleable) e)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Called by Game class to perform any necessary actions per tick for this
     * entity.
     */
    void handle();
}
