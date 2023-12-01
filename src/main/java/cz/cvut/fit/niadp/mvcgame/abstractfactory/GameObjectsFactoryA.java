package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.CannonA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.MissileA;

public class GameObjectsFactoryA implements IGameObjectsFactory {
    private static GameModel model;
    // volatile so that double check lock would work correctly.
    private static volatile GameObjectsFactoryA instance;

    private GameObjectsFactoryA() {}

    public static void createInstance(GameModel m) {
        model = m;

        synchronized(GameObjectsFactoryA.class) {
            if (instance == null) {
                instance = new GameObjectsFactoryA();
            }
        }
    }

    public static GameObjectsFactoryA getInstance() {
        if (instance == null)
            throw new RuntimeException("The model must be initialized before getting the instance!");
        return instance;
    }

    @Override
    public CannonA createCannon() {
        return new CannonA(new Position(MvcGameConfig.CANNON_POS_X, MvcGameConfig.CANNON_POS_Y), this);
    }

    @Override
    public MissileA createMissile(double initAngle, int initVelocity) {
        return new MissileA(
                new Position(this.model.getCannonPosition().getX(), this.model.getCannonPosition().getY()),
                initAngle,
                initVelocity,
                this.model.getMovingStrategy()
        );
    }
}
