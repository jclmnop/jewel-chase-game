package DataTypes;

import Entities.Characters.Character;
import Entities.Characters.Npc.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Entity;
import Entities.Items.Bomb;
import Entities.Items.Collectable.*;
import Game.Game;
import Utils.Deserialiser;

/**
 * Enum representing the type of event that a collision has resulted in.
 * @author Jonny
 * @version 1.0
 */
public enum CollisionEvent {
    /** Nothing should happen. */
    NOTHING,
    /** Bomb should explode. */
    DETONATE,
    /** Explosions should destroy entity */
    DESTROY,
    /** Loot stolen by a thief, score should decrease. */
    LOOT_STOLEN,
    /** Loot collected by a player, score should increase. */
    LOOT_COLLECTED,
    /** Clock stolen by a thief, time should decrease. */
    CLOCK_STOLEN,
    /** Clock collected by a player, time should increase. */
    CLOCK_COLLECTED,
    /** Mirror collected/stolen, entity will be cloned. */
    CLONE,
    /** Coffee consumed, grant permanent speed boost. */
    COFFEE_CONSUMED,
    /** Relevant gates should open. */
    OPEN_GATES,
    /** A character was assassinated. */
    ASSASSINATION,
    /** Two assassins have assassinated each other. */
    DOUBLE_ASSASSINATION,
    /** Player wins the game if all collectables have been collected. */
    WIN,
    /** Player loses the game if all collectables have been collected.. */
    LOSE;

    /**
     * Compute the type of collision event resulting from a collision.
     * @param collision The collision to compute an event from.
     * @return The computed event.
     */
    public static CollisionEvent calculateCollisionEvent(Collision collision) {
        Entity.CollisionType collisionOne = collision.getEntityOne().getCollisionType();
        Entity.CollisionType collisionTwo = collision.getEntityTwo().getCollisionType();
        return switch (collisionOne) {
            case LOOT -> switch (collisionTwo) {
                case THIEF  -> LOOT_STOLEN;
                case PLAYER -> LOOT_COLLECTED;
                default     -> NOTHING;
            };
            case CLOCK -> switch (collisionTwo) {
                case THIEF  -> CLOCK_STOLEN;
                case PLAYER -> CLOCK_COLLECTED;
                default     -> NOTHING;
            };
            case MIRROR -> switch (collisionTwo) {
                case PLAYER, THIEF, ASSASSIN -> CLONE;
                default                      -> NOTHING;
            };
            case COFFEE -> switch (collisionTwo) {
                case THIEF, PLAYER, ASSASSIN -> COFFEE_CONSUMED;
                default                      -> NOTHING;
            };
            case KEY -> switch (collisionTwo) {
                case THIEF, PLAYER -> OPEN_GATES;
                default            -> NOTHING;
            };
            case DOOR -> switch (collisionTwo) {
                case THIEF  -> LOSE;
                case PLAYER -> WIN;
                default     -> NOTHING;
            };
            case EXPLOSION -> switch (collisionTwo) {
                case GATE, DOOR -> NOTHING;
                case BOMB       -> DETONATE;
                default         -> DESTROY;
            };
            case ASSASSIN -> switch (collisionTwo) {
                case THIEF, PLAYER -> ASSASSINATION;
                case ASSASSIN      -> DOUBLE_ASSASSINATION;
                default            -> NOTHING;
            };
            case THIEF, PLAYER -> switch (collisionTwo) {
                case THIEF, PLAYER -> NOTHING;
                // Because collisionOne and collisionTwo will be sorted,
                // THIEF and PLAYER will always be collisionTwo relative to all
                // others, but this case swaps them round if they're somehow unsorted.
                default ->
                    CollisionEvent.calculateCollisionEvent(
                        new Collision(
                            collision.getCoords(),
                            collision.getEntityTwo(),
                            collision.getEntityOne()
                        )
                    );
            };
            default -> NOTHING;
        };
    }

