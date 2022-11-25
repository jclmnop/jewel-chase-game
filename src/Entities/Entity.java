package Entities;

import DataTypes.Collision;
import DataTypes.CollisionEvent;
import DataTypes.CollisionType;
import DataTypes.Coords;
import Interfaces.Serialisable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Entity implements Serialisable {
    private static ArrayDeque<Collision> collisions = new ArrayDeque<>();
    private static ArrayList<Entity> entities = new ArrayList<>();
    protected final CollisionType collisionType;
    protected final boolean blocking;
    protected Coords coords;

    protected Entity(CollisionType collisionType, boolean blocking) {
        this.collisionType = collisionType;
        this.blocking = blocking;
        entities.add(this);
    }

    public static void enqueCollision(Coords coords, Entity entityOne, Entity entityTwo) {
        Entity.collisions.addLast(new Collision(coords, entityOne, entityTwo));
    }

    public static Collision dequeCollision() {
        return collisions.removeFirst();
    }

    public static Collision peekCollision() {
        return collisions.peekFirst();
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public static <T extends Entity> ArrayList<T> getEntitiesOfType(Class<T> c) {
        return Entity.filterEntitiesByType(c, Entity.entities);
    }

    public static <T extends Entity> ArrayList<T> filterEntitiesByType(
        Class<T> c,
        ArrayList<Entity> entityArrayList
    ) {
        // The IDE/compiler thinks the cast to T is unchecked, but we check
        // by filtering the ArrayList for only objects that are instanceof T,
        // so really it's fine.
        return entityArrayList
            .stream()
            .filter(c::isInstance)
            .map(e -> (T) e)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void processCollisions() {
        while (!Entity.collisions.isEmpty()) {
            Entity.processCollision();
        }
    }

    public static void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public static void clearEntities() {
        Entity.entities = new ArrayList<>();
        Entity.collisions = new ArrayDeque<>();
    }

    private static void processCollision() {
        Collision collision = Entity.dequeCollision();
        if (collision.isValid()) {
            CollisionEvent collisionEvent = CollisionEvent.calculateCollisionEvent(
                collision
            );
            switch (collisionEvent) {
                case NOTHING -> {}
                case LOOT_STOLEN -> {
                    //TODO: reduce score
                }
                case LOOT_COLLECTED -> {
                    //TODO: increase score
                }
                case CLOCK_STOLEN -> {
                    //TODO: reduce timeLeft
                }
                case CLOCK_COLLECTED -> {
                    //TODO: increase timeLeft
                }
                case LEVER_TRIGGERED -> {
                    //TODO: remove gate of same colour
                }
                case DOUBLE_ASSASSINATION -> {
                    //TODO: this means two assassins collided, kill them both
                }
                case ASSASSINATION -> {
                    //TODO: kill entityTwo
                }
                case LOSE -> {
                    //TODO: end game; show defeat screen; don't save highscore;
                }
                case WIN -> {
                    //TODO: end game; show victory screen; save highscore;
                }
            }
        }
    }

    public CollisionType getCollisionType() {
        return this.collisionType;
    }

    public Coords getCoords() {
        return this.coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public boolean isBlocking() {
        return this.blocking;
    }
}
