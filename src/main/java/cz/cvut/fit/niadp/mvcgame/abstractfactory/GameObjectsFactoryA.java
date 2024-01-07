package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.decorator.IgnoreEnemyCollisionsDecorator;
import cz.cvut.fit.niadp.mvcgame.decorator.IgnoreWallCollisionsDecorator;
import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.CannonA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo.GameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.MissileA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyA;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyType;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.WallA;

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
        MissileA missile = new MissileA(
                new Position(this.model.getCannonPosition().getX(), this.model.getCannonPosition().getY()),
                initAngle,
                initVelocity,
                lifeTime,
                this.model.getMissileMovingStrategy()
        );

        ICollisionChecker cc = missile.getCollisionChecker();

        if (this.model.getMissilesWallPiercing())
            cc = new IgnoreWallCollisionsDecorator(cc);
        if (this.model.getMissilesEnemyPiercing())
            cc = new IgnoreEnemyCollisionsDecorator(cc);
        missile.setCollisionChecker(cc);

        return missile;
    }

    @Override
    public AbsEnemy createEnemy(Position position, EnemyType type) {
        return new EnemyA(
                position,
                type
        );
    }

    @Override
    public AbsWall createWall(Position position) {
        return new WallA(position);
    }

    @Override
    public AbsGameInfo createGameInfo() {
        return new GameInfo(this.model);
    }
}
