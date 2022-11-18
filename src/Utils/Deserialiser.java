package Utils;

import DataTypes.Colour;
import DataTypes.Colours;
import Entities.Characters.FloorFollowingThief;
import Entities.Characters.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.SmartThief;
import Entities.Items.Clock;
import Entities.Items.Gate;
import Entities.Items.Lever;
import Entities.Items.Loot;
import Game.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Deserialiser {
    public static Object deserialiseObject(String serialisedString) throws ClassNotFoundException {
        var args = serialisedString.split(" ");
        var objectTypeName = (args.length > 1) ? args[0] : "Tile";

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
            default -> {
                throw new ClassNotFoundException(
                    "Deserialisation of " + objectTypeName + " failed, class name not recognised"
                );
            }
        }
    }

    //TODO: implement these methods once constructors for each class have
    //      been implemented.

    private static SmartThief deserialiseSmartThief(String[] splitString) {
        return new SmartThief();
    }

    private static Player deserialisePlayer(String[] splitString) {
        return new Player();
    }

    private static FlyingAssassin deserialiseFlyingAssassin(String[] splitString) {
        return new FlyingAssassin();
    }

    private static FloorFollowingThief deserialiseFloorFollowingThief(String[] splitString) {
        return new FloorFollowingThief();
    }

    private static Lever deserialiseLever(String[] splitString) {
        return new Lever();
    }

    private static Loot deserialiseLoot(String[] splitString) {
        return new Loot();
    }

    private static Gate deserialiseGate(String[] args) {
        return new Gate();
    }

    private static Clock deserialiseClock(String[] args) {
        return new Clock();
    }

    private static Tile deserialiseTile(String arg) {
        // TODO: can either throw exception in switch statement, or check length
        //       of result from map and check for nulls then throw exception if either
        List<Colour> colours = arg.chars().mapToObj(
            c -> Colour.fromChar((char) c)
        ).toList();
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
