package com.ato.exercises.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tutran on 7/6/16.
 */
public class CollsTest {

  @Test
  public void testEmpty() {
    assertTrue(Colls.isEmpty(Collections.emptyList()));
    assertFalse(Colls.isEmpty(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void testTransform() {
    final List<Integer> lst1 = new ArrayList<>();
    final List<Integer> lst2 = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      lst1.add(i);
      lst2.add(i + 1);
    }

    final List<Integer> result1 = Colls.toList(Colls.transform(lst1, new Function<Integer, Integer>() {
      @Override
      public Integer eval(Integer value) {
        return value + 1;
      }
    }));

    final List<Integer> result2 = Colls.toList(Colls.transform(lst2, new Function<Integer, Integer>() {
      @Override
      public Integer eval(Integer value) {
        return value - 1;
      }
    }));

    assertEquals(result1, lst2);
    assertEquals(result2, lst1);
  }
}
