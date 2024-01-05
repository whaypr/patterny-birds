package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.observer.IObservable;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;

import java.util.List;

public interface IGameModel extends IObservable {
    void update();

    /* COMMANDS */
    void registerCommand(AbstractGameCommand command);
    void undoLastCommand();
    void moveCannonUp();
    void moveCannonDown();
    void aimCannonUp();
    void aimCannonDown();
    void cannonPowerUp();
    void cannonPowerDown();
    void cannonShoot();
    void toggleShootingMode();
    void toggleMovingStrategy();
    void addMissilesForDynamicShootingMode(int toAdd);
    void removeMissilesForDynamicShootingMode(int toRemove);

    /* INFO */
    List<GameObject> getGameObjects();
    Position getCannonPosition();
    List<AbsMissile> getMissiles();
    IMovingStrategy getMissileMovingStrategy();

    /* MEMENTO */
    Object createMemento();
    void setMemento(Object memento);
}
