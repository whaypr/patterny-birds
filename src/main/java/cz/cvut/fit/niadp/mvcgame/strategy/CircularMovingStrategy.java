package cz.cvut.fit.niadp.mvcgame.strategy;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;

public class CircularMovingStrategy implements IMovingStrategy {

    @Override
    public String getName() {
        return CircularMovingStrategy.class.getSimpleName()
                .replace("MovingStrategy", "");
    }

    @Override
    public void updatePosition(AbsMissile missile) {
        int initVelocity = missile.getInitVelocity();
        double initAngle = missile.getInitAngle();
        long time = missile.getAge() / 100;

        int angularSpeer = 100;
        long angle = angularSpeer * time;

        int dX = (int)(Math.cos(angle + initAngle) * time * initVelocity * .025);
        int dY = (int)(Math.sin(angle + initAngle) * time * initVelocity * .025);

        missile.move(new Vector(dX, dY));
    }
}
