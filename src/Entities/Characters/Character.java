package Entities.Characters;

import DataTypes.Coords;
import DataTypes.Direction;
import Entities.Entity;
import Game.Tile;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public abstract class Character extends Entity {
    public static final String RESOURCES_PATH = Entity.RESOURCES_PATH + "characters/";
    protected Direction currentDirection;
    protected int ticksPerMove;
    protected int ticksSinceLastMove;
    //TODO: sprite/image file?
    //TODO: death animation?

    public Character(CollisionType collisionType, boolean isBlocking, Coords coords, int ticksPerMove) {
        super(collisionType, isBlocking, coords);
        this.ticksPerMove = ticksPerMove;
        this.ticksSinceLastMove = 0;
    }

    /**
     * Used when cloning an entity to make sure they don't both try moving at
     * the exact same time.
     */
    public void decrementTicksSinceLastMove() {
        this.ticksSinceLastMove--;
    }

    /**
     * Double the speed of a character.
     */
    public void speedUp() {
        this.ticksPerMove = this.ticksPerMove / 2;
        // Nothing bad would happen if ticksPerMove goes below 1, or even below
        // zero (would still behave the same as 1), but 1 is technically the minimum.
        if (this.ticksPerMove < 1) {
            this.ticksPerMove = 1;
        }
    }

    public void kill() {
        // TODO: death animation?
        Entity.removeEntity(this);
    }

    @Override
    public Image toImage() {
        ImageView imageView = new ImageView(super.toImage());
        double rotation = this.currentDirection.toDegrees();
        if (rotation == 180) {
            // Flip image horizontally
            Translate flipTranslation = new Translate(0, imageView.getImage().getHeight());
            Rotate flipRotation = new Rotate(180, Rotate.Y_AXIS);
            imageView.getTransforms().addAll(flipTranslation,flipRotation);
        } else {
            imageView.setRotate(this.currentDirection.toDegrees());
        }
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    protected boolean move(Coords nextCoords) {
        this.ticksSinceLastMove++;
        if (this.ticksSinceLastMove >= this.ticksPerMove) {
            this.ticksSinceLastMove = 0;
            this.currentDirection = this.coords.directionTo(nextCoords);
            Tile.move(this, this.coords, nextCoords);
            return true;
        } else {
            return false;
        }
    }
}
