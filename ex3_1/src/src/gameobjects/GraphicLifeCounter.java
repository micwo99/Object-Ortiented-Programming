package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;
import danogl.collisions.GameObjectCollection;


public class GraphicLifeCounter extends GameObject {
    private final GameObjectCollection gameObjectCollection;
    private final Counter livesCounter;
    private final int numOfLives;
    private final GameObject[] lives;




public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner, danogl.util.Vector2 widgetDimensions,
                          danogl.util.Counter livesCounter, danogl.gui.rendering.Renderable widgetRenderable,
                          danogl.collisions.GameObjectCollection gameObjectsCollection, int numOfLives)
    {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.gameObjectCollection = gameObjectsCollection;
        this.livesCounter =livesCounter;
        this.numOfLives = numOfLives;
        this.livesCounter.increaseBy(numOfLives);
        this.lives = new GameObject[numOfLives];
        for (int heart = 0; heart <livesCounter.value() ; heart++) {
            GameObject life = new GameObject(widgetTopLeftCorner,widgetDimensions, widgetRenderable);
            gameObjectCollection.addGameObject(life, Layer.BACKGROUND);
            lives[heart]=life;
            widgetTopLeftCorner = widgetTopLeftCorner.add(new Vector2(35,0));
        }
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(livesCounter.value()!=numOfLives){
            gameObjectCollection.removeGameObject(lives[livesCounter.value()],Layer.BACKGROUND);
        }



    }
}
