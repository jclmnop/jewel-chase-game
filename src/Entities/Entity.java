package Entities;

import DataTypes.Collision;
import DataTypes.CollisionEvent;
import DataTypes.Coords;
import Entities.Characters.Character;
import Entities.Characters.Npc.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Items.Bomb;
import Entities.Items.Collectable.*;
import Interfaces.Renderable;
import Interfaces.Serialisable;
import Game.Game;
import Game.Tile;
import Utils.Deserialiser;
import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents any object that can exist at a certain coordinate on the board.
 *
 * @author Jonny
 * @version 1.4
 */
public abstract class Entity implements Serialisable, Renderable {
    private static ArrayDeque<Collision> collisions = new ArrayDeque<>();
    private static ArrayList<Entity> entities = new ArrayList<>();
    protected final CollisionType collisionType;
    protected final boolean blocking;
    protected String imagePath;
    protected Coords coords;
    protected Image image = null;

    /**
     * Construct an entity object with the given parameters.
     * @param collisionType The enum type to be used when calculating the outcome
     *                      of a collision between this entity and another..
     * @param blocking Whether this entity stops other entities from occupying
     *                 the same coordinate.
     * @param coords Coordinates of the tile to spawn this entity on.
     */
    protected Entity(CollisionType collisionType, boolean blocking, Coords coords) {
        this.collisionType = collisionType;
        this.blocking = blocking;
        this.coords = coords;
        entities.add(this);
        Tile.getTile(coords).addEntity(this);
    }

    /**
     * Collision type for an entity.
     * Used to compute the outcome of a collision between two Entity objects.
     */
    public enum CollisionType {
        /** An explosion caused by a bomb */
        EXPLOSION,
        /** A bomb */
        BOMB,
        /** A loot item */
        LOOT,
        /** A clock */
        CLOCK,
        /** A key */
        KEY,
        /** A mirror */
        MIRROR,
        /** A coffee */
        COFFEE,
        /** A gate */
        GATE,
        /** A door */
        DOOR,
        /** A flying assassin */
        ASSASSIN,
        /** Any type of thief */
        THIEF,
        /** A player */
        PLAYER,
    }

    /**
     * Add a collision to the back of the collision queue, so it can be processed.
     * @param coords Coordinates of tile where collision took place.
     * @param entityOne First entity involved in collision.
     * @param entityTwo Seconds entity involved in collision.
     */
    public static void enqueCollision(Coords coords, Entity entityOne, Entity entityTwo) {
        Entity.collisions.addLast(new Collision(coords, entityOne, entityTwo));
    }

    /**
     * Remove and return a collision from the front of the queue.
     * @return The collision at the front of the queue.
     */
    public static Collision dequeCollision() {
        return collisions.removeFirst();
    }

    /**
     * @return All entities which currently exist on the board.
     */
    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Get all entities on the board which are instances of a specific class. .
     * @param c Class to filter by, must extend Entity.
     * @param <T> Type to be returned.
     * @return All entities which are instances of the class.
     */
    public static <T extends Entity> ArrayList<T> getEntitiesOfType(Class<T> c) {
        return Entity.filterEntitiesByType(c, Entity.entities);
    }

    /**
     * Filter a given list of entities for those that are instances of the
     * specified class.
     * @param c Class to filter by, must extend Entity.
     * @param entityArrayList Entities to be filtered.
     * @param <T> Type to be returned.
     * @return All entities from the given array list which are instances
     *         of the class.
     */
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

    /**
     * Process all collisions currently in the queue.
     */
    public static void processCollisions() {
        while (!Entity.collisions.isEmpty()) {
            Entity.processCollision();
        }
    }

    /**
     * Remove an entity from the game entirely.
     * @param entity Entity to be removed.
     */
    public static void removeEntity(Entity entity) {
        Tile.removeEntityFromBoard(entity);
        entities.remove(entity);

        if (entity instanceof Player) {
            Game.removePlayer((Player) entity);
        }
    }

    /**
     * Reset the entities currently in the game.
     */
    public static void clearEntities() {
        Entity.entities = new ArrayList<>();
        Entity.collisions = new ArrayDeque<>();
    }

    /**
     * @return Collision type of this entity.
     */
    public CollisionType getCollisionType() {
        return this.collisionType;
    }

    /**
     * @return Current coordinates of this entity.
     */
    public Coords getCoords() {
        return this.coords;
    }

