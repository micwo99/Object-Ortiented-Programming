package src.gameobjects;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    private TextRenderable text;
    private GameObject textObject;
    private final Counter livesCounter;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final GameObjectCollection gameObjectCollection;
    private final int num;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                              danogl.util.Vector2 topLeftCorner,
                              danogl.util.Vector2 dimensions,
                              danogl.collisions.GameObjectCollection gameObjectCollection){

        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.num =livesCounter.value();
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.gameObjectCollection = gameObjectCollection;
        text = new TextRenderable(String.format("%d", livesCounter.value()));
        textObject = new GameObject(topLeftCorner, dimensions, text);
        gameObjectCollection.addGameObject(textObject, Layer.BACKGROUND);


    }

    public int getLivesCounterValue(){
        return livesCounter.value();
    }
    public void decrementLivesCounter(){
        livesCounter.decrement();
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(livesCounter.value()!=num){
            gameObjectCollection.removeGameObject(textObject,Layer.BACKGROUND);
            text = new TextRenderable(String.format("%d", livesCounter.value()));
            textObject = new GameObject(topLeftCorner,dimensions, text);
            gameObjectCollection.addGameObject(textObject, Layer.BACKGROUND);
        }

    }
}
