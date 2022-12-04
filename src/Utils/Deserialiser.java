package Utils;

import DataTypes.*;
import DataTypes.Exception.DeserialiseException;
import DataTypes.Exception.ParseTileColourException;
import Entities.Characters.Npc.FloorFollowingThief;
import Entities.Characters.Npc.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.Npc.SmartThief;
import Entities.Items.Bomb;
import Entities.Items.Collectable.Clock;
import Entities.Items.Gate;
import Entities.Items.Collectable.Lever;
import Entities.Items.Collectable.Loot;
import Game.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Utility meant to be used by GameFileHandler when loading a level/save file.
 */
public class Deserialiser {
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
                // TODO: case "Explosion -> {}"
                default -> {
                    throw new DeserialiseException(
                        "Deserialisation of " + objectTypeName + " failed, class name not recognised"
                    );
                }
            }
        } catch (Exception e) {
            throw new DeserialiseException(
                "Exception occurred when attempting to deserialise " + objectTypeName,
                e
            );
        }

    }

    // TODO: deserialiseGameParams
    public static GameParams deserialiseGameParams(String gameParamString) {
        String[] args = gameParamString.split(" ");
        int time = Integer.parseInt(args[0]);
        int score = (args.length > 1) ? Integer.parseInt(args[1]) : 0;
        return new GameParams(time, score);
    }

    // TODO: implement these methods once constructors for each class have
    //       been implemented.

    private static SmartThief deserialiseSmartThief(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        int speed = Integer.parseInt(stringIterator.next());
        Direction direction = Direction.fromString(stringIterator.next());
        return new SmartThief(coords, speed, direction);
    }

    private static Player deserialisePlayer(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        Coords coords = Coords.fromString(stringIterator.next(), stringIterator.next());
        int speed = Integer.parseInt(stringIterator.next());
        Direction direction = Direction.fromString(stringIterator.next());
        //TODO direction
        return new Player(coords, speed); // TODO
    }

    private static FlyingAssassin deserialiseFlyingAssassin(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        //TODO speed
        //TODO direction
        return new FlyingAssassin(new Coords(0, 0), 1); // TODO
    }

    private static FloorFollowingThief deserialiseFloorFollowingThief(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        //TODO speed
        //TODO direction
        //TODO colour
        return new FloorFollowingThief(new Coords(0, 0), 1); // TODO
    }

    private static Lever deserialiseLever(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        //TODO colour
        return new Lever(new Coords(0, 0)); // TODO
    }

    private static Loot deserialiseLoot(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        //TODO lootType
        return new Loot(new Coords(0, 0)); // TODO
    }

    private static Gate deserialiseGate(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        //TODO colour
        return new Gate(new Coords(0, 0)); // TODO
    }

    private static Clock deserialiseClock(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        return new Clock(new Coords(0, 0)); // TODO
    }

    private static Bomb deserialiseBomb(String[] splitString) {
        Iterator<String> stringIterator = Arrays.stream(splitString).iterator();
        //TODO coords
        //TODO detonated boolean
        //TODO timer (in ms)
        return new Bomb(new Coords(0, 0)); // TODO
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