    /**
     * Set current coordinates of this entity.
     * @param coords New coordinates.
     */
    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    /**
     * @return Whether this entity blocks other entities from moving to the
     *         same tile.
     */
    public boolean isBlocking() {
        return this.blocking;
    }

    /**
     * @return The JavaFx image associated with this entity.
     * @see Interfaces.Renderable
     */
    public Image toImage() {
        // Only load image if it hasn't already been loaded to prevent
        // memory issues.
        if (this.image == null) {
            this.image = new Image(this.imagePath);
        }
        return this.image;
    }

    /**
     * Serialise object into a string that can be deserialised.
     *
     * @return Serialised string representation of this object.
     * @see Interfaces.Serialisable
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise()
        );
    }

    //TODO: maybe move all to CollisionEvent and extract into separate methods?
    private static void processCollision() {
        Collision collision = Entity.dequeCollision();
        if (collision.isValid()) {
            CollisionEvent collisionEvent = CollisionEvent.calculateCollisionEvent(
                collision
            );
            System.out.println("COLLISION: " + collisionEvent);
            switch (collisionEvent) {
                case NOTHING -> {}
                case LOOT_STOLEN -> {
                    // Spec says loot just disappears when stolen, doesn't
                    // decrease score.
                    Entity.removeEntity(collision.getEntityOne());
                }
                case LOOT_COLLECTED -> {
                    Loot loot = (Loot) collision.getEntityOne();
                    int lootValue = loot.getScore();
                    Game.adjustScore(lootValue);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case CLOCK_STOLEN -> {
                    Game.adjustTime(-Clock.SECONDS);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case CLOCK_COLLECTED -> {
                    Game.adjustTime(+Clock.SECONDS);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case LEVER_TRIGGERED -> {
                    Key triggeredKey = (Key) collision.getEntityOne();
                    triggeredKey.openGates();
                    Entity.removeEntity(triggeredKey);
                }
                case DOUBLE_ASSASSINATION -> {
                    FlyingAssassin entityOne = (FlyingAssassin) collision.getEntityOne();
                    FlyingAssassin entityTwo = (FlyingAssassin) collision.getEntityTwo();
                    if (!entityOne.isTemporarilyInvincible() && !entityTwo.isTemporarilyInvincible()) {
                        System.out.println("DOUBLE KILL");
                        entityOne.kill();
                        entityTwo.kill();
                    }
                }
                case ASSASSINATION -> {
                    Character entityTwo = (Character) collision.getEntityTwo();
                    entityTwo.kill();
                }
                case LOSE -> {
                    if (Entity.getEntitiesOfType(Collectable.class).isEmpty()) {
                        Game.lose();
                    }
                }
                case WIN -> {
                    if (Entity.getEntitiesOfType(Collectable.class).isEmpty()) {
                        Game.win();
                    }
                }
                case CLONE -> {
                    Entity entityToBeCloned = collision.getEntityTwo();
                    int playerCount = Entity.getEntitiesOfType(Player.class).size();
                    if (
                        entityToBeCloned instanceof Player
                            && playerCount >= Game.MAX_PLAYERS
                    ) {
                        // If max players has already been reached, the item
                        // will give extra points instead.
                        Game.adjustScore(+Mirror.POINTS_IF_MAX_PLAYERS_REACHED);
                    } else {
                        if (entityToBeCloned instanceof FlyingAssassin flyingAssassin) {
                            flyingAssassin.makeTemporarilyInvincible();
                        }
                        Object deserialised =
                            Deserialiser.deserialiseObject(entityToBeCloned.serialise());
                        if (deserialised instanceof Character deserialisedCharacter) {
                            deserialisedCharacter.decrementTicksSinceLastMove();
                        }
                        if (deserialised instanceof FlyingAssassin flyingAssassin) {
                            flyingAssassin.turnRight();
                        }
                    }
                    Entity.removeEntity(collision.getEntityOne());
                }
                case SPEED_UP -> {
                    Character character = (Character) collision.getEntityTwo();
                    boolean maxSpeedAlreadyReached = character.speedUp();
                    if (maxSpeedAlreadyReached && character instanceof Player) {
                        int scoreAdjustment = +Coffee.POINTS_IF_MAX_SPEED_REACHED;
                        Game.adjustScore(scoreAdjustment);
                    }
                    Entity.removeEntity(collision.getEntityOne());
                }
                case DETONATE -> {
                    Bomb bomb = (Bomb) collision.getEntityTwo();
                    bomb.chainReaction();
                }
                case DESTROY -> {
                    Entity.removeEntity(collision.getEntityTwo());
                }
            }
        }
    }
}
