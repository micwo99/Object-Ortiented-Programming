package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import  danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    public static final int MOVE_SPEED = 300 ;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistanceFromEdge;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */


    public Paddle(danogl.util.Vector2 topLeftCorner,
                  danogl.util.Vector2 dimensions,
                  danogl.gui.rendering.Renderable renderable,
                  danogl.gui.UserInputListener inputListener,
                  danogl.util.Vector2 windowDimensions,
                  int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener= inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistFromEdge;
    }

    @Override
    public void update(float deltaTime) {
        Vector2 movementDir= Vector2.ZERO;
        super.update(deltaTime);
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVE_SPEED));
        if(minDistanceFromEdge > getTopLeftCorner().x())
        {
            setTopLeftCorner(new Vector2(minDistanceFromEdge, getTopLeftCorner().y()));

        }
        if(getTopLeftCorner().x()> windowDimensions.x() - minDistanceFromEdge - getDimensions().x()){
            setTopLeftCorner(new Vector2(windowDimensions.x() - minDistanceFromEdge - getDimensions().x()
                                                                                        ,getTopLeftCorner().y()));
        }
    }

}
