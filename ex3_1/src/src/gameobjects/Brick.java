package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private final Counter counter;
    private  boolean removed;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Brick(danogl.util.Vector2 topLeftCorner, danogl.util.Vector2 dimensions,
                 danogl.gui.rendering.Renderable renderable, CollisionStrategy strategy,
                 danogl.util.Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = strategy;
        this.counter = counter;
        this.removed=false;
    }
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (!removed && !(other instanceof WidePaddle)){
        super.onCollisionEnter(other, collision);
        counter.increment();

        collisionStrategy.onCollision(this, other, counter);
        removed=true;
        }



    }
}
