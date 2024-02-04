package src.brick_strategies;

import danogl.GameObject;

import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.WidePaddle;


import java.util.Random;

public class WidePaddleStrategy extends RemoveBrickStrategyDecorator {
    private final ImageReader imageReader;
    private final Vector2 windowDimension;
    private final int BALL_SPEED=150;

    public WidePaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,Vector2 windowDimension) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowDimension = windowDimension;
    }


    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj,otherObj,counter);
        super.onCollision(thisObj,otherObj,counter);
        Random random= new Random();
        Renderable widenImage = imageReader.readImage("assets/buffWiden.png",true);
        Renderable narrowImage = imageReader.readImage("assets/buffNarrow.png",true);
        boolean flag = random.nextBoolean();
        Vector2 widenVector=new Vector2(200,20);
        Vector2 narrowVector= new Vector2(100,20);
        WidePaddle growthPaddle;
        if(flag){

            growthPaddle = new WidePaddle(thisObj.getTopLeftCorner().add(Vector2.DOWN),
                                                    thisObj.getDimensions(),
                                                    widenImage,
                                                    getGameObjectCollection(),
                                                    widenVector,windowDimension);
        }
        else{

            growthPaddle= new WidePaddle(thisObj.getTopLeftCorner(),thisObj.getDimensions(),
                    narrowImage,getGameObjectCollection(),narrowVector,windowDimension);


        }
        growthPaddle.setVelocity(new Vector2(0, (float) (BALL_SPEED*1.2)));
        getGameObjectCollection().addGameObject(growthPaddle);


    }


}