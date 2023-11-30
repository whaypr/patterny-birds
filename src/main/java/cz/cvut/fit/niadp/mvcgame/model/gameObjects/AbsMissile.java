package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsMissile extends LifetimeLimitedGameObject {

    private final double initAngle;
    private final int initVelocity;

    protected AbsMissile(Position initPosition, double initAngle, int initVelocity) {
        super(initPosition);
        this.initAngle = initAngle;
        this.initVelocity = initVelocity;
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
}
