package Entities;

import DataTypes.Collision;
import DataTypes.CollisionEvent;
import DataTypes.CollisionType;
import DataTypes.Coords;
import Interfaces.Serialisable;

import java.util.ArrayDeque;
import java.util.ArrayList;

public abstract class Entity implements Serialisable {
    private static final ArrayDeque<Collision> collisions = new ArrayDeque<>();
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

    // TODO: static method for each subclass that filters entities for instances of subclass
    //       e.g. Player.getPlayers() -> ArrayList<Player>,
    //            Character.getCharacters() -> ArrayList<Character> etc.
    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public static void processCollisions() {
        while (!Entity.collisions.isEmpty()) {
            Entity.processCollision();
        }
    }

    public static void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    private static void processCollision() {
        Collision collision = Entity.dequeCollision();
        if (collision.isValid()) {
            CollisionEvent collisionEvent = CollisionEvent.calculateCollisionEvent(
                    collision.getEntityOne().collisionType,
                    collision.getEntityTwo().getCollisionType()
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
