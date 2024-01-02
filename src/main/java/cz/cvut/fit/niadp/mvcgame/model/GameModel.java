package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.LifetimeLimitedGameObject;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;

import java.util.*;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.strategy.*;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsSoundMaker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class GameModel implements IGameModel {

    private final GameObjectsSoundMaker soundMaker;

    private final Map<Aspect, Set<IObserver>> observers;
    private IGameObjectsFactory gameObjectsFactory;

    private final AbsCannon cannon;
    private final List<AbsMissile> missiles;
    private IMovingStrategy movingStrategy;

    private final Queue<AbstractGameCommand> unexecutedCommands;
    private final Stack<AbstractGameCommand> executedCommands;

    public GameModel(GameObjectsSoundMaker soundMaker) {
        this.observers = new HashMap<>();
        GameObjectsFactoryA.createInstance(this);
        this.gameObjectsFactory = GameObjectsFactoryA.getInstance();
        this.cannon = this.gameObjectsFactory.createCannon();
        this.missiles = new ArrayList<>();
        this.movingStrategy = new SimpleMovingStrategy();
        this.soundMaker = soundMaker;
        this.unexecutedCommands = new LinkedBlockingQueue<>();
        this.executedCommands = new Stack<>();
    }

    public void update() {
        this.checkLifeTimeLimitedGameObjects();
        this.executeCommands();
        this.moveMissiles();
    }


    /* COMMANDS */

    private void executeCommands() {
        while (!this.unexecutedCommands.isEmpty()) {
            AbstractGameCommand command = this.unexecutedCommands.poll();
            command.doExecute();
            executedCommands.add(command);
        }
    }

    @Override
    public void registerCommand(AbstractGameCommand command) {
        this.unexecutedCommands.add(command);
    }

    @Override
    public void undoLastCommand() {
        if (this.executedCommands.empty())
            return;
        this.executedCommands.pop().unExecute();
        // TODO add some way to notify only the correct aspect
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.OBJECT_ANGLES);
        this.notifyObservers(Aspect.STATUS);
    }

    public void moveCannonUp() {
        this.cannon.moveUp();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        cannon.acceptVisitor(soundMaker); // soundMaker.visitCannon(cannon);
    }

    public void moveCannonDown() {
        this.cannon.moveDown();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        cannon.acceptVisitor(soundMaker); // soundMaker.visitCannon(cannon);
    }

    public void aimCannonUp() {
        this.cannon.aimUp();
        this.notifyObservers(Aspect.OBJECT_ANGLES);
    }

    public void aimCannonDown() {
        this.cannon.aimDown();
        this.notifyObservers(Aspect.OBJECT_ANGLES);
    }

    public void cannonPowerUp() {
        this.cannon.powerUp();
        this.notifyObservers(Aspect.STATUS);
    }

    public void cannonPowerDown() {
        this.cannon.powerDown();
        this.notifyObservers(Aspect.STATUS);
    }

    public void cannonShoot() {
        List<AbsMissile> newMissiles = this.cannon.shoot();
        this.missiles.addAll(newMissiles);
        this.notifyObservers(Aspect.OBJECT_POSITIONS);

        // play sound only once even if multiple missiles are shot
        newMissiles.get(0).acceptVisitor(soundMaker); // soundMaker.visitMissile(missiles.get(0));
    }

    public void toggleShootingMode() {
        this.cannon.toggleShootingMode();
        this.notifyObservers(Aspect.STATUS);
    }

    public void toggleMovingStrategy() {
        if (this.movingStrategy instanceof SimpleMovingStrategy) {
            this.movingStrategy = new RealisticMovingStrategy();
        }
        else if (this.movingStrategy instanceof RealisticMovingStrategy) {
            this.movingStrategy = new SinusoidalMovingStrategy();
        }
        else if (this.movingStrategy instanceof SinusoidalMovingStrategy) {
            this.movingStrategy = new CircularMovingStrategy();
        }
        else if (this.movingStrategy instanceof CircularMovingStrategy) {
            this.movingStrategy = new SimpleMovingStrategy();
        }
    }

    public void addMissilesForDynamicShootingMode(int toAdd) {
        cannon.addMissilesForDynamicShootingMode(toAdd);
    }

    public void removeMissilesForDynamicShootingMode(int toRemove) {
        cannon.removeMissilesForDynamicShootingMode(toRemove);
    }


    /* OBSERVER */

    @Override
    public void registerObserver(IObserver observer, Aspect aspect) {
        if (!this.observers.containsKey(aspect))
            this.observers.put(aspect, new HashSet<>());

        this.observers.get(aspect).add(observer);
    }

    @Override
    public void unregisterObserver(IObserver observer, Aspect aspect) {
        this.observers.get(aspect).remove(observer);
    }

    @Override
    public void notifyObservers(Aspect aspect) {
        this.observers.get(aspect).forEach(o -> o.update(aspect));
    }


    /* HELPERS, MODEL INFO */

    private void moveMissiles() {
        this.missiles.forEach(AbsMissile::move);
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
    }

    private void checkLifeTimeLimitedGameObjects() {
        List<AbsMissile> toDestroy = this.missiles.stream().filter(LifetimeLimitedGameObject::shouldBeDestroyed).toList();
        this.missiles.removeAll(toDestroy);
        toDestroy.forEach(m -> m = null);
    }

    public List<GameObject> getGameObjects() {
        return Stream.concat(Stream.of(this.cannon), this.missiles.stream()).toList();
    }

    public Position getCannonPosition() {
        return this.cannon.getPosition();
    }

    public List<AbsMissile> getMissiles() {
        return this.missiles;
    }

    public IMovingStrategy getMovingStrategy() {
        return this.movingStrategy;
    }


    /* MEMENTO */

    private static class Memento {
        private int cannonPositionX;
        private int cannonPositionY;
        // game model snapshot (enemies, gameInfo, strategy, state)
    }

    public Object createMemento() {
        Memento gameModelSnapshot = new Memento();
        gameModelSnapshot.cannonPositionX = this.getCannonPosition().getX();
        gameModelSnapshot.cannonPositionY = this.getCannonPosition().getY();
        return gameModelSnapshot;
    }

    public void setMemento(Object memento) {
        Memento gameModelSnapshot = (Memento) memento;
        this.cannon.getPosition().setX(gameModelSnapshot.cannonPositionX);
        this.cannon.getPosition().setY(gameModelSnapshot.cannonPositionY);
    }
}
