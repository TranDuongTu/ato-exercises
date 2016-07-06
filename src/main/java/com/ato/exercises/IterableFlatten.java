package com.ato.exercises;

import com.ato.exercises.utils.Colls;
import com.ato.exercises.utils.Colls.EmptyIterator;
import com.ato.exercises.utils.Colls.UnmodifiableIterator;
import com.ato.exercises.utils.Function;

import java.util.*;

/**
 * Created by tutran on 7/5/16.
 */
public final class IterableFlatten {

  /**
   * flattenElements([[1,2,3],[1],[1,2,3]]) = [1,2,3,1,1,2,3]
   * */
  public static <T> Iterable<? extends T> flattenElements(final Iterable<? extends Iterable<? extends T>> input) {
    final Iterator<T> iterator = input != null ? concatIterators(iterators(input)) : new EmptyIterator<T>();
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterator;
      }
    };
  }

  /**
   * flattenNestedElements([[1,2,3],1,[[1,2],[1],[1,[1,[1]]]]) = [1,2,3,1,1,2,1,1,1,1];
   * */
  public static <T> Iterable<? extends T> flattenNestedElements(final NestedElement<T> input) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return Colls.reverseIterator(new NestedIterator<>(input));
      }
    };
  }

  /**
   * NestedElement interface for flatten nested elements
   * */
  public static class NestedElement<T> {
    private boolean isElement;
    private T element;
    private Iterable<NestedElement<T>> children;

    private NestedElement(final T element) {
      this.isElement = true;
      this.element = element;
      this.children = new ArrayList<>();
    }

    private NestedElement(final Iterable<NestedElement<T>> elements) {
      this.children = elements;
    }

    public boolean isElement() {
      return isElement;
    }

    public T getElement() {
      return element;
    }

    public Iterable<NestedElement<T>> getChildren() {
      return children;
    }

    public static <T> NestedElement<T> element(final T element) {
      return new NestedElement<>(element);
    }

    public static <T> NestedElement<T> elements(final Collection<T> elements) {
      return new NestedElement<>(Colls.transform(
        elements,
        new Function<T, NestedElement<T>>() {
          @Override
          public NestedElement<T> eval(T value) {
            return element(value);
          }
        }));
    }

    public static <T> NestedElement<T> nestedElements(final Collection<NestedElement<T>> nestedElements) {
      return new NestedElement<>(nestedElements);
    }
  }

  private static <T> Iterator<T> concatIterators(final Iterator<? extends Iterator<? extends T>> input) {
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

  private static <T> UnmodifiableIterator<Iterator<? extends T>> iterators(
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

  private static class NestedIterator<T> extends UnmodifiableIterator<T> {
    private Stack<NestedElement<T>> stack = new Stack<>();

    public NestedIterator(final NestedElement<T> nestedElement) {
      if (nestedElement != null) {
        stack.push(nestedElement);
      }
    }

    @Override
    public boolean hasNext() {
      while(!stack.isEmpty()){
        final NestedElement<T> top = stack.peek();
        if (top.isElement()) {
          return true;
        } else {
          stack.pop();
          for (final NestedElement<T> element : top.getChildren()) {
            stack.push(element);
          }
        }
      }

      return false;
    }

    @Override
    public T next() {
      return stack.pop().getElement();
    }
  }
}
