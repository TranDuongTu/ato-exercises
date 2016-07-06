package com.ato.exercises.flatten_iterables;

import com.ato.exercises.utils.Colls;
import com.ato.exercises.utils.Function;

import java.util.ArrayList;
import java.util.Collection;

class NestedElement<T> {
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