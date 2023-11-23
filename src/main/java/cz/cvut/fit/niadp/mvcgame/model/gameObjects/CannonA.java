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
    public AbsMissile shoot() {
        return this.gameObjectsFactory.createMissile();
    }
}
