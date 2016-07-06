package com.ato.exercises.utils;

import java.util.*;

/**
 * Created by tutran on 7/6/16.
 */
public class Colls {

  /**
   * Transform Iterable<U> to Iterable<V> using Function<U, V>
   */
  public static <U, V> Iterable<V> transform(final Iterable<? extends U> elements,
                                                     final Function<U, V> function) {
    final Iterator<? extends U> inputIterator = isEmpty(elements) ? new EmptyIterator<U>() : elements.iterator();
    final Iterator<V> iterator = new UnmodifiableIterator<V>() {
      @Override
      public boolean hasNext() {
        return inputIterator.hasNext();
      }

      @Override
      public V next() {
        if (!inputIterator.hasNext()) {
          throw new NoSuchElementException();
        }
        return function.eval(inputIterator.next());
      }
    };

    return new Iterable<V>() {
      @Override
      public Iterator<V> iterator() {
        return iterator;
      }
    };
  }

  /**
   * Reverse an Iterator
   */
  public static <T> Iterator<T> reverseIterator(final Iterator<? extends T> iterator) {
    final List<T> list = new LinkedList<>();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    final ListIterator<T> listIterator = list.listIterator(list.size());
    return new UnmodifiableIterator<T>() {
      @Override
      public boolean hasNext() {
        return listIterator.hasPrevious();
      }

      @Override
      public T next() {
        return listIterator.previous();
      }
    };
  }

  /**
   * Check if given Iterable is empty
   */
  public static <T> boolean isEmpty(final Iterable<? extends T> input) {
    if (input == null) {
      return false;
    }
    return !input.iterator().hasNext();
  }

  public static <T> List<T> toList(final Iterable<? extends T> elements) {
    final List<T> result = new ArrayList<>();
    for (final T element : elements) {
      result.add(element);
    }
    return result;
  }

  /**
   * Convenient Iterator that not support remove method
   * */
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

  /**
   * Convenient Empty iterator
   * */
  public static abstract class UnmodifiableIterator<T> implements Iterator<T> {
    @Override
    public void remove() {
      throw new UnsupportedOperationException("Iterator not support remove method");
    }
  }
}
