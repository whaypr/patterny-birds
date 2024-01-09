package cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;

public class MissileA extends AbsMissile {

    private final IMovingStrategy movingStrategy;

    public MissileA(Position initPosition, double initAngle, int initVelocity, long lifeTime, IMovingStrategy movingStrategy) {
        super(initPosition, initAngle, initVelocity, lifeTime);
        this.movingStrategy = movingStrategy;
    }

    public MissileA(MissileA other) {
        super(other);
        this.movingStrategy = other.movingStrategy;
    }

    @Override
    public void move() {
        this.movingStrategy.updatePosition(this);
    }

    @Override
    public AbsMissile clone() {
        return new MissileA(this);
    }
}
