package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyType;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public interface IGameObjectsFactory {
    AbsCannon createCannon();
    AbsMissile createMissile(double initAngle, int initVelocity, long lifeTime);
    AbsEnemy createEnemy(Position position, EnemyType type);
    AbsWall createWall(Position position);
    AbsGameInfo createGameInfo();
}
