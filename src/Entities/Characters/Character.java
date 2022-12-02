package Entities.Characters;

import DataTypes.CollisionType;
import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;
import Game.Tile;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Character extends Entity {
    public static final String RESOURCES_PATH = Entity.RESOURCES_PATH + "characters/";
    protected Direction currentDirection;
    protected int speed;
    protected int ticksSinceLastMove;
    //TODO: sprite/image file?
    //TODO: death animation?

    public Character(CollisionType collisionType, boolean isBlocking, Coords coords, int speed) {
        super(collisionType, isBlocking, coords);
        this.speed = speed;
        this.ticksSinceLastMove = speed;
    }

    public void kill() {
        // TODO: death animation?
        Entity.removeEntity(this);
    }

    @Override
    public Image toImage() {
        ImageView imageView = new ImageView(super.toImage());
        //TODO: flip horizontal if degrees == 270
        imageView.setRotate(this.currentDirection.toDegrees());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    protected boolean move(Coords nextCoords) {
        this.ticksSinceLastMove++;
        if (this.ticksSinceLastMove >= this.speed) {
            this.ticksSinceLastMove = 0;
            this.currentDirection = this.coords.directionTo(nextCoords);
            Tile.move(this, this.coords, nextCoords);
            return true;
        } else {
            return false;
        }
    }
}
