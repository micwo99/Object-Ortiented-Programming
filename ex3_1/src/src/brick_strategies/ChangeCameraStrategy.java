package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;


public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{

    private final BrickerGameManager gameManager;
    private final WindowController windowController;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;
    private Ball ball;

    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;

    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        for (GameObject gameObject : getGameObjectCollection()) {
            if (gameObject instanceof Ball) {
                ball = (Ball) gameObject;
                break;
            }

        }
        super.onCollision(thisObj,otherObj,counter);
        if(gameManager.getCamera()==null){
            gameManager.setCamera(new Camera(ball,Vector2.ZERO, windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
            if(otherObj instanceof Ball){
                ballCollisionCountdownAgent= new BallCollisionCountdownAgent(ball,this,5);

            }
            else{
                ballCollisionCountdownAgent= new BallCollisionCountdownAgent(ball,this,4);


            }
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent, Layer.BACKGROUND);
        }


    }
    public void turnOffCameraChange(){

        gameManager.setCamera(null);
        getGameObjectCollection().removeGameObject(ballCollisionCountdownAgent,Layer.BACKGROUND);
    }
}
