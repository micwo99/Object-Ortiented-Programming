package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.gameobjects.*;
import java.util.Random;


public class BrickerGameManager extends GameManager {

    public static final int BORDER_WIDTH = 20;
    private static final float BALL_SPEED = 150;
    private static final int NUM_BRICK = 7;
    private static final int LINE_BRICKS = 8;
    private static final int DIM_X = 700;
    private static final int DIM_Y = 500;
    private static final float BALL_SIZE = 30;
    private static final float BRICK_SIZE = 30;
    private static final Vector2 PADDLE_DIMENSION  = new Vector2(150, 20);

    private Ball ball;
    private Counter counterBrick;
    private WindowController windowController;
    private NumericLifeCounter count;
    private SoundReader soundReader;
    private Paddle paddle;
    private Vector2 windowDimensions;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    private void initializeWall(Vector2 windowDimensions) {
        gameObjects().addGameObject(new GameObject(new Vector2(DIM_X - BORDER_WIDTH, 0), new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null),Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH),
                null),Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null),Layer.STATIC_OBJECTS);

    }
    private void initializeBall(Vector2 windowDimensions,ImageReader imageReader,SoundReader soundReader) {
        this.windowDimensions = windowDimensions;


        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED));
        repositionBall(ball);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        gameObjects().addGameObject(ball);

    }
    private void initializePaddle(ImageReader imageReader,UserInputListener inputListener,Vector2 windowDimensions ){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        paddle = new Paddle(Vector2.ZERO, PADDLE_DIMENSION, paddleImage, inputListener,
                windowDimensions,  BORDER_WIDTH);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);
    }
    private void initializeBricks(ImageReader imageReader, Vector2 windowDimensions
    ,UserInputListener inputListener){
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        counterBrick = new Counter();
        float dim_x = ((DIM_X - (BORDER_WIDTH * 2)) - (NUM_BRICK)) / (float) NUM_BRICK;
        float beginX = BORDER_WIDTH;
        float beginY = BORDER_WIDTH;
        int line = 0;
        while (line < LINE_BRICKS) {
            for (int col = 0; col < NUM_BRICK; col++) {
                GameObjectCollection gameObjectCollection = gameObjects();
                BrickStrategyFactory strategy= new BrickStrategyFactory(gameObjectCollection,this,
                        imageReader,soundReader,inputListener,windowController,windowDimensions);
                GameObject brick = new Brick(new Vector2(beginX, beginY), new Vector2(dim_x, BRICK_SIZE), brickImage,
                        strategy.getStrategy(), counterBrick);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                beginX += 1 + dim_x;
            }
            line++;
            beginY += 1 + BRICK_SIZE;
            beginX = BORDER_WIDTH;

        }

    }
    private void initializeBackground(ImageReader imageReader){
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

    }
    private void initializeCounter(Counter livesCounter){
        count = new NumericLifeCounter(livesCounter, new Vector2(
                30, DIM_Y - 100), new Vector2(30, 30)
                , gameObjects());
        gameObjects().addGameObject(count, Layer.BACKGROUND);

    }
    private  void initializeGraphicCounter(ImageReader imageReader,Counter livesCounter){
        Renderable heart = imageReader.readImage("assets/heart.png", true);
        GraphicLifeCounter life = new GraphicLifeCounter(new Vector2(55, DIM_Y - 100), new Vector2(30, 30),
                livesCounter, heart, gameObjects(), 3);
        gameObjects().addGameObject(life, Layer.BACKGROUND);
        initializeCounter(livesCounter);

    }
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.soundReader = soundReader;
        this.windowController = windowController;
        Counter livesCounter = new Counter();
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowController.setTargetFramerate(80);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        initializeBall(windowDimensions,imageReader,soundReader);
        initializePaddle(imageReader,inputListener,windowDimensions);
        initializeWall(windowDimensions);
        initializeBricks(imageReader,windowDimensions,inputListener);
        initializeBackground(imageReader);
        initializeCounter(livesCounter);
        initializeGraphicCounter(imageReader,livesCounter);
    }

    private void removePuck(){
        for (GameObject gameObject : gameObjects()) {
            if (gameObject instanceof Ball) {
                if (gameObject.getCenter().y() > windowDimensions.y()) {
                    gameObjects().removeGameObject(gameObject);
                } else if (gameObject.getCenter().y() < 0) {
                    gameObjects().removeGameObject(gameObject);
                } else if (gameObject.getCenter().x() > windowDimensions.x()) {
                    gameObjects().removeGameObject(gameObject);
                } else if (gameObject.getCenter().x() < 0) {
                    gameObjects().removeGameObject(gameObject);
                }


            }
        }
    }
    private void removeBallCollisionCount(){
        boolean isInstanced=false;
        BallCollisionCountdownAgent ballCollisionCountdownAgent = null;
        for (GameObject gameObject : gameObjects()) {
            if (gameObject instanceof BallCollisionCountdownAgent) {
                ballCollisionCountdownAgent= (BallCollisionCountdownAgent)gameObject;
                isInstanced=true;
                break;
            }
        }
        if (isInstanced && ball.getCenter().y()>= windowDimensions.y()){
            gameObjects().removeGameObject(ballCollisionCountdownAgent);


        }
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        String prompt ;
        double height = ball.getCenter().y();
        if (height >paddle.getCenter().y()+30 ){
            count.decrementLivesCounter();
            if (count.getLivesCounterValue()== 0) {
                prompt = "You Loose\nPlay Again?";
                if (windowController.openYesNoDialog(prompt)) {
                    windowController.resetGame();
                } else windowController.closeWindow();
            }
            else {
                this.setCamera(null);
                repositionBall(ball);

                }

        }

        if (counterBrick.value() == NUM_BRICK * LINE_BRICKS) {
            prompt = "You win\n";
            prompt += "Play Again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else windowController.closeWindow();
        }
        removePuck();
        removeBallCollisionCount();

    }

    void repositionBall(Ball ball){
        ball.setCenter(windowDimensions.mult(0.8F));

    }
    public static void main(String[] args) {
        new BrickerGameManager("Bouncing Ball", new Vector2(DIM_X, DIM_Y)).run();

    }
}