package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public class GameInfo extends AbsGameInfo{

    public GameInfo(IGameModel model) {
        this.gameModel = model;
    }

    @Override
    public double cannonAngle() {
        return this.gameModel.getCannonAngle();
    }

    @Override
    public int cannonPower() {
        return this.gameModel.getCannonPower();
    }

    @Override
    public IShootingMode cannonShootingState() {
        return this.gameModel.getCannonShootingMode();
    }

    @Override
    public IMovingStrategy missilesMovingStrategy() {
        return this.gameModel.getMissileMovingStrategy();
    }

    @Override
    public int cannonDynamicShootingModeNumberOfMissiles() {
        return this.gameModel.getCannonDynamicShootingModeNumberOfMissiles();
    }

    @Override
    public int score() {
        return this.gameModel.getScore();
    }

    @Override
    public int numberOfMissilesShot() {
        return this.gameModel.getNumberOfMissilesShot();
    }

    @Override
    public int enemiesLeft() {
        return this.gameModel.getNumberOfEnemiesLeft();
    }

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitGameInfo(this);
    }
}
