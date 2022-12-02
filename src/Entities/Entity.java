package Entities;

import DataTypes.Collision;
import DataTypes.CollisionEvent;
import DataTypes.CollisionType;
import DataTypes.Coords;
import Entities.Characters.Character;
import Interfaces.Renderable;
import Interfaces.Serialisable;
import Game.Game;
import Game.Tile;
import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Entity implements Serialisable, Renderable {
    private static ArrayDeque<Collision> collisions = new ArrayDeque<>();
    private static ArrayList<Entity> entities = new ArrayList<>();
    protected final CollisionType collisionType;
    protected final boolean blocking;
    protected String imagePath;
    protected Coords coords;
    protected Image image = null;

    protected Entity(CollisionType collisionType, boolean blocking, Coords coords) {
        this.collisionType = collisionType;
        this.blocking = blocking;
        this.coords = coords;
        entities.add(this);
        Tile.getTile(coords).addEntity(this);
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
        Tile.removeEntityFromBoard(entity);
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
                    //TODO: get value of loot
                    int lootValue = 10;
                    Game.adjustScore(-lootValue);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case LOOT_COLLECTED -> {
                    //TODO: get value of loot
                    int lootValue = 10;
                    Game.adjustScore(lootValue);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case CLOCK_STOLEN -> {
                    //TODO: decide seconds per clock
                    int timeAdjustment = 10;
                    Game.adjustTime(-timeAdjustment);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case CLOCK_COLLECTED -> {
                    //TODO: decide seconds per clock
                    int timeAdjustment = 10;
                    Game.adjustTime(timeAdjustment);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case LEVER_TRIGGERED -> {
                    //TODO: remove gate of same colour
                }
                case DOUBLE_ASSASSINATION -> {
                    //TODO: this means two assassins collided, kill them both
                    Character entityOne = (Character) collision.getEntityOne();
                    Character entityTwo = (Character) collision.getEntityOne();
                    entityOne.kill();
                    entityTwo.kill();
                }
                case ASSASSINATION -> {
                    Character entityTwo = (Character) collision.getEntityOne();
                    entityTwo.kill();
                }
                case LOSE -> {
                    //TODO: finish implementing Game.lose()
                    Game.lose();
                }
                case WIN -> {
                    //TODO: finish implementing Game.win()
                    Game.win();
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

    public Image toImage() {
        if (this.image == null) {
            this.image = new Image(this.imagePath);
        }
        return this.image;
    }
}
