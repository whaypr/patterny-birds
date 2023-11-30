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
    private final GameObjectsSoundMaker soundMaker;

    public GameModel(GameObjectsSoundMaker soundMaker) {
        this.observers = new HashMap<>();
        GameObjectsFactoryA.createInstance(this);
        this.gameObjectsFactory = GameObjectsFactoryA.getInstance();
        this.cannon = this.gameObjectsFactory.createCannon();
        this.missiles = new ArrayList<>();
        this.soundMaker = soundMaker;
    }

    public void update() {
        this.moveMissiles();
    }

    private void moveMissiles() {
        this.missiles.forEach(missile -> missile.move(new Vector(MvcGameConfig.MOVE_STEP, 0)));
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
        AbsMissile missile = this.cannon.shoot();
        this.missiles.add(missile);
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        missile.acceptVisitor(soundMaker); // soundMaker.visitMissile(missile);
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
}
