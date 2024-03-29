package src.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle  extends Paddle {
    private final GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;
    public static boolean isInstantiated=false;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener
     * @param windowDimensions
     * @param minDistanceFromEdge
     */

    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                      Vector2 windowDimensions, GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear){
        super(topLeftCorner,dimensions,renderable,inputListener,windowDimensions,minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.isInstantiated = true;

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {
            numCollisionsToDisappear--;
        }
        if(numCollisionsToDisappear==0){
            isInstantiated=false;
            gameObjectCollection.removeGameObject(this);
        }


    }


}
