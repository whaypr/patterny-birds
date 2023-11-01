package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.Cannon;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObservable;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameModel implements IObservable {

    private final Cannon cannon;
    private final Map<Aspect, Set<IObserver>> observers;

    public GameModel() {
        this.cannon = new Cannon(new Position(MvcGameConfig.CANNON_POS_X, MvcGameConfig.CANNON_POS_Y));
        this.observers = new HashMap<>();
    }

    public void update() {
        // this.moveMissiles();
    }

    public Position getCannonPosition() {
        return this.cannon.getPosition();
    }

    public void moveCannonUp() {
        this.cannon.moveUp();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
    }

    public void moveCannonDown() {
        this.cannon.moveDown();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
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
}
