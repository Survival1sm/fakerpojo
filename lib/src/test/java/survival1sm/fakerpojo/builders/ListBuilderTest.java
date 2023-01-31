package survival1sm.fakerpojo.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.leangen.geantyref.TypeFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import survival1sm.fakerpojo.FakerPojo;
import survival1sm.fakerpojo.FakerPojo.Builder;
import survival1sm.fakerpojo.annotations.FakerField;
import survival1sm.fakerpojo.domain.ClassTestDomain;
import survival1sm.fakerpojo.domain.CustomValueGeneratorTestDomain;
import survival1sm.fakerpojo.domain.FieldAnnotationTestDomain;
import survival1sm.fakerpojo.domain.ListClassTestDomain;
import survival1sm.fakerpojo.domain.ListDomainTestDomain;
import survival1sm.fakerpojo.domain.ListMapTestDomain;
import survival1sm.fakerpojo.domain.ListRecordTestDomain;
import survival1sm.fakerpojo.domain.MapStringDomainTestDomain;
import survival1sm.fakerpojo.domain.MapStringListTestDomain;
import survival1sm.fakerpojo.domain.MapStringSetTestDomain;
import survival1sm.fakerpojo.domain.RecordTestDomain;
import survival1sm.fakerpojo.domain.SetClassTestDomain;
import survival1sm.fakerpojo.domain.TestTypes;
import survival1sm.fakerpojo.enums.NanoPrefix;
import survival1sm.fakerpojo.exceptions.UniqueValueException;
import survival1sm.fakerpojo.service.NanoIdService;
import survival1sm.fakerpojo.service.TestValueGenerators;

public class ListBuilderTest {

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
  void build_class_domain_returns_expected_types() throws Exception {
    List<ClassTestDomain> test = Builder.fromPojo(ClassTestDomain.class)
        .toList()
        .build();

    assertEquals(Integer.class, test.get(0).getTestInteger().getClass());
    assertEquals(Boolean.class, test.get(0).getTestBoolean().getClass());
    assertEquals(String.class, test.get(0).getTestString().getClass());
    assertEquals(Date.class, test.get(0).getTestDate().getClass());
    assertEquals(LocalDate.class, test.get(0).getTestLocalDate().getClass());
    assertEquals(LocalDateTime.class, test.get(0).getTestLocalDateTime().getClass());
    assertEquals(Float.class, test.get(0).getTestFloat().getClass());
    assertEquals(Double.class, test.get(0).getTestDouble().getClass());
    assertEquals(Long.class, test.get(0).getTestLong().getClass());
    assertEquals(Instant.class, test.get(0).getTestInstant().getClass());
    assertEquals(NanoPrefix.class, test.get(0).getTestEnum().getClass());
    assertEquals(UUID.class, test.get(0).getTestUuid().getClass());
    assertEquals(Duration.class, test.get(0).getTestDuration().getClass());
  }

  @Test
  void build_class_domain_returns_expected_formats() throws Exception {
    List<ClassTestDomain> test = Builder.fromPojo(ClassTestDomain.class)
        .toList()
        .build();

    FakerField fieldAnnotation = TypeFactory.annotation(FakerField.class, Map.of());
    assertTrue(
        test.get(0).getTestInteger() >= fieldAnnotation.min() && test.get(0).getTestInteger() <= fieldAnnotation.max());
    assertTrue(Stream.of(true, false).anyMatch(bool -> bool.equals(test.get(0).getTestBoolean())));
    assertEquals(10, test.get(0).getTestString().length());
    assertTrue(test.get(0).getTestDate().getHours() == 0 && test.get(0).getTestDate().getMinutes() == 0
               && test.get(0).getTestDate().getSeconds() == 0);
    assertEquals(10, test.get(0).getTestLocalDate().toString().length());
    assertTrue(test.get(0).getTestLocalDateTime().getHour() > 0 || test.get(0).getTestLocalDateTime().getMinute() > 0
               || test.get(0).getTestLocalDateTime().getSecond() > 0);
    assertEquals(7, test.get(0).getTestLong().toString().length());
  }

