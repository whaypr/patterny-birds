package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObservable;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.strategy.RealisticMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.strategy.SimpleMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsSoundMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class GameModel implements IObservable {

    private final AbsCannon cannon;
    private final Map<Aspect, Set<IObserver>> observers;
    private IGameObjectsFactory gameObjectsFactory;
    private final List<AbsMissile> missiles;
    private IMovingStrategy movingStrategy;
    private final GameObjectsSoundMaker soundMaker;

    public GameModel(GameObjectsSoundMaker soundMaker) {
        this.observers = new HashMap<>();
        GameObjectsFactoryA.createInstance(this);
        this.gameObjectsFactory = GameObjectsFactoryA.getInstance();
        this.cannon = this.gameObjectsFactory.createCannon();
        this.missiles = new ArrayList<>();
        this.movingStrategy = new SimpleMovingStrategy();
        this.soundMaker = soundMaker;
    }

    public void update() {
        this.moveMissiles();
    }

    private void moveMissiles() {
        this.missiles.forEach(AbsMissile::move);
        this.destroyMissiles();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
    }

    private void destroyMissiles() {
        this.missiles.removeAll(
            this.missiles.stream().filter(missile -> missile.getPosition().getX() > MvcGameConfig.MAX_X).toList()
        );
    }

    public Position getCannonPosition() {
        return this.cannon.getPosition();
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

    public void cannonShoot() {
        List<AbsMissile> missiles = this.cannon.shoot();
        this.missiles.addAll(missiles);
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        missiles.forEach(m -> m.acceptVisitor(soundMaker));
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

    public List<AbsMissile> getMissiles() {
        return this.missiles;
    }

    public List<GameObject> getGameObjects() {
        return Stream.concat(Stream.of(this.cannon), this.missiles.stream()).toList();
    }

    public IMovingStrategy getMovingStrategy() {
        return this.movingStrategy;
    }

    public void toggleMovingStrategy() {
        if (this.movingStrategy instanceof SimpleMovingStrategy) {
            this.movingStrategy = new RealisticMovingStrategy();
        }
        else if (this.movingStrategy instanceof RealisticMovingStrategy) {
            this.movingStrategy = new SimpleMovingStrategy();
        }
        else {

        }
    }

    public void toggleShootingMode() {
        this.cannon.toggleShootingMode();
        this.notifyObservers(Aspect.STATUS);
    }

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
