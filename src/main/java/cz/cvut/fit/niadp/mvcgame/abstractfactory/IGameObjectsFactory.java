package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsGameInfo;

public interface IGameObjectsFactory {
    AbsCannon createCannon();
    AbsMissile createMissile(double initAngle, int initVelocity, long lifeTime);
    //Enemy createEnemy(Position position);
    AbsGameInfo createGameInfo();
}
