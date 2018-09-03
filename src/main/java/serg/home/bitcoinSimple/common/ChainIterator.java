package serg.home.bitcoinSimple.common;

import java.util.Iterator;

public class ChainIterator<E> implements Iterator<E> {
    private final Iterator<E>[] iterators;
    private int index = 0;

    public ChainIterator(Iterator<E>... iterators) {
        this.iterators = iterators;
    }

    @Override
    public boolean hasNext() {
        while (true) {
            if (iterators[index].hasNext()) {
                return true;
            }
            if (index == iterators.length - 1) {
                return false;
            }
            ++index;
        }
    }

    @Override
    public E next() {
        return iterators[index].next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
