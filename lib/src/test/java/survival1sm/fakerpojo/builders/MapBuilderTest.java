package survival1sm.fakerpojo.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import survival1sm.fakerpojo.FakerPojo;
import survival1sm.fakerpojo.FakerPojo.Builder;
import survival1sm.fakerpojo.domain.ClassTestDomain;
import survival1sm.fakerpojo.domain.TestTypes;
import survival1sm.fakerpojo.service.TestValueGenerators;

public class MapBuilderTest {

  @BeforeAll
  static void addTestValueGenerator() {
    FakerPojo.addFieldValuesGenerator(TestTypes.EMPLOYEE_NUMBER, TestValueGenerators.employeeNumberValueGenerator);
    FakerPojo.addFieldValuesGenerator(TestTypes.EMPLOYEE_NUMBER_STRING,
        TestValueGenerators.employeeNumberStringValueGenerator);
    FakerPojo.addFieldValuesGenerator(TestTypes.TYPE_NANOID, TestValueGenerators.typeNanoIdValueGenerator);
    FakerPojo.addFieldValuesGenerator(TestTypes.LIST_PROJECT_NANOID,
        TestValueGenerators.listProjectNanoIdValueGenerator);
  }

  @BeforeEach
  void setUp() {
    Builder.resetUniqueOnKeyList();
  }

  @Test
  void build_creates_map_with_key_and_types() throws Exception {
    Map<String, ClassTestDomain> test = Builder.fromPojo(ClassTestDomain.class)
        .toMapOnKey("testString", String.class)
        .build();

    assertEquals(1, test.size());
    assertEquals(String.class, test.keySet().iterator().next().getClass());
    assertEquals(ClassTestDomain.class, test.values().iterator().next().getClass());
  }
}