    /**
     * Process a collision based on the collision event.
     * @param collision The collision to be processed.
     */
    public void processCollisionEvent(Collision collision) {
        if (collision.isValid()) {
            System.out.println("COLLISION: " + this);
            switch (this) {
                case NOTHING -> {}
                case LOOT_STOLEN -> {
                    CollisionEvent.processLootStolen(collision);
                }
                case LOOT_COLLECTED -> {
                    CollisionEvent.processLootCollected(collision);
                }
                case CLOCK_STOLEN -> {
                    CollisionEvent.processClockStolen(collision);
                }
                case CLOCK_COLLECTED -> {
                    CollisionEvent.processClockCollected(collision);
                }
                case OPEN_GATES -> {
                    CollisionEvent.processOpenGates(collision);
                }
                case DOUBLE_ASSASSINATION -> {
                    CollisionEvent.processDoubleAssassination(collision);
                }
                case ASSASSINATION -> {
                    CollisionEvent.processAssassination(collision);
                }
                case LOSE -> {
                    CollisionEvent.processLose();
                }
                case WIN -> {
                    CollisionEvent.processWin();
                }
                case CLONE -> {
                    CollisionEvent.processClone(collision);
                }
                case COFFEE_CONSUMED -> {
                    CollisionEvent.processCoffeeConsumed(collision);
                }
                case DETONATE -> {
                    CollisionEvent.processDetonate(collision);
                }
                case DESTROY -> {
                    CollisionEvent.processDestroy(collision);
                }
            }
        }
    }

    private static void processLootStolen(Collision collision) {
        // Spec says loot just disappears when stolen, doesn't
        // decrease score.
        Entity.removeEntity(collision.getEntityOne());
    }

    private static void processLootCollected(Collision collision) {
        Loot loot = (Loot) collision.getEntityOne();
        int lootValue = loot.getScore();
        Game.adjustScore(lootValue);
        Entity.removeEntity(collision.getEntityOne());
    }

    private static void processClockStolen(Collision collision) {
        Game.adjustTime(-Clock.SECONDS);
        Entity.removeEntity(collision.getEntityOne());
    }

    private static void processClockCollected(Collision collision) {
        Game.adjustTime(+Clock.SECONDS);
        Entity.removeEntity(collision.getEntityOne());
    }

    private static void processOpenGates(Collision collision) {
        Key triggeredKey = (Key) collision.getEntityOne();
        triggeredKey.openGates();
        Entity.removeEntity(triggeredKey);
    }

    private static void processDoubleAssassination(Collision collision) {
        FlyingAssassin entityOne = (FlyingAssassin) collision.getEntityOne();
        FlyingAssassin entityTwo = (FlyingAssassin) collision.getEntityTwo();
        if (!entityOne.isTemporarilyInvincible() && !entityTwo.isTemporarilyInvincible()) {
            System.out.println("DOUBLE KILL");
            entityOne.kill();
            entityTwo.kill();
        }
    }

    private static void processAssassination(Collision collision) {
        Character entityTwo = (Character) collision.getEntityTwo();
        entityTwo.kill();
    }

    private static void processLose() {
        if (Entity.getEntitiesOfType(Collectable.class).isEmpty()) {
            Game.lose();
        }
    }

    private static void processWin() {
        if (Entity.getEntitiesOfType(Collectable.class).isEmpty()) {
            Game.win();
        }
    }

    private static void processClone(Collision collision) {
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

    private static void processCoffeeConsumed(Collision collision) {
        Character character = (Character) collision.getEntityTwo();
        boolean maxSpeedAlreadyReached = character.speedUp();
        if (maxSpeedAlreadyReached && character instanceof Player) {
            int scoreAdjustment = +Coffee.POINTS_IF_MAX_SPEED_REACHED;
            Game.adjustScore(scoreAdjustment);
        }
        Entity.removeEntity(collision.getEntityOne());
    }

    private static void processDetonate(Collision collision) {
        Bomb bomb = (Bomb) collision.getEntityTwo();
        bomb.chainReaction();
    }

    private static void processDestroy(Collision collision) {
        Entity.removeEntity(collision.getEntityTwo());
    }
}
