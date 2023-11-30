package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;

public class MissileA extends AbsMissile {

    private final IMovingStrategy movingStrategy;

    public MissileA(Position initPosition, double initAngle, int initVelocity, IMovingStrategy movingStrategy) {
        super(initPosition, initAngle, initVelocity);
        this.movingStrategy = movingStrategy;
    }

    @Override
    public void move() {
        this.movingStrategy.updatePosition(this);
    }
}
