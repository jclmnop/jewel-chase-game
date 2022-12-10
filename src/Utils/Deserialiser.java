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
import Game.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Utility meant to be used by GameFileHandler when loading a level/save file.
 */
public class Deserialiser {
    public static final int DEFAULT_NPC_TICKS_PER_MOVE = 5;
    public static final int DEFAULT_PLAYER_TICKS_PER_MOVE = 1;
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
                case "Lever" -> {
                    return Deserialiser.deserialiseLever(args);
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
                case "Star" -> {
                    return Deserialiser.deserialiseStar(args);
                }
                case "Mushroom" -> {
                    return Deserialiser.deserialiseMushroom(args);
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

    public static GameParams deserialiseGameParams(String gameParamString) {
        String[] args = gameParamString.split(" ");
        int time = Integer.parseInt(args[1]);
        int levelNumber = Integer.parseInt(args[0]);
        int score = (args.length > 2) ? Integer.parseInt(args[2]) : 0;
        return new GameParams(time, score, levelNumber);
    }

    // TODO: implement these methods once constructors for each class have
    //       been implemented.

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
        return new Player(coords, speed, direction, ticksSinceLastMove); // TODO
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

    private static Lever deserialiseLever(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        Colour colour = Colour.fromChar(stringIterator.next().charAt(0));
        //TODO colour
        return new Lever(coords); // TODO
    }

    private static Loot deserialiseLoot(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        //TODO coords
        //TODO lootType
        return new Loot(coords); // TODO
    }

    private static Gate deserialiseGate(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        Colour colour = Colour.fromChar(stringIterator.next().charAt(0));
        //TODO colour
        return new Gate(coords); // TODO
    }

    private static Clock deserialiseClock(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Clock(coords); // TODO
    }

    private static Door deserialiseDoor(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Door(coords); // TODO
    }

    private static Bomb deserialiseBomb(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        //TODO detonated boolean
        //TODO timer (in ms)
        return new Bomb(coords); // TODO
    }

    private static Star deserialiseStar(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Star(coords);
    }

    private static Mushroom deserialiseMushroom(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        stringIterator.next(); // Skip type name
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        return new Mushroom(coords);
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

    // TODO: private static BombExplosion deserialiseExplosion(String[] splitString) {}

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
