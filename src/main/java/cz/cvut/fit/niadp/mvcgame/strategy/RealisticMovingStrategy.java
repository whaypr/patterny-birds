package cz.cvut.fit.niadp.mvcgame.strategy;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;

public class RealisticMovingStrategy implements IMovingStrategy {

    @Override
    public String getName() {
        return RealisticMovingStrategy.class.getSimpleName()
                .replace("MovingStrategy", "");
    }

    @Override
    public void updatePosition(AbsMissile missile) {
        int initVelocity = missile.getInitVelocity();
        double initAngle = missile.getInitAngle();
        long time = missile.getAge() / 100;

        int dX = (int) (initVelocity * Math.cos(initAngle));
        int dY = (int) (initVelocity * Math.sin(initAngle) + (0.5 * MvcGameConfig.GRAVITY * time * time));

        missile.move(new Vector(dX, dY));
    }
}
