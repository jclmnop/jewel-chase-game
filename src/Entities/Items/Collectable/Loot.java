package Entities.Items.Collectable;

import DataTypes.Coords;
import Entities.Items.Item;

/**
 * Represents a loot item which can be collected by the player to increase score.
 *
 * @author TODO: author
 * @version 1.0
 */
public class Loot extends Collectable {
    private static final String IMAGE_PATH = Item.RESOURCES_PATH + "loot_";
    private final LootTier lootTier;

    /**
     * Instantiate loot object with specified coordinates and loot tier.
     * @param coords Coordinates of loot object.
     * @param lootTier Tier of loot, four tiers in total.
     */
    public Loot(Coords coords, LootTier lootTier) {
        super(CollisionType.LOOT, false, coords);
        this.lootTier = lootTier;
        this.imagePath = IMAGE_PATH + (this.lootTier.ordinal() + 1) + ".png";
    }

    /**
     * Instantiate a loot object with specified coordinates and default loot
     * tier of TIER_1.
     * @param coords Coordinates of loot object.
     */
    public Loot(Coords coords) {
        this(coords, LootTier.TIER_1);
    }

    /**
     * Represents the "tier" of a loot. The higher the tier, the more points are
     * awarded upon collection. Also affects the image displayed for a loot item.
     */
    public enum LootTier {
        TIER_1,
        TIER_2,
        TIER_3,
        TIER_4;

        /**
         * Parse a string to a loot object.
         *
         * E.g "TIER_1" -> LootTier.TIER_1
         * @param str String to be parsed
         * @return Parsed loot tier.
         * @throws IllegalArgumentException if string cannot be parsed.
         */
        public static LootTier fromString(String str) {
            return switch (str) {
                case "TIER_1" -> TIER_1;
                case "TIER_2" -> TIER_2;
                case "TIER_3" -> TIER_3;
                case "TIER_4" -> TIER_4;
                default -> throw new IllegalArgumentException(
                    "Could not parse loot tier from string: " + str
                );
            };
        }

        /**
         * @return Number of points awarded for collecting this tier of loot.
         */
        public int getScore() {
            return switch (this) {
                case TIER_1 -> 10;
                case TIER_2 -> 20;
                case TIER_3 -> 40;
                case TIER_4 -> 80;
            };
        }
    }

    /**
     * @return Number of points awarded for collecting this loot object.
     */
    public int getScore() {
        return this.lootTier.getScore();
    }

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s %s",
            this.getClass().getSimpleName(),
            this.coords.serialise(),
            this.lootTier
        );
    }
}
