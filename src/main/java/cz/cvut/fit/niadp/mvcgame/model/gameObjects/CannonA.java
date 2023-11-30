package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.Vector;

public class CannonA extends AbsCannon {

    private IGameObjectsFactory gameObjectsFactory;

    public CannonA(Position initPosition, IGameObjectsFactory gameObjectsFactory) {
        this.position = initPosition;
        this.gameObjectsFactory = gameObjectsFactory;

        this.power = MvcGameConfig.INIT_POWER;
        this.angle = MvcGameConfig.INIT_ANGLE;
    }

    @Override
    public void moveUp() {
        this.move(new Vector(0, -MvcGameConfig.MOVE_STEP));
    }

    @Override
    public void moveDown() {
        this.move(new Vector(0, MvcGameConfig.MOVE_STEP));
    }

    @Override
    public void aimUp() {
        this.angle -= MvcGameConfig.ANGLE_STEP;
    }

    @Override
    public void aimDown() {
        this.angle += MvcGameConfig.ANGLE_STEP;
    }

    @Override
    public void powerUp() {
        this.power += MvcGameConfig.POWER_STEP;
    }

    @Override
    public void powerDown() {
        this.power -= MvcGameConfig.POWER_STEP;
    }

    @Override
    public AbsMissile shoot() {
        return this.gameObjectsFactory.createMissile(this.angle, this.power);
    }
}
