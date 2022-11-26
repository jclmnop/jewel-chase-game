package Utils;

import DataTypes.Colour;
import DataTypes.Colours;
import DataTypes.Exception.ParseTileColourException;
import DataTypes.GameParams;
import Entities.Characters.Npc.FloorFollowingThief;
import Entities.Characters.Npc.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.Npc.SmartThief;
import Entities.Items.Collectable.Clock;
import Entities.Items.Gate;
import Entities.Items.Collectable.Lever;
import Entities.Items.Collectable.Loot;
import Game.Tile;

import java.util.ArrayList;

/**
 * Utility meant to be used by GameFileHandler when loading a level/save file.
 */
public class Deserialiser {
    public static Object deserialiseObject(String serialisedString) throws ClassNotFoundException, ParseTileColourException {
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
                // TODO: custom exception
                throw new ClassNotFoundException(
                    "Deserialisation of " + objectTypeName + " failed, class name not recognised"
                );
            }
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
