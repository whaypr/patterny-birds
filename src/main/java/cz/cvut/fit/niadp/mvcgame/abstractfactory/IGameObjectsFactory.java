package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;

public interface IGameObjectsFactory {
    AbsCannon createCannon();
    AbsMissile createMissile(double initAngle, int initVelocity);
    // TODO enemies, gameInfo
}
