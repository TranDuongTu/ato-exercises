package com.ato.exercises.utils;

/**
 * Created by tutran on 7/6/16.
 */
public interface Function<U, V> {
  V eval(final U value);
}
