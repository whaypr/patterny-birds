package cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;

public abstract class AbsGameInfo extends GameObject {

    protected IGameModel gameModel;

    public abstract double cannonAngle();
    public abstract int cannonPower();
    public abstract IShootingMode cannonShootingState();
    public abstract IMovingStrategy missilesMovingStrategy();
    public abstract int cannonDynamicShootingModeNumberOfMissiles();
    public abstract int score();
    public abstract int numberOfMissilesShot();
    public abstract int enemiesLeft();
}
