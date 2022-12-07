package DataTypes;

import Entities.Entity;

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
    /** Loot stolen by a thief, score should decrease. */
    LOOT_STOLEN,
    /** Loot collected by a player, score should increase. */
    LOOT_COLLECTED,
    /** Clock stolen by a thief, time should decrease. */
    CLOCK_STOLEN,
    /** Clock collected by a player, time should increase. */
    CLOCK_COLLECTED,
    /** Star collected, entity will be cloned. */
    CLONE,
    /** Relevant gates should open. */
    LEVER_TRIGGERED,
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
            case STAR -> switch (collisionTwo) {
                case PLAYER, THIEF -> CLONE;
                default            -> NOTHING;
            };
            case LEVER -> switch (collisionTwo) {
                case THIEF, PLAYER -> LEVER_TRIGGERED;
                default            -> NOTHING;
            };
            case DOOR -> switch (collisionTwo) {
                case THIEF  -> LOSE;
                case PLAYER -> WIN;
                default     -> NOTHING;
            };
            case BOMB -> switch (collisionTwo) {
                case THIEF, PLAYER -> DETONATE;
                // TODO: explosion chain reaction
                default            -> NOTHING;
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
}
