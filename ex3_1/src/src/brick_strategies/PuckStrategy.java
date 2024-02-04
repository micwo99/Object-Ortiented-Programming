package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;
import java.util.Random;


public class PuckStrategy extends RemoveBrickStrategyDecorator {

    private final ImageReader imageReader;
    private final Sound soundCollision;
    private final float BALL_SPEED=150;


    public PuckStrategy(CollisionStrategy toBeDecorated,
                         danogl.gui.ImageReader imageReader,
                         danogl.gui.SoundReader soundReader){
        super(toBeDecorated);
        this.imageReader = imageReader;
        soundCollision =soundReader.readSound("assets/blop.wav");
    }
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj,Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable ballImage = imageReader.readImage("assets/mockBall.png", true);
        Random random = new Random() {
        };
        for (int i = 0; i < 3; i++) {
            int first=-100, second=100;
            int velocity= random.nextInt((second-first+1)+first);;
            float divide=(float) 1/3;

            Vector2 dim= new Vector2(thisObj.getDimensions().x()*divide,thisObj.getDimensions().x()*divide);
            Puck puck = new Puck(thisObj.getCenter(), dim, ballImage, soundCollision);

            puck.setVelocity(new Vector2(velocity, BALL_SPEED));
            getGameObjectCollection().addGameObject(puck);

        }
    }



}
