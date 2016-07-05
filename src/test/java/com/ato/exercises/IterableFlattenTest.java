package com.ato.exercises;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by tutran on 7/5/16.
 */
public class IterableFlattenTest {

  @Test
  public void testFlattenSimpleLists() {
    final List<List<Integer>> input = new ArrayList<>();
    input.add(Arrays.asList(1, 2, 3));
    input.add(Collections.<Integer> emptyList());
    input.add(Arrays.asList(4, 5, 6));
    final Iterable<? extends Integer> result = IterableFlatten.flatten(input);

    final Iterator<? extends Integer> iter = result.iterator();
    final List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6);
    final List<Integer> actual = new ArrayList<>();
    while (iter.hasNext()) {
      actual.add(iter.next());
    }
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFlattenNestedLists() {

  }
}
