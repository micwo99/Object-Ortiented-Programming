package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.MockPaddle;

import java.util.Random;

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions){
        super(toBeDecorated);
        this.imageReader=imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {

        super.onCollision(thisObj,otherObj,counter);
        Random random= new Random();
        int first= BrickerGameManager.BORDER_WIDTH;
        int second = (int)windowDimensions.x()-BrickerGameManager.BORDER_WIDTH;
        float dim_y = windowDimensions.y()/2;
        int flag2 = random.nextInt((second-first+1)+first);
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        if(!MockPaddle.isInstantiated){
            MockPaddle Pad= new MockPaddle(new Vector2(flag2,dim_y),new Vector2(150, 20),paddleImage,inputListener,
                    windowDimensions,getGameObjectCollection(),BrickerGameManager.BORDER_WIDTH,3);
            getGameObjectCollection().addGameObject(Pad);
        }

    }
}
