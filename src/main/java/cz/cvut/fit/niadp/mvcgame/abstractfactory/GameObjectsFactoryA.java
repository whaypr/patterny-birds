package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.CannonA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.MissileA;

public class GameObjectsFactoryA implements IGameObjectsFactory {
    private GameModel model;
    // volatile so that double check lock would work correctly.
    private static volatile GameObjectsFactoryA instance;


    private GameObjectsFactoryA(GameModel model) {
        this.model = model;
    }

    public static GameObjectsFactoryA getInstance(GameModel model) {
        // double-checked locking
        GameObjectsFactoryA result = instance;
        if (result != null) {
            return result;
        }
        synchronized(GameObjectsFactoryA.class) {
            if (instance == null) {
                instance = new GameObjectsFactoryA(model);
            }
            return instance;
        }
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
