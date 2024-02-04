package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject{


    private final int numOfCollisionToDo;
    private final Ball ball;
    private final ChangeCameraStrategy owner;



    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(Vector2.ZERO,Vector2.ZERO,null);
        this.ball = ball;
        this.owner = owner;
        this.numOfCollisionToDo=countDownValue+ ball.getCollisionCount();
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(numOfCollisionToDo <= ball.getCollisionCount()){
            owner.turnOffCameraChange();

        }
    }
}
