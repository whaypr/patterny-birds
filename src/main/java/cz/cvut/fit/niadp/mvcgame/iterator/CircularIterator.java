package cz.cvut.fit.niadp.mvcgame.iterator;

import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;

import java.util.Collection;
import java.util.Iterator;

public class CircularIterator<T> implements IIterator<T> {

    protected Collection<T> reference;
    protected Iterator<T> iterator;

    public CircularIterator(Collection<T> collection) {
        this.reference = collection;
        this.iterator = this.reference.iterator();
    }

    @Override
    public T next() {
        if (!this.iterator.hasNext())
            this.iterator = this.reference.iterator();
        if (!this.iterator.hasNext())
            return null;
        return this.iterator.next();
    }

    @Override
    public boolean hasNext() {
        return !this.reference.isEmpty();
    }
}
