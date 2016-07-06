package com.ato.exercises.flatten_iterables;

import com.ato.exercises.utils.Colls;
import org.junit.Test;

import java.util.*;

import static com.ato.exercises.flatten_iterables.IterableFlatten.flattenElements;
import static com.ato.exercises.flatten_iterables.IterableFlatten.flattenNestedElements;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tutran on 7/5/16.
 */
public class IterableFlattenTest {

  @Test
  public void testFlattenElements1() {
    final List<List<Integer>> input = new ArrayList<>();
    input.add(Arrays.asList(1, 2, 3));
    input.add(Collections.<Integer> emptyList());
    input.add(Arrays.asList(4, 5, 6));
    final Iterable<? extends Integer> result = flattenElements(input);

    final Iterator<? extends Integer> iter = result.iterator();
    final List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6);
    final List<Integer> actual = new ArrayList<>();
    while (iter.hasNext()) {
      actual.add(iter.next());
    }
    assertEquals(expected, actual);
  }

  @Test
  public void testFlattenElements2() {
    assertTrue(Colls.isEmpty(flattenElements(null)));
    assertTrue(Colls.isEmpty(flattenElements(Collections.<Iterable<Integer>>emptyList())));
    assertTrue(Colls.isEmpty(flattenElements(
      Arrays.asList(
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList()
      )
    )));
  }

  @Test
  public void testFlattenNestedElements1() {
    final NestedElement<Integer> input = NestedElement.nestedElements(
      Arrays.asList(
        NestedElement.element(1),
        NestedElement.elements(Arrays.asList(1, 2, 3)),
        NestedElement.nestedElements(
          Arrays.asList(
            NestedElement.element(3),
            NestedElement.elements(Arrays.asList(4, 5, 6))
          )
        )
      )
    );

    final Iterable<? extends Integer> result = flattenNestedElements(input);

    final List<Integer> expected = Arrays.asList(1, 1, 2, 3, 3, 4, 5, 6);
    final List<Integer> actual = Colls.toList(result);
    assertEquals(expected, actual);
  }

  @Test
  public void testFlattenNestedElements2() {
    assertTrue(Colls.isEmpty(flattenNestedElements(null)));
    assertTrue(Colls.isEmpty(flattenNestedElements(NestedElement.elements(Collections.emptyList()))));
  }
}
