package cz.cvut.fit.niadp.mvcgame.proxy;

import cz.cvut.fit.niadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;

import java.util.List;

public class GameModelProxy implements IGameModel {

    private IGameModel subject;

    public GameModelProxy(IGameModel model) {
        this.subject = model;
    }

    @Override
    public void update() {
        this.subject.update();
    }

    @Override
    public void registerCommand(AbstractGameCommand command) {
        this.subject.registerCommand(command);
    }

    @Override
    public void undoLastCommand() {
        this.subject.undoLastCommand();
    }

    @Override
    public void moveCannonUp() {
        this.subject.moveCannonUp();
    }

    @Override
    public void moveCannonDown() {
        this.subject.moveCannonDown();
    }

    @Override
    public void aimCannonUp() {
        this.subject.aimCannonUp();
    }

    @Override
    public void aimCannonDown() {
        this.subject.aimCannonDown();
    }

    @Override
    public void cannonPowerUp() {
        this.subject.cannonPowerUp();
    }

    @Override
    public void cannonPowerDown() {
        this.subject.cannonPowerDown();
    }

    @Override
    public void cannonShoot() {
        this.subject.cannonShoot();
    }

    @Override
    public void toggleShootingMode() {
        this.subject.toggleShootingMode();
    }

    @Override
    public void toggleMovingStrategy() {
        this.subject.toggleMovingStrategy();
    }

    @Override
    public void addMissilesForDynamicShootingMode(int toAdd) {
        this.subject.addMissilesForDynamicShootingMode(toAdd);
    }

    @Override
    public void removeMissilesForDynamicShootingMode(int toRemove) {
        this.subject.removeMissilesForDynamicShootingMode(toRemove);
    }

    @Override
    public void registerObserver(IObserver obs, Aspect aspect) {
        this.subject.registerObserver(obs, aspect);
    }

    @Override
    public void unregisterObserver(IObserver obs, Aspect aspect) {
        this.subject.unregisterObserver(obs, aspect);
    }

    @Override
    public void notifyObservers(Aspect aspect) {
        this.subject.notifyObservers(aspect);
    }

    @Override
    public int getScore() {
        return this.subject.getScore();
    }

    @Override
    public int getNumberOfMissilesShot() {
        return this.subject.getNumberOfMissilesShot();
    }

    @Override
    public int getNumberOfEnemiesLeft() {
        return this.subject.getNumberOfEnemiesLeft();
    }

    @Override
    public List<GameObject> getGameObjects() {
        return this.subject.getGameObjects();
    }

    @Override
    public Position getCannonPosition() {
        return this.subject.getCannonPosition();
    }

    @Override
    public double getCannonAngle() {
        return this.subject.getCannonAngle();
    }

    @Override
    public int getCannonPower() {
        return this.subject.getCannonPower();
    }

    @Override
    public IShootingMode getCannonShootingMode() {
        return this.subject.getCannonShootingMode();
    }

    @Override
    public int getCannonDynamicShootingModeNumberOfMissiles() {
        return this.subject.getCannonDynamicShootingModeNumberOfMissiles();
    }

    @Override
    public List<AbsMissile> getMissiles() {
        return this.subject.getMissiles();
    }

    @Override
    public IMovingStrategy getMissileMovingStrategy() {
        return this.subject.getMissileMovingStrategy();
    }

    @Override
    public Object createMemento() {
        return this.subject.createMemento();
    }

    @Override
    public void setMemento(Object memento) {
        this.subject.setMemento(memento);
    }
}
