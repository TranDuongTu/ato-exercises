package com.ato.exercises;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by tutran on 7/5/16.
 */
public final class IterableFlatten {

  public static <T> Iterable<? extends T> flatten(final Iterable<? extends Iterable<? extends  T>> input) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return flattenIterators(iterators(input));
      }
    };
  }

  public static <T> Iterator<T> flattenIterators(final Iterator<? extends Iterator<? extends T>> input) {
    return new Iterator<T>() {
      private Iterator<? extends T> current = new EmptyIterator<>();
      private Iterator<? extends T> removeCurrent;

      @Override
      public boolean hasNext() {
        boolean result;
        while (!(result = current.hasNext()) && input.hasNext()) {
          current = input.next();
        }
        return result;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        removeCurrent = current;
        return current.next();
      }

      @Override
      public void remove() {
        assert removeCurrent != null : "Never call next() before remove()";
        removeCurrent.remove();
      }
    };
  }

  public static <T> UnmodifiableIterator<Iterator<? extends T>> iterators(
      final Iterable<? extends Iterable<? extends T>> iterables) {
    final Iterator<? extends Iterable<? extends T>> iterator = iterables.iterator();
    return new UnmodifiableIterator<Iterator<? extends T>>() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public Iterator<? extends T> next() {
        return iterator.next().iterator();
      }
    };
  }

  public static class EmptyIterator<T> extends UnmodifiableIterator<T> {
    @Override
    public boolean hasNext() {
      return false;
    }

    @Override
    public T next() {
      throw new NoSuchElementException();
    }
  }

  public static abstract class UnmodifiableIterator<T> implements Iterator<T> {
    @Override
    public void remove() {
      throw new UnsupportedOperationException("Iterator not support remove method");
    }
  }
}
