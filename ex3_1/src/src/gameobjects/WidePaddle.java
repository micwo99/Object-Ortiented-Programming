package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


public class WidePaddle extends GameObject {

    private final GameObjectCollection gameObjectCollection;
    private final Vector2 paddleDimension;
    private final Vector2 windowsDimension;



    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public WidePaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      GameObjectCollection gameObjectCollection, Vector2 paddleDimension, Vector2 windowsDimension) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.paddleDimension = paddleDimension;
        this.windowsDimension = windowsDimension;

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision)
    {
        if(other instanceof Paddle && other.getCenter().y()==windowsDimension.y()- 30){
            gameObjectCollection.removeGameObject(this);
            other.setDimensions(paddleDimension);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.getCenter().y()>=windowsDimension.y()){
            gameObjectCollection.removeGameObject(this);

        }

    }
}
