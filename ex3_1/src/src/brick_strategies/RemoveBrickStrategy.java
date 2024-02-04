package src.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjectCollection;

    RemoveBrickStrategy(GameObjectCollection gameObjectCollection){

        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {

        gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }
}
