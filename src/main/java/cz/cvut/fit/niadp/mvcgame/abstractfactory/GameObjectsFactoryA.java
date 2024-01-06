package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.CannonA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.MissileA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyType;

public class GameObjectsFactoryA implements IGameObjectsFactory {
    private final IGameModel model;

    private static GameObjectsFactoryA instance;

    private GameObjectsFactoryA(IGameModel model) {
        this.model = model;
    }

    public static void createInstance(IGameModel model) {
        synchronized(GameObjectsFactoryA.class) {
            if (instance == null) {
                instance = new GameObjectsFactoryA(model);
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
    public MissileA createMissile(double initAngle, int initVelocity, long lifeTime) {
        return new MissileA(
                new Position(this.model.getCannonPosition().getX(), this.model.getCannonPosition().getY()),
                initAngle,
                initVelocity,
                lifeTime,
                this.model.getMissileMovingStrategy()
        );
    }

    @Override
    public AbsEnemy createEnemy(Position position, EnemyType type) {
        return new EnemyA(
                position,
                type
        );
    }

    @Override
    public AbsGameInfo createGameInfo() {
        return new GameInfo(this.model);
    }
}
