package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyType;

public interface IGameObjectsFactory {
    AbsCannon createCannon();
    AbsMissile createMissile(double initAngle, int initVelocity, long lifeTime);
    AbsEnemy createEnemy(Position position, EnemyType type);
    AbsGameInfo createGameInfo();
}
