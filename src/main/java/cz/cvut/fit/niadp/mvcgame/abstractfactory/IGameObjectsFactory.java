package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;

public interface IGameObjectsFactory {
    AbsCannon createCannon();
    // TODO enemies, gameInfo
    AbsMissile createMissile(double initAngle, int initVelocity, long lifeTime);
}
