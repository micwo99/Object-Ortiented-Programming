package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

public class DoubleStrategy extends RemoveBrickStrategyDecorator{
    private CollisionStrategy secondStrategy;

    public DoubleStrategy(CollisionStrategy firstStrategy,
                          CollisionStrategy secondStrategy) {
        super(firstStrategy);
        this.secondStrategy = secondStrategy;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj,otherObj,counter);
        secondStrategy.onCollision(thisObj,otherObj,counter);


    }
}