  @Test
  void build_list_class_domain_returns_expected_lists() throws Exception {
    List<ListClassTestDomain> test = Builder
        .fromPojo(ListClassTestDomain.class)
        .toList()
        .build();
    FakerField fieldAnnotation = TypeFactory.annotation(FakerField.class, Map.of());

    assertTrue(test.get(0).getTestInteger().size() == fieldAnnotation.records()
               && test.get(0).getTestInteger().get(0) != null);
    assertTrue(test.get(0).getTestBoolean().size() == fieldAnnotation.records()
               && test.get(0).getTestBoolean().get(0) != null);
    assertTrue(
        test.get(0).getTestDouble().size() == fieldAnnotation.records() && test.get(0).getTestDouble().get(0) != null);
    assertTrue(
        test.get(0).getTestString().size() == fieldAnnotation.records() && test.get(0).getTestString().get(0) != null);
    assertTrue(
        test.get(0).getTestDate().size() == fieldAnnotation.records() && test.get(0).getTestDate().get(0) != null);
    assertTrue(test.get(0).getTestLocalDate().size() == fieldAnnotation.records()
               && test.get(0).getTestLocalDate().get(0) != null);
    assertTrue(
        test.get(0).getTestLocalDateTime().size() == fieldAnnotation.records()
        && test.get(0).getTestLocalDateTime().get(0) != null);
    assertTrue(
        test.get(0).getTestFloat().size() == fieldAnnotation.records() && test.get(0).getTestFloat().get(0) != null);
    assertTrue(
        test.get(0).getTestLong().size() == fieldAnnotation.records() && test.get(0).getTestLong().get(0) != null);
    assertTrue(test.get(0).getTestInstant().size() == fieldAnnotation.records()
               && test.get(0).getTestInstant().get(0) != null);
    assertTrue(
        test.get(0).getTestEnum().size() == fieldAnnotation.records() && test.get(0).getTestEnum().get(0) != null);
    assertTrue(
        test.get(0).getTestUuid().size() == fieldAnnotation.records() && test.get(0).getTestUuid().get(0) != null);
    assertTrue(test.get(0).getTestDuration().size() == fieldAnnotation.records()
               && test.get(0).getTestDuration().get(0) != null);
  }

  @Test
  void build_set_class_domain_returns_expected_sets() throws Exception {
    List<SetClassTestDomain> test = Builder
        .fromPojo(SetClassTestDomain.class)
        .toList()
        .build();
    FakerField fieldAnnotation = TypeFactory.annotation(FakerField.class, Map.of());

    assertTrue(
        test.get(0).getTestInteger().size() == fieldAnnotation.records()
        && test.get(0).getTestInteger().iterator().next() != null);
    assertTrue(
        test.get(0).getTestBoolean().size() == fieldAnnotation.records()
        && test.get(0).getTestBoolean().iterator().next() != null);
    assertTrue(
        test.get(0).getTestDouble().size() == fieldAnnotation.records()
        && test.get(0).getTestDouble().iterator().next() != null);
    assertTrue(
        test.get(0).getTestString().size() == fieldAnnotation.records()
        && test.get(0).getTestString().iterator().next() != null);
    assertTrue(test.get(0).getTestDate().size() == fieldAnnotation.records()
               && test.get(0).getTestDate().iterator().next() != null);
    assertTrue(test.get(0).getTestLocalDate().size() == fieldAnnotation.records()
               && test.get(0).getTestLocalDate().iterator().next() != null);
    assertTrue(
        test.get(0).getTestLocalDateTime().size() == fieldAnnotation.records()
        && test.get(0).getTestLocalDateTime().iterator().next() != null);
    assertTrue(
        test.get(0).getTestFloat().size() == fieldAnnotation.records()
        && test.get(0).getTestFloat().iterator().next() != null);
    assertTrue(test.get(0).getTestLong().size() == fieldAnnotation.records()
               && test.get(0).getTestLong().iterator().next() != null);
    assertTrue(
        test.get(0).getTestInstant().size() == fieldAnnotation.records()
        && test.get(0).getTestInstant().iterator().next() != null);
    assertTrue(test.get(0).getTestEnum().size() == fieldAnnotation.records()
               && test.get(0).getTestEnum().iterator().next() != null);
    assertTrue(test.get(0).getTestUuid().size() == fieldAnnotation.records()
               && test.get(0).getTestUuid().iterator().next() != null);
    assertTrue(
        test.get(0).getTestDuration().size() == fieldAnnotation.records()
        && test.get(0).getTestDuration().iterator().next() != null);
  }

