package DataTypes;

import Entities.Characters.Player;

/**
 * Represents a player movement input.
 * @param player Player for which this input applies.
 * @param direction Direction in which this input moves the player.
 */
public record PlayerInput(Player player, Direction direction) {}
