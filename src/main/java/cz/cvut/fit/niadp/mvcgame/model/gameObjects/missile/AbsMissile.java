package cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.LifetimeLimitedGameObject;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.collisions.CollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.collisions.ICollidable;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsMissile extends LifetimeLimitedGameObject implements ICollidable {

    private final double initAngle;
    private final int initVelocity;
    private final CollisionChecker collisionChecker;

    protected AbsMissile(Position initPosition, double initAngle, int initVelocity, long lifeTime) {
        super(initPosition, lifeTime);
        this.initAngle = initAngle;
        this.initVelocity = initVelocity;
        this.collisionChecker = new CollisionChecker(this, MvcGameConfig.MISSILE_HITBOX);
    }

    public abstract void move();

    public double getInitAngle() {
        return this.initAngle;
    }

    public int getInitVelocity() {
        return this.initVelocity;
    }

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitMissile(this);
    }

    @Override
    public CollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }
}
