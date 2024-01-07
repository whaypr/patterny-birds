package cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionResponse;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.LifetimeLimitedGameObject;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollidable;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsMissile extends LifetimeLimitedGameObject implements ICollidable {

    private final double initAngle;
    private final int initVelocity;
    private ICollisionChecker collisionChecker;

    protected AbsMissile(Position initPosition, double initAngle, int initVelocity, long lifeTime) {
        super(initPosition, lifeTime);
        this.initAngle = initAngle;
        this.initVelocity = initVelocity;
        this.collisionChecker = new CollisionChecker(
                this, MvcGameConfig.MISSILE_HITBOX,
                CollisionResponse.IGNORE, CollisionResponse.DESTROY, CollisionResponse.DESTROY
        );
    }

    protected AbsMissile(AbsMissile other) {
        super(other.position, other.lifeTime - other.getAge());
        this.initAngle = other.getInitAngle();
        this.initVelocity = other.getInitVelocity();
        this.collisionChecker = other.collisionChecker;
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
    public ICollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }

    public abstract AbsMissile clone();
}
