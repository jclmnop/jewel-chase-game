package Utils;

import DataTypes.*;
import DataTypes.Exception.DeserialiseException;
import DataTypes.Exception.ParseTileColourException;
import Entities.Characters.Npc.FloorFollowingThief;
import Entities.Characters.Npc.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.Npc.SmartThief;
import Entities.Explosion;
import Entities.Items.Bomb;
import Entities.Items.Collectable.*;
import Entities.Items.Door;
import Entities.Items.Gate;
import Game.HighScoreTable;
import Game.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Used by GameFileHandler when loading a level/save file.
 *
 * @author Jonny
 * @version 1.3
 */
public class Deserialiser {
    public static final int DEFAULT_NPC_TICKS_PER_MOVE = 5;
    public static final int DEFAULT_PLAYER_TICKS_PER_MOVE = 1;

    /**
     * Deserialise a Tile or Entity object from its string representation and
     * instantiate it.
     * @param serialisedString String representation of the object.
     * @return The deserialised object.
     * @throws ParseTileColourException If a tile colour cannot be parsed from
     *                                  a character.
     * @throws DeserialiseException If class name in the serialised string does
     *                              not match any known class.
     */
    public static Object deserialiseObject(
        String serialisedString
    ) throws ParseTileColourException, DeserialiseException {
        var args = serialisedString.split(" ");
        var objectTypeName = (args.length > 1) ? args[0] : "Tile";
        try {
            switch (objectTypeName) {
                case "Tile" -> {
                    return Deserialiser.deserialiseTile(args[0]);
                }
                case "Clock" -> {
                    return Deserialiser.deserialiseClock(args);
                }
                case "Gate" -> {
                    return Deserialiser.deserialiseGate(args);
                }
                case "Key" -> {
                    return Deserialiser.deserialiseKey(args);
                }
                case "Loot" -> {
                    return Deserialiser.deserialiseLoot(args);
                }
                case "FloorFollowingThief" -> {
                    return Deserialiser.deserialiseFloorFollowingThief(args);
                }
                case "FlyingAssassin" -> {
                    return Deserialiser.deserialiseFlyingAssassin(args);
                }
                case "Player" -> {
                    return Deserialiser.deserialisePlayer(args);
                }
                case "SmartThief" -> {
                    return Deserialiser.deserialiseSmartThief(args);
                }
                case "Bomb" -> {
                    return Deserialiser.deserialiseBomb(args);
                }
                case "Door" -> {
                    return Deserialiser.deserialiseDoor(args);
                }
                case "Mirror" -> {
                    return Deserialiser.deserialiseMirror(args);
                }
                case "Coffee" -> {
                    return Deserialiser.deserialiseCoffee(args);
                }
                case "Explosion" -> {
                    return Deserialiser.deserialiseExplosion(args);
                }
                default -> {
                    throw new DeserialiseException(
                        "Deserialisation of " + objectTypeName + " failed, class name not recognised"
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeserialiseException(
                "Exception occurred when attempting to deserialise " + objectTypeName,
                e
            );
        }
    }

    /**
     * Deserialise game parameters from a string representation.
     * @param gameParamString String to be deserialised.
     * @return Deserialised game parameter object.
     */
    public static GameParams deserialiseGameParams(String gameParamString) {
        String[] args = gameParamString.split(" ");
        int time = Integer.parseInt(args[1]);
        int levelNumber = Integer.parseInt(args[0]);
        int score = (args.length > 2) ? Integer.parseInt(args[2]) : 0;
        return new GameParams(time, score, levelNumber);
    }


    /**
     * Deserialise a high score table from a string representation.
     * @param highScoreTableString String representation to be deserialised.
     * @return Deserialised high score table.
     */
    public static HighScoreTable deserialiseHighScoreTable(String highScoreTableString) {
        Iterator<String> lines = highScoreTableString.lines().iterator();
        int levelNumber = Integer.parseInt(lines.next());
        ArrayList<HighScoreTable.HighScoreEntry> highScoreEntries = new ArrayList<>();
        while (lines.hasNext()) {
            String nextLine = lines.next();
            if (!nextLine.isBlank()) {
                highScoreEntries.add(
                    Deserialiser.deserialiseHighScoreEntry(nextLine)
                );
            }
        }
        return new HighScoreTable(levelNumber, highScoreEntries);
    }

    private static HighScoreTable.HighScoreEntry deserialiseHighScoreEntry(String highScoreEntryString) {
        Iterator<String> splitLine = Arrays.stream(highScoreEntryString.split(" ")).iterator();
        return new HighScoreTable.HighScoreEntry(
            splitLine.next(),
            Integer.parseInt(splitLine.next())
        );

    }

    private static SmartThief deserialiseSmartThief(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        int speed = stringIterator.hasNext() ?
            Integer.parseInt(stringIterator.next())
            : DEFAULT_NPC_TICKS_PER_MOVE;
        Direction direction = stringIterator.hasNext()
            ? Direction.fromString(stringIterator.next())
            : Direction.RIGHT;
        int ticksSinceLastMove = stringIterator.hasNext()
            ? Integer.parseInt(stringIterator.next())
            : 0;
        return new SmartThief(coords, speed, direction, ticksSinceLastMove);
    }

    private static Player deserialisePlayer(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        int speed = stringIterator.hasNext() ?
            Integer.parseInt(stringIterator.next())
            : DEFAULT_PLAYER_TICKS_PER_MOVE;
        Direction direction = stringIterator.hasNext()
            ? Direction.fromString(stringIterator.next())
            : Direction.RIGHT;
        int ticksSinceLastMove = stringIterator.hasNext()
            ? Integer.parseInt(stringIterator.next())
            : 0;
        return new Player(coords, speed, direction, ticksSinceLastMove);
    }

    private static FlyingAssassin deserialiseFlyingAssassin(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        Direction direction = Direction.fromString(stringIterator.next());
        int speed = stringIterator.hasNext() ?
            Integer.parseInt(stringIterator.next())
            : DEFAULT_NPC_TICKS_PER_MOVE;
        //TODO direction
        return new FlyingAssassin(coords, speed); // TODO
    }

    private static FloorFollowingThief deserialiseFloorFollowingThief(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        int speed = stringIterator.hasNext() ?
            Integer.parseInt(stringIterator.next())
            : DEFAULT_NPC_TICKS_PER_MOVE;
        Colour colour = stringIterator.hasNext()
            ? Colour.fromChar(stringIterator.next().charAt(0))
            : Tile.getTile(coords).getColours().c1();
        Direction direction = stringIterator.hasNext()
            ? Direction.fromString(stringIterator.next())
            : Direction.RIGHT;
        int ticksSinceLastMove = stringIterator.hasNext()
            ? Integer.parseInt(stringIterator.next())
            : 0;
        return new FloorFollowingThief(coords, speed, colour, direction, ticksSinceLastMove);
    }

    private static Key deserialiseKey(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        Colour colour = Colour.fromChar(stringIterator.next().charAt(0));
        return new Key(coords, colour);
    }

    private static Loot deserialiseLoot(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        Loot.LootTier lootTier = stringIterator.hasNext()
            ? Loot.LootTier.fromString(stringIterator.next())
            : Loot.LootTier.TIER_1;
        return new Loot(coords, lootTier);
    }

    private static Gate deserialiseGate(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        Colour colour = Colour.fromChar(stringIterator.next().charAt(0));
        //TODO colour
        return new Gate(coords, colour);
    }

    private static Clock deserialiseClock(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Clock(coords);
    }

    private static Door deserialiseDoor(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Door(coords);
    }

    private static Bomb deserialiseBomb(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        boolean triggered = stringIterator.hasNext()
            ? Boolean.parseBoolean(stringIterator.next())
            : false;
        int state = stringIterator.hasNext()
            ? Integer.parseInt(stringIterator.next())
            : Bomb.INITIAL_STATE;
        return new Bomb(coords, triggered, state);
    }

    private static Mirror deserialiseMirror(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Mirror(coords);
    }

    private static Coffee deserialiseCoffee(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Coffee(coords);
    }

    private static Explosion deserialiseExplosion(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        int currentDurationTicks = stringIterator.hasNext()
            ? Integer.parseInt(stringIterator.next())
            : 0;
        return new Explosion(coords, currentDurationTicks);
    }

    private static Tile deserialiseTile(String arg) throws ParseTileColourException {
        // TODO: can either throw exception in fromChar(), or check length
        //       of result from map and check for nulls then throw exception if either
        ArrayList<Colour> colours = new ArrayList<>();
        var chars = arg.toCharArray();

        for (char c: chars) {
            var colour = Colour.fromChar(c);
            colours.add(colour);
        }

        return new Tile(
            new Colours(
                colours.get(0),
                colours.get(1),
                colours.get(2),
                colours.get(3)
            )
        );
    }
}
