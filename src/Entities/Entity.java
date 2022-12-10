package Entities;

import DataTypes.Collision;
import DataTypes.CollisionEvent;
import DataTypes.Coords;
import Entities.Characters.Character;
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

    /**
     * Collision type for an entity.
     * Used to compute the outcome of a collision between two Entity objects.
     */
    public enum CollisionType {
        EXPLOSION,
        BOMB,
        LOOT,
        CLOCK,
        KEY,
        MIRROR,
        COFFEE,
        GATE,
        DOOR,
        ASSASSIN,
        THIEF,
        PLAYER,
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

        if (entity instanceof Player) {
            Game.removePlayer((Player) entity);
        }
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
            System.out.println("COLLISION: " + collisionEvent);
            //TODO: extract into own methods (and maybe move all to CollisionEvent)
            switch (collisionEvent) {
                case NOTHING -> {}
                case LOOT_STOLEN -> {
                    Loot loot = (Loot) collision.getEntityOne();
                    int lootValue = loot.getScore();
                    Game.adjustScore(-lootValue);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case LOOT_COLLECTED -> {
                    Loot loot = (Loot) collision.getEntityOne();
                    int lootValue = loot.getScore();
                    Game.adjustScore(lootValue);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case CLOCK_STOLEN -> {
                    //TODO: decide seconds per clock
                    int timeAdjustment = 10;
                    Game.adjustTime(-Clock.SECONDS);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case CLOCK_COLLECTED -> {
                    //TODO: decide seconds per clock
                    int timeAdjustment = 10;
                    Game.adjustTime(+Clock.SECONDS);
                    Entity.removeEntity(collision.getEntityOne());
                }
                case LEVER_TRIGGERED -> {
                    Key triggeredKey = (Key) collision.getEntityOne();
                    triggeredKey.openGates();
                    Entity.removeEntity(triggeredKey);
                }
                case DOUBLE_ASSASSINATION -> {
                    //TODO: this means two assassins collided, kill them both
                    Character entityOne = (Character) collision.getEntityOne();
                    Character entityTwo = (Character) collision.getEntityOne();
                    entityOne.kill();
                    entityTwo.kill();
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
                    //TODO: finish implementing Game.win()
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
                        Object deserialised =
                            Deserialiser.deserialiseObject(entityToBeCloned.serialise());
                        if (deserialised instanceof Character deserialisedCharacter) {
                            deserialisedCharacter.decrementTicksSinceLastMove();
                        }
                    }
                    Entity.removeEntity(collision.getEntityOne());
                }
                case SPEED_UP -> {
                    Character character = (Character) collision.getEntityTwo();
                    boolean maxSpeedAlreadyReached = character.speedUp();
                    if (maxSpeedAlreadyReached) {
                        int scoreAdjustment =
                            character instanceof Player
                                ? +Coffee.POINTS_IF_MAX_SPEED_REACHED
                                : -Coffee.POINTS_IF_MAX_SPEED_REACHED;
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
