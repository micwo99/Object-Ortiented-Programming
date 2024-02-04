package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.util.Counter;
import danogl.util.Vector2;
public class Ball extends GameObject{

    private final Sound collisionSound;
    private final Counter counter;

    public Ball(danogl.util.Vector2 topLeftCorner, danogl.util.Vector2 dimensions,
                danogl.gui.rendering.Renderable renderable, danogl.gui.Sound sound){
        super(topLeftCorner,dimensions,renderable);
        this.collisionSound = sound;
        counter= new Counter();

    }
    @Override
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision) {
        if (!(other instanceof Ball)  && !(other instanceof WidePaddle)) {
            super.onCollisionEnter(other, collision);
            counter.increment();
            Vector2 newVel = getVelocity().flipped(collision.getNormal());
            setVelocity(newVel);
            collisionSound.play();
        }
    }
    public int getCollisionCount(){return counter.value();}
}
