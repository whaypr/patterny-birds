package cz.cvut.fit.niadp.mvcgame.observer;

public interface IObservable {
    void registerObserver(IObserver observer, Aspect aspect);
    void unregisterObserver(IObserver observer, Aspect aspect);
    void notifyObservers(Aspect aspect);
}