  @Test
  void build_string_set_map_class_domain_returns_expected_maps() throws Exception {
    List<MapStringSetTestDomain> test = Builder
        .fromPojo(MapStringSetTestDomain.class)
        .toList()
        .build();
    FakerField fieldAnnotation = TypeFactory.annotation(FakerField.class, Map.of());

    assertTrue(test.get(0).getTestInteger().size() == fieldAnnotation.records()
               && test.get(0).getTestInteger().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestBoolean().size() == fieldAnnotation.records()
               && test.get(0).getTestBoolean().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestDouble().size() == fieldAnnotation.records()
               && test.get(0).getTestDouble().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestString().size() == fieldAnnotation.records()
               && test.get(0).getTestString().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestDate().size() == fieldAnnotation.records()
               && test.get(0).getTestDate().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestLocalDate().size() == fieldAnnotation.records()
               && test.get(0).getTestLocalDate().values().iterator().next().iterator().next() != null);
    assertTrue(
        test.get(0).getTestLocalDateTime().size() == fieldAnnotation.records()
        && test.get(0).getTestLocalDateTime().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestFloat().size() == fieldAnnotation.records()
               && test.get(0).getTestFloat().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestLong().size() == fieldAnnotation.records()
               && test.get(0).getTestLong().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestInstant().size() == fieldAnnotation.records()
               && test.get(0).getTestInstant().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestEnum().size() == fieldAnnotation.records()
               && test.get(0).getTestEnum().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestUuid().size() == fieldAnnotation.records()
               && test.get(0).getTestUuid().values().iterator().next().iterator().next() != null);
    assertTrue(test.get(0).getTestDuration().size() == fieldAnnotation.records()
               && test.get(0).getTestDuration().values().iterator().next().iterator().next() != null);
  }

  @Test
  void build_string_list_map_class_domain_returns_expected_maps() throws Exception {
    List<MapStringListTestDomain> test = Builder
        .fromPojo(MapStringListTestDomain.class)
        .toList()
        .build();
    FakerField fieldAnnotation = TypeFactory.annotation(FakerField.class, Map.of());

    assertTrue(test.get(0).getTestInteger().size() == fieldAnnotation.records()
               && test.get(0).getTestInteger().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestBoolean().size() == fieldAnnotation.records()
               && test.get(0).getTestBoolean().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestDouble().size() == fieldAnnotation.records()
               && test.get(0).getTestDouble().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestString().size() == fieldAnnotation.records()
               && test.get(0).getTestString().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestDate().size() == fieldAnnotation.records()
               && test.get(0).getTestDate().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestLocalDate().size() == fieldAnnotation.records()
               && test.get(0).getTestLocalDate().values().iterator().next().get(0) != null);
    assertTrue(
        test.get(0).getTestLocalDateTime().size() == fieldAnnotation.records()
        && test.get(0).getTestLocalDateTime().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestFloat().size() == fieldAnnotation.records()
               && test.get(0).getTestFloat().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestLong().size() == fieldAnnotation.records()
               && test.get(0).getTestLong().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestInstant().size() == fieldAnnotation.records()
               && test.get(0).getTestInstant().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestEnum().size() == fieldAnnotation.records()
               && test.get(0).getTestEnum().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestUuid().size() == fieldAnnotation.records()
               && test.get(0).getTestUuid().values().iterator().next().get(0) != null);
    assertTrue(test.get(0).getTestDuration().size() == fieldAnnotation.records()
               && test.get(0).getTestDuration().values().iterator().next().get(0) != null);
  }

  @Test
  void build_string_domain_map_class_domain_returns_expected_map() throws Exception {
    List<MapStringDomainTestDomain> test = Builder
        .fromPojo(MapStringDomainTestDomain.class)
        .toList()
        .build();
    FakerField fieldAnnotation = TypeFactory.annotation(FakerField.class, Map.of());

    assertTrue(test.get(0).getTestDomainMap().size() == fieldAnnotation.records()
               && test.get(0).getTestDomainMap().values().iterator().next() != null);
  }

  @Test
  void build_class_domain_with_overrides_returns_expected_domain() throws Exception {
    String overrideTestString = "this is an overridden test string";
    List<ClassTestDomain> test = Builder.fromPojo(ClassTestDomain.class)
        .toList()
        .withOverrides(Map.of("testString", overrideTestString))
        .build();

    assertEquals(test.get(0).getTestString(), overrideTestString);
  }

