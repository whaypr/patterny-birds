package cz.cvut.fit.niadp.mvcgame.strategy;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;

public interface IMovingStrategy {
    void updatePosition(AbsMissile missile);
}
