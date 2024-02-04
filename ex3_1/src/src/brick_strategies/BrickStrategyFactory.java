package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.Vector2;
import src.BrickerGameManager;



import java.util.Random;

public class BrickStrategyFactory {


    private final GameObjectCollection gameObjectCollection;
    private final SoundReader soundReader;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final BrickerGameManager gameManager;
    private final WindowController windowController;



    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions){
        this.gameObjectCollection=gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.gameManager = gameManager;
        this.windowController = windowController;

    }
    public CollisionStrategy getStrategy() {

        RemoveBrickStrategy removeBrickStrategy = new RemoveBrickStrategy(gameObjectCollection);
        Random random = new Random();

        CollisionStrategy [] collisionStrategies =
                {new WidePaddleStrategy(removeBrickStrategy,imageReader,windowDimensions),
                        new PuckStrategy(removeBrickStrategy,imageReader,soundReader),
                        new AddPaddleStrategy(removeBrickStrategy,imageReader,inputListener,windowDimensions),
                        new ChangeCameraStrategy(removeBrickStrategy,windowController,gameManager),
                        null,
                        removeBrickStrategy};

        int index = random.nextInt(collisionStrategies.length);
        if(index==0){
            return collisionStrategies[0];
        }
        if (index == 1) {
            return collisionStrategies[1];
        }

        if (index == 2) {
            return collisionStrategies[2];
        }
        if (index==3){
            return collisionStrategies[3];

        }
        if (index==4){
            int first =random.nextInt(4);
            int doubleOrTriple = random.nextInt(5);
            if (doubleOrTriple == 4) {
                int firstStrategy= random.nextInt(4);
                while(first==firstStrategy){
                    firstStrategy= random.nextInt(4);
                }
                int secondStrategy = random.nextInt(4);
                while (secondStrategy== firstStrategy|| secondStrategy==first){
                    secondStrategy = random.nextInt(4);

                }

                DoubleStrategy doubleStrategy =new DoubleStrategy(collisionStrategies[firstStrategy],
                        collisionStrategies[secondStrategy]);
                return new DoubleStrategy(collisionStrategies[first],doubleStrategy);
            }
            else{
                while (doubleOrTriple==first){
                    doubleOrTriple = random.nextInt(4);
                }
                return new DoubleStrategy(collisionStrategies[first],
                        collisionStrategies[doubleOrTriple]);
            }
        }
        return removeBrickStrategy;
    }

}
