package io.github.survival1sm.fakerpojo.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.survival1sm.fakerpojo.FakerPojo;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassBuilderTest {

  @BeforeEach
  void setUp() {
    FakerPojo.Builder.resetUniqueOnKeyList();
  }

  @Test
  void build_integer_returns_integer() throws Exception {
    Integer test = FakerPojo.Builder.fromClass(Integer.class).getFirst().build();

    assertNotNull(test);
  }

  @Test
  void build_integer_list_returns_integer_list() throws Exception {
    List<Integer> test = FakerPojo.Builder.fromClass(Integer.class).toList().build();

    assertEquals(1, test.size());
    assertEquals(Integer.class, test.get(0).getClass());
  }

  @Test
  void build_integer_set_returns_integer_list() throws Exception {
    Set<Integer> test = FakerPojo.Builder.fromClass(Integer.class).toSet().build();

    assertEquals(1, test.size());
    assertEquals(Integer.class, test.iterator().next().getClass());
  }

  @Test
  void build_first_integer_with_override_throws_UnsupportedOperationException() {
    DataBuilder<Integer> test =
        FakerPojo.Builder.fromClass(Integer.class).getFirst().withOverrides(Map.of());
    assertThrows(UnsupportedOperationException.class, test::build);
  }

  @Test
  void build_first_integer_with_unique_throws_UnsupportedOperationException() {
    DataBuilder<Integer> test =
        FakerPojo.Builder.fromClass(Integer.class).getFirst().withUniqueOnKey("");
    assertThrows(UnsupportedOperationException.class, test::build);
  }
}