  @Test
  void build_class_domain_with_unique_on_key_returns_expected_unique_domain() throws Exception {
    List<ClassTestDomain> boolOne = Builder.fromPojo(ClassTestDomain.class)
        .toList()
        .withUniqueOnKey("testBoolean")
        .build();

    List<ClassTestDomain> boolTwo = Builder.fromPojo(ClassTestDomain.class)
        .toList()
        .withUniqueOnKey("testBoolean")
        .build();

    assertEquals(boolOne.get(0).getTestBoolean(), !boolTwo.get(0).getTestBoolean());
    assertThrows(UniqueValueException.class, () -> Builder.fromPojo(ClassTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build());
  }

  @Test
  void build_record_domain_returns_expected_types() throws Exception {
    List<RecordTestDomain> test = Builder.fromPojo(RecordTestDomain.class)
        .toList()
        .build();

    assertEquals(Integer.class, test.get(0).testInteger().getClass());
    assertEquals(Boolean.class, test.get(0).testBoolean().getClass());
    assertEquals(String.class, test.get(0).testString().getClass());
    assertEquals(Date.class, test.get(0).testDate().getClass());
    assertEquals(LocalDate.class, test.get(0).testLocalDate().getClass());
    assertEquals(LocalDateTime.class, test.get(0).testLocalDateTime().getClass());
    assertEquals(Float.class, test.get(0).testFloat().getClass());
    assertEquals(Double.class, test.get(0).testDouble().getClass());
    assertEquals(Long.class, test.get(0).testLong().getClass());
    assertEquals(Instant.class, test.get(0).testInstant().getClass());
    assertEquals(NanoPrefix.class, test.get(0).testEnum().getClass());
    assertEquals(UUID.class, test.get(0).testUuid().getClass());
    assertEquals(Duration.class, test.get(0).testDuration().getClass());
  }

  @Test
  void build_record_domain_with_overrides_returns_expected_domain() throws Exception {
    String overrideTestString = "this is an overridden test string";
    List<RecordTestDomain> test = Builder.fromPojo(RecordTestDomain.class)
        .toList()
        .withOverrides(Map.of("testString", overrideTestString))
        .build();

    assertEquals(test.get(0).testString(), overrideTestString);
  }

  @Test
  void build_record_domain_with_unique_on_key_returns_expected_unique_domain() throws Exception {
    RecordTestDomain boolOne = Builder.fromPojo(RecordTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build();

    RecordTestDomain boolTwo = Builder.fromPojo(RecordTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build();

    assertEquals(boolOne.testBoolean(), !boolTwo.testBoolean());
    assertThrows(UniqueValueException.class, () -> Builder.fromPojo(RecordTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build());
  }

  @Test
  void build_list_domain_domain_returns_expected_list() throws Exception {
    List<ListDomainTestDomain> test = Builder.fromPojo(ListDomainTestDomain.class).toList().build();

    assertTrue(test.get(0).getClassTestDomainList().size() == 1 && test.get(0).getClassTestDomainList().get(0) != null);
  }

  @Test
  void build_list_record_domain_returns_expected_list() throws Exception {
    List<ListRecordTestDomain> test = Builder.fromPojo(ListRecordTestDomain.class).toList().build();

    assertTrue(
        test.get(0).getRecordTestDomainList().size() == 1 && test.get(0).getRecordTestDomainList().get(0) != null);
  }

  @Test
  void build_list_map_domain_returns_expected_list() throws Exception {
    List<ListMapTestDomain> test = Builder.fromPojo(ListMapTestDomain.class).toList().build();

    assertTrue(test.get(0).getMapStringDomainList().size() == 1 && test.get(0).getMapStringDomainList().get(0) != null);
  }

  @Test
  void build_custom_field_annotation_class_domain_returns_expected_domain() throws Exception {
    List<FieldAnnotationTestDomain> test = Builder.fromPojo(FieldAnnotationTestDomain.class).toList().build();

    assertEquals(10, test.get(0).getTestParagraph().split("\\n").length);
    assertEquals(2, test.get(0).getTestFullName().split(" ").length);
    assertTrue(test.get(0).getTestLastName().length() > 1);
    assertTrue(test.get(0).getTestFirstName().length() > 1);
    assertTrue(test.get(0).getTestUrl().contains("www."));
    assertTrue(
        test.get(0).getTestPhoneNumber()
            .matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"));
    assertTrue(test.get(0).getTestEmail().contains("@"));
  }

  @Test
  void build_custom_value_generator_returns_expected_values() throws Exception {
    List<CustomValueGeneratorTestDomain> test = Builder.fromPojo(CustomValueGeneratorTestDomain.class).toList().build();

    assertTrue(test.get(0).getTestEmployeeNumber() >= 20 && test.get(0).getTestEmployeeNumber() <= 1200000);
    assertTrue(Integer.parseInt(test.get(0).getTestEmployeeNumberString()) >= 20
               && test.get(0).getTestEmployeeNumber() <= 1200000);
    assertEquals(NanoPrefix.type, NanoIdService.getPrefixFromValue(test.get(0).getTestNanoId().substring(0, 3)));
    assertEquals(NanoPrefix.project,
        NanoIdService.getPrefixFromValue(test.get(0).getTestNanoIdList().get(0).substring(0, 3)));
  }
}
