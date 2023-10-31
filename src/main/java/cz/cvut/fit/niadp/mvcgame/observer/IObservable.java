package cz.cvut.fit.niadp.mvcgame.observer;

public interface IObservable {
    void registerObserver(IObserver observer);
    void unregisterObserver(IObserver observer);
    void notifyObservers();
}
