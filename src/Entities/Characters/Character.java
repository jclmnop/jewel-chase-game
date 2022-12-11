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

/**
 * Represents an entity which is capable of moving on the board.
 *
 * @author Jonny
 * @version 1.1
 * @see Interfaces.Serialisable
 * @see Interfaces.Renderable
 * @see Entities.Entity
 */
public abstract class Character extends Entity {
    public static final String RESOURCES_PATH = Entity.RESOURCES_PATH + "characters/";
    public static final int MIN_TICKS_PER_MOVE = 1;
    protected Direction currentDirection;
    protected int ticksPerMove;
    protected int ticksSinceLastMove;

    /**
     * Construct a character with given parameters.
     * @param collisionType The collision type enum used to calculate collision
     *                      outcomes when this character collides with another
     *                      entity.
     * @param isBlocking Whether this character blocks other entities from
     *                   occupying the same tile.
     * @param coords Coordinates to create this character on.
     * @param ticksPerMove Number of ticks that must pass between each movement
     *                     for this character.
     */
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
     * Double the speed if possible, otherwise return true to indicate that
     * max speed had already been reached.
     *
     * @return true if max speed had already been reached before calling this
     *         method, false otherwise.
     */
    public boolean speedUp() {
        if (this.ticksPerMove == MIN_TICKS_PER_MOVE) {
            return true;
        } else {
            this.ticksPerMove = this.ticksPerMove / 2;
            // Nothing bad would happen if ticksPerMove goes below 1, or even below
            // zero (would still behave the same as 1), but 1 is technically the minimum.
            if (this.ticksPerMove <= MIN_TICKS_PER_MOVE ) {
                this.ticksPerMove = MIN_TICKS_PER_MOVE;
            }
            return false;
        }
    }

    /**
     * Kill a character.
     *
     * At the moment this method is no different to 
     * {@link Entity#removeEntity(Entity)} but remains implemented for the 
     * sake of future extendability. For example, if death animations for 
     * characters need to be implemented. 
     * 
     * @see Entity#removeEntity(Entity)
     */
    public void kill() {
        Entity.removeEntity(this);
    }

    /**
     * Load and return image associated with this character, rotated depending
     * on the direction in which the character is currently facing.
     * @return JavaFx image for this character.
     */
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
