package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.CannonA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.MissileA;

public class GameObjectsFactoryA implements IGameObjectsFactory {
    private GameModel model;
    private static IGameObjectsFactory instance;

    public GameObjectsFactoryA(GameModel model) {
        // TODO Singleton task
        this.model = model;
    }

    public GameObjectsFactoryA() {
        // TODO Singleton task
    }

    public static IGameObjectsFactory getInstance() {
        // TODO Singleton task
        return new GameObjectsFactoryA();
    }

    @Override
    public CannonA createCannon() {
        return new CannonA(new Position(MvcGameConfig.CANNON_POS_X, MvcGameConfig.CANNON_POS_Y), this);
    }

    @Override
    public MissileA createMissile() {
        return new MissileA(new Position(this.model.getCannonPosition().getX(), this.model.getCannonPosition().getY()));
    }
}
