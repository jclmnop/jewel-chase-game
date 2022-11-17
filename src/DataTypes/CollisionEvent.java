package DataTypes;

public enum CollisionEvent {
    // I don't like this, but Java doesn't allow us to
    // pattern match (enum, enum) in switch statements.
    NOTHING,
    DETONATE,
    LOOT_STOLEN,
    LOOT_COLLECTED,
    CLOCK_STOLEN,
    CLOCK_COLLECTED,
    LEVER_TRIGGERED,
    ASSASSINATION,
    DOUBLE_ASSASSINATION,
    WIN,
    LOSE;

    public static CollisionEvent calculateCollisionEvent(
        CollisionType collisionOne,
        CollisionType collisionTwo
    ) {
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
                        collisionTwo, collisionOne
                    );
            };
            default -> NOTHING;
        };
    }
}
