package cz.cvut.fit.niadp.mvcgame.strategy;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;

public class SimpleMovingStrategy implements IMovingStrategy {
    @Override
    public void updatePosition(AbsMissile missile) {
        int initVelocity = missile.getInitVelocity();
        double initAngle = missile.getInitAngle();

        int dX = (int) (initVelocity * Math.cos(initAngle));
        int dY = (int) (initVelocity * Math.sin(initAngle));

        missile.move(new Vector(dX, dY));
    }
}
