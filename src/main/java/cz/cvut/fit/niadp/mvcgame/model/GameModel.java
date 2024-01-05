package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.*;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;

import java.util.*;

import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.*;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsSoundMaker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class GameModel implements IGameModel {

    private final GameObjectsSoundMaker soundMaker;

    private final Map<Aspect, Set<IObserver>> observers;
    private final IGameObjectsFactory gameObjectsFactory;

    private final AbsCannon cannon;
    private final List<AbsMissile> missiles;
    private IMovingStrategy movingStrategy;

    private int score;
    private int numberOfMissilesShot;

    private AbsGameInfo gameInfo;

    private final Queue<AbstractGameCommand> unexecutedCommands;
    private final Stack<AbstractGameCommand> executedCommands;

    public GameModel() {
        this.observers = new HashMap<>();
        this.missiles = new ArrayList<>();
        this.unexecutedCommands = new LinkedBlockingQueue<>();
        this.executedCommands = new Stack<>();

        this.soundMaker = new GameObjectsSoundMaker();
        this.movingStrategy = new SimpleMovingStrategy();

        GameObjectsFactoryA.createInstance(this);
        this.gameObjectsFactory = GameObjectsFactoryA.getInstance();
        this.cannon = this.gameObjectsFactory.createCannon();
        this.gameInfo = this.gameObjectsFactory.createGameInfo();

        this.score = 0;
        this.numberOfMissilesShot = 0;
    }

    @Override
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

    @Override
    public void moveCannonUp() {
        this.cannon.moveUp();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        cannon.acceptVisitor(soundMaker); // soundMaker.visitCannon(cannon);
    }

    @Override
    public void moveCannonDown() {
        this.cannon.moveDown();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        cannon.acceptVisitor(soundMaker); // soundMaker.visitCannon(cannon);
    }

    @Override
    public void aimCannonUp() {
        this.cannon.aimUp();
        this.notifyObservers(Aspect.OBJECT_ANGLES);
    }

    @Override
    public void aimCannonDown() {
        this.cannon.aimDown();
        this.notifyObservers(Aspect.OBJECT_ANGLES);
    }

    @Override
    public void cannonPowerUp() {
        this.cannon.powerUp();
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void cannonPowerDown() {
        this.cannon.powerDown();
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void cannonShoot() {
        List<AbsMissile> newMissiles = this.cannon.shoot();

        this.missiles.addAll(newMissiles);
        this.numberOfMissilesShot += newMissiles.size();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);

        // play sound only once even if multiple missiles are shot
        newMissiles.get(0).acceptVisitor(soundMaker); // soundMaker.visitMissile(missiles.get(0));
    }

    @Override
    public void toggleShootingMode() {
        this.cannon.toggleShootingMode();
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
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

    @Override
    public void addMissilesForDynamicShootingMode(int toAdd) {
        cannon.addMissilesForDynamicShootingMode(toAdd);
    }

    @Override
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

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getNumberOfMissilesShot() {
        return numberOfMissilesShot;
    }

    @Override
    public int getNumberOfEnemiesLeft() {
        return 0; // TODO
    }

    @Override
    public List<GameObject> getGameObjects() {
        return Stream.concat(
                Stream.of(this.cannon, this.gameInfo),
                this.missiles.stream()
        ).toList();
    }

    @Override
    public Position getCannonPosition() {
        return this.cannon.getPosition();
    }

    @Override
    public double getCannonAngle() {
        return this.cannon.getAngle();
    }

    @Override
    public int getCannonPower() {
        return this.cannon.getPower();
    }

    @Override
    public IShootingMode getCannonShootingMode() {
        return this.cannon.getShootingMode();
    }

    @Override
    public int getCannonDynamicShootingModeNumberOfMissiles() {
        return this.cannon.getDynamicShootingModeNumberOfMissiles();
    }

    @Override
    public List<AbsMissile> getMissiles() {
        return this.missiles;
    }

    @Override
    public IMovingStrategy getMissileMovingStrategy() {
        return this.movingStrategy;
    }


    /* MEMENTO */

    private static class Memento {
        private int cannonPositionX;
        private int cannonPositionY;
        // game model snapshot (enemies, gameInfo, strategy, state)
    }

    @Override
    public Object createMemento() {
        Memento gameModelSnapshot = new Memento();
        gameModelSnapshot.cannonPositionX = this.getCannonPosition().getX();
        gameModelSnapshot.cannonPositionY = this.getCannonPosition().getY();
        return gameModelSnapshot;
    }

    @Override
    public void setMemento(Object memento) {
        Memento gameModelSnapshot = (Memento) memento;
        this.cannon.getPosition().setX(gameModelSnapshot.cannonPositionX);
        this.cannon.getPosition().setY(gameModelSnapshot.cannonPositionY);
    }
}
