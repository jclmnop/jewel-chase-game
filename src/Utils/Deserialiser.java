package Utils;

import Entities.Characters.FloorFollowingThief;
import Entities.Characters.FlyingAssassin;
import Entities.Characters.Player;
import Entities.Characters.SmartThief;
import Entities.Items.Clock;
import Entities.Items.Gate;
import Entities.Items.Lever;
import Entities.Items.Loot;
import Game.Tile;

public class Deserialiser {
    public static Object deserialiseObject(String serialisedString) throws ClassNotFoundException {
        var args = serialisedString.split(" ");
        var objectTypeName = args[0];

        switch (objectTypeName) {
            case "Tile" -> { 
                return Deserialiser.deserialiseTile(args);
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

    private static Tile deserialiseTile(String[] args) {
        return new Tile();
    }
}
