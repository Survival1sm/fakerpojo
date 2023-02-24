package io.github.survival1sm.fakerpojo.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.survival1sm.fakerpojo.FakerPojo;
import io.github.survival1sm.fakerpojo.domain.ClassTestDomain;
import io.github.survival1sm.fakerpojo.domain.TestTypes;
import io.github.survival1sm.fakerpojo.service.TestValueGenerators;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapBuilderTest {

  @BeforeAll
  static void addTestValueGenerator() {
    FakerPojo.addFieldValuesGenerator(
        TestTypes.EMPLOYEE_NUMBER, TestValueGenerators.employeeNumberValueGenerator);
    FakerPojo.addFieldValuesGenerator(
        TestTypes.EMPLOYEE_NUMBER_STRING, TestValueGenerators.employeeNumberStringValueGenerator);
    FakerPojo.addFieldValuesGenerator(
        TestTypes.TYPE_NANOID, TestValueGenerators.typeNanoIdValueGenerator);
    FakerPojo.addFieldValuesGenerator(
        TestTypes.LIST_PROJECT_NANOID, TestValueGenerators.listProjectNanoIdValueGenerator);
  }

  @BeforeEach
  void setUp() {
    FakerPojo.Builder.resetUniqueOnKeyList();
  }

  @Test
  void build_creates_map_with_key_and_types() throws Exception {
    Map<String, ClassTestDomain> test =
        FakerPojo.Builder.fromPojo(ClassTestDomain.class)
            .toMapOnKey("testString", String.class)
            .build();

    assertEquals(1, test.size());
    assertEquals(String.class, test.keySet().iterator().next().getClass());
    assertEquals(ClassTestDomain.class, test.values().iterator().next().getClass());
  }
}
