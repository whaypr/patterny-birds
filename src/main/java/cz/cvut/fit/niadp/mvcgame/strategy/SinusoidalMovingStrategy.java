package cz.cvut.fit.niadp.mvcgame.strategy;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.state.SingleShootingMode;

public class SinusoidalMovingStrategy implements IMovingStrategy {

    @Override
    public String getName() {
        return SinusoidalMovingStrategy.class.getSimpleName()
                .replace("MovingStrategy", "");
    }

    @Override
    public void updatePosition(AbsMissile missile) {
        int initVelocity = missile.getInitVelocity();
        double initAngle = missile.getInitAngle();
        long time = missile.getAge() / 100;

        int amplitude = 10;
        var x = time;
        var y = amplitude * Math.sin(time);

        int dX = (int)(((x * Math.cos(initAngle)) - (y * Math.sin(initAngle))) * initVelocity / 20.);
        int dY = (int)(((x * Math.sin(initAngle)) + (y * Math.cos(initAngle))) * initVelocity / 20.);

        missile.move(new Vector(dX, dY));
    }
}
