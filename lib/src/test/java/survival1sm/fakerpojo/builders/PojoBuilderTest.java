package survival1sm.fakerpojo.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import survival1sm.fakerpojo.FakerPojo;
import survival1sm.fakerpojo.FakerPojo.Builder;
import survival1sm.fakerpojo.domain.ClassTestDomain;
import survival1sm.fakerpojo.domain.TestTypes;
import survival1sm.fakerpojo.enums.NanoPrefix;
import survival1sm.fakerpojo.service.TestValueGenerators;

public class PojoBuilderTest {

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
  void build_usingValueMapList_creates_correct_data() throws Exception {
    Integer overrideIntegerValueOne = 1;
    String overrideStringValueOne = "override String";
    NanoPrefix overrideEnumValueOne = NanoPrefix.status;

    Integer overrideIntegerValueTwo = 2000000;
    String overrideStringValueTwo = "a different string";
    NanoPrefix overrideEnumValueTwo = NanoPrefix.product;

    List<Map<String, Object>> valueMapList = new ArrayList<>();

    valueMapList.add(
        Map.of("testInteger", overrideIntegerValueOne, "testString", overrideStringValueOne, "testEnum",
            overrideEnumValueOne));

    valueMapList.add(
        Map.of("testInteger", overrideIntegerValueTwo, "testString", overrideStringValueTwo, "testEnum",
            overrideEnumValueTwo));

    List<ClassTestDomain> test = Builder.fromPojo(ClassTestDomain.class)
        .usingValueMapList(valueMapList)
        .build();

    assertEquals(2, test.size());
    assertEquals(overrideIntegerValueOne, test.get(0).getTestInteger());
    assertEquals(overrideStringValueOne, test.get(0).getTestString());
    assertEquals(overrideEnumValueOne, test.get(0).getTestEnum());
    assertTrue(test.get(0).getTestInstant() != null && test.get(0).getTestInstant() instanceof Instant);
    assertTrue(test.get(0).getTestFloat() != null && test.get(0).getTestFloat() instanceof Float);
    assertTrue(test.get(0).getTestUuid() != null && test.get(0).getTestUuid() instanceof UUID);
    assertTrue(test.get(0).getTestDuration() != null && test.get(0).getTestDuration() instanceof Duration);

    assertEquals(overrideIntegerValueTwo, test.get(1).getTestInteger());
    assertEquals(overrideStringValueTwo, test.get(1).getTestString());
    assertEquals(overrideEnumValueTwo, test.get(1).getTestEnum());
    assertTrue(test.get(1).getTestInstant() != null && test.get(0).getTestInstant() instanceof Instant);
    assertTrue(test.get(1).getTestFloat() != null && test.get(0).getTestFloat() instanceof Float);
    assertTrue(test.get(1).getTestUuid() != null && test.get(0).getTestUuid() instanceof UUID);
    assertTrue(test.get(1).getTestDuration() != null && test.get(0).getTestDuration() instanceof Duration);
  }

  @Test
  void build_usingValueMapList_adds_additional_records_after_mapList() throws Exception {
    Integer overrideIntegerValueOne = 1;
    String overrideStringValueOne = "override String";
    NanoPrefix overrideEnumValueOne = NanoPrefix.status;

    Integer overrideIntegerValueTwo = 2000000;
    String overrideStringValueTwo = "a different string";
    NanoPrefix overrideEnumValueTwo = NanoPrefix.product;

    List<Map<String, Object>> valueMapList = new ArrayList<>();

    valueMapList.add(
        Map.of("testInteger", overrideIntegerValueOne, "testString", overrideStringValueOne, "testEnum",
            overrideEnumValueOne));

    valueMapList.add(
        Map.of("testInteger", overrideIntegerValueTwo, "testString", overrideStringValueTwo, "testEnum",
            overrideEnumValueTwo));

    List<ClassTestDomain> test = Builder.fromPojo(ClassTestDomain.class)
        .usingValueMapList(valueMapList)
        .withRecords(5)
        .build();

    assertEquals(5, test.size());
    assertEquals(overrideIntegerValueOne, test.get(0).getTestInteger());
    assertEquals(overrideStringValueOne, test.get(0).getTestString());
    assertEquals(overrideEnumValueOne, test.get(0).getTestEnum());
    assertTrue(test.get(0).getTestInstant() != null && test.get(0).getTestInstant() instanceof Instant);
    assertTrue(test.get(0).getTestFloat() != null && test.get(0).getTestFloat() instanceof Float);
    assertTrue(test.get(0).getTestUuid() != null && test.get(0).getTestUuid() instanceof UUID);
    assertTrue(test.get(0).getTestDuration() != null && test.get(0).getTestDuration() instanceof Duration);

    assertEquals(overrideIntegerValueTwo, test.get(1).getTestInteger());
    assertEquals(overrideStringValueTwo, test.get(1).getTestString());
    assertEquals(overrideEnumValueTwo, test.get(1).getTestEnum());
    assertTrue(test.get(1).getTestInstant() != null && test.get(0).getTestInstant() instanceof Instant);
    assertTrue(test.get(1).getTestFloat() != null && test.get(0).getTestFloat() instanceof Float);
    assertTrue(test.get(1).getTestUuid() != null && test.get(0).getTestUuid() instanceof UUID);
    assertTrue(test.get(1).getTestDuration() != null && test.get(0).getTestDuration() instanceof Duration);

    assertTrue(test.get(2).getTestInstant() != null && test.get(0).getTestInstant() instanceof Instant);
    assertTrue(test.get(2).getTestFloat() != null && test.get(0).getTestFloat() instanceof Float);
    assertTrue(test.get(2).getTestUuid() != null && test.get(0).getTestUuid() instanceof UUID);
    assertTrue(test.get(2).getTestDuration() != null && test.get(0).getTestDuration() instanceof Duration);
  }
}
