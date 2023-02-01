package io.github.survival1sm.fakerpojo.builders;

import io.github.survival1sm.fakerpojo.FakerPojo;
import io.github.survival1sm.fakerpojo.domain.*;
import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import io.github.survival1sm.fakerpojo.exceptions.UniqueValueException;
import io.github.survival1sm.fakerpojo.service.NanoIdService;
import io.github.survival1sm.fakerpojo.service.TestValueGenerators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SetBuilderTest {

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
    FakerPojo.Builder.resetUniqueOnKeyList();
  }

  @Test
  void build_class_domain_returns_expected_types() throws Exception {
    Set<ClassTestDomain> test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
        .toSet()
        .build();

    assertEquals(Integer.class, test.iterator().next().getTestInteger().getClass());
    assertEquals(Boolean.class, test.iterator().next().getTestBoolean().getClass());
    assertEquals(String.class, test.iterator().next().getTestString().getClass());
    assertEquals(Date.class, test.iterator().next().getTestDate().getClass());
    assertEquals(LocalDate.class, test.iterator().next().getTestLocalDate().getClass());
    assertEquals(LocalDateTime.class, test.iterator().next().getTestLocalDateTime().getClass());
    assertEquals(Float.class, test.iterator().next().getTestFloat().getClass());
    assertEquals(Double.class, test.iterator().next().getTestDouble().getClass());
    assertEquals(Long.class, test.iterator().next().getTestLong().getClass());
    assertEquals(Instant.class, test.iterator().next().getTestInstant().getClass());
    assertEquals(NanoPrefix.class, test.iterator().next().getTestEnum().getClass());
    assertEquals(UUID.class, test.iterator().next().getTestUuid().getClass());
    assertEquals(Duration.class, test.iterator().next().getTestDuration().getClass());
  }

  @Test
  void build_class_domain_returns_expected_formats() throws Exception {
    Set<ClassTestDomain> test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
        .toSet()
        .build();

    DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();
    assertTrue(
        test.iterator().next().getTestInteger() >= fieldAnnotation.getMin()
            && test.iterator().next().getTestInteger() <= fieldAnnotation.getMax());
    assertTrue(Stream.of(true, false).anyMatch(bool -> bool.equals(test.iterator().next().getTestBoolean())));
    assertEquals(10, test.iterator().next().getTestString().length());
    assertTrue(
        test.iterator().next().getTestDate().getHours() == 0 && test.iterator().next().getTestDate().getMinutes() == 0
            && test.iterator().next().getTestDate().getSeconds() == 0);
    assertEquals(10, test.iterator().next().getTestLocalDate().toString().length());
    assertTrue(test.iterator().next().getTestLocalDateTime().getHour() > 0
        || test.iterator().next().getTestLocalDateTime().getMinute() > 0
        || test.iterator().next().getTestLocalDateTime().getSecond() > 0);
    assertEquals(7, test.iterator().next().getTestLong().toString().length());
  }

  @Test
  void build_list_class_domain_returns_expected_lists() throws Exception {
    Set<ListClassTestDomain> test = FakerPojo.Builder
        .fromPojo(ListClassTestDomain.class)
        .toSet()
        .build();
    DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

    assertTrue(test.iterator().next().getTestInteger().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestInteger().iterator().next() != null);
    assertTrue(test.iterator().next().getTestBoolean().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestBoolean().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestDouble().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestDouble().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestString().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestString().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestDate().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestDate().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLocalDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLocalDate().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestLocalDateTime().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestLocalDateTime().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestFloat().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestFloat().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestLong().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestLong().iterator().next() != null);
    assertTrue(test.iterator().next().getTestInstant().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestInstant().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestEnum().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestEnum().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestUuid().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestUuid().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDuration().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDuration().iterator().next() != null);
  }

  @Test
  void build_set_class_domain_returns_expected_sets() throws Exception {
    Set<SetClassTestDomain> test = FakerPojo.Builder
        .fromPojo(SetClassTestDomain.class)
        .toSet()
        .build();
    DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

    assertTrue(
        test.iterator().next().getTestInteger().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestInteger().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestBoolean().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestBoolean().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestDouble().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestDouble().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestString().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestString().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDate().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLocalDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLocalDate().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestLocalDateTime().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestLocalDateTime().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestFloat().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestFloat().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLong().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLong().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestInstant().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestInstant().iterator().next() != null);
    assertTrue(test.iterator().next().getTestEnum().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestEnum().iterator().next() != null);
    assertTrue(test.iterator().next().getTestUuid().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestUuid().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestDuration().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestDuration().iterator().next() != null);
  }

  @Test
  void build_string_set_map_class_domain_returns_expected_maps() throws Exception {
    Set<MapStringSetTestDomain> test = FakerPojo.Builder
        .fromPojo(MapStringSetTestDomain.class)
        .toSet()
        .build();
    DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

    assertTrue(test.iterator().next().getTestInteger().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestInteger().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestBoolean().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestBoolean().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDouble().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDouble().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestString().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestString().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDate().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLocalDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLocalDate().values().iterator().next().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestLocalDateTime().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestLocalDateTime().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestFloat().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestFloat().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLong().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLong().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestInstant().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestInstant().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestEnum().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestEnum().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestUuid().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestUuid().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDuration().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDuration().values().iterator().next().iterator().next() != null);
  }

  @Test
  void build_string_list_map_class_domain_returns_expected_maps() throws Exception {
    Set<MapStringListTestDomain> test = FakerPojo.Builder
        .fromPojo(MapStringListTestDomain.class)
        .toSet()
        .build();
    DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

    assertTrue(test.iterator().next().getTestInteger().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestInteger().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestBoolean().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestBoolean().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDouble().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDouble().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestString().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestString().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDate().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLocalDate().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLocalDate().values().iterator().next().iterator().next() != null);
    assertTrue(
        test.iterator().next().getTestLocalDateTime().size() == fieldAnnotation.getRecords()
            && test.iterator().next().getTestLocalDateTime().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestFloat().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestFloat().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestLong().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestLong().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestInstant().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestInstant().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestEnum().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestEnum().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestUuid().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestUuid().values().iterator().next().iterator().next() != null);
    assertTrue(test.iterator().next().getTestDuration().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDuration().values().iterator().next().iterator().next() != null);
  }

  @Test
  void build_string_domain_map_class_domain_returns_expected_map() throws Exception {
    Set<MapStringDomainTestDomain> test = FakerPojo.Builder
        .fromPojo(MapStringDomainTestDomain.class)
        .toSet()
        .build();
    DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

    assertTrue(test.iterator().next().getTestDomainMap().size() == fieldAnnotation.getRecords()
        && test.iterator().next().getTestDomainMap().values().iterator().next() != null);
  }

  @Test
  void build_class_domain_with_overrides_returns_expected_domain() throws Exception {
    String overrideTestString = "this is an overridden test string";
    Set<ClassTestDomain> test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
        .toSet()
        .withOverrides(Map.of("testString", overrideTestString))
        .build();

    assertEquals(test.iterator().next().getTestString(), overrideTestString);
  }

  @Test
  void build_class_domain_with_unique_on_key_returns_expected_unique_domain() throws Exception {
    Set<ClassTestDomain> boolOne = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
        .toSet()
        .withUniqueOnKey("testBoolean")
        .build();

    Set<ClassTestDomain> boolTwo = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
        .toSet()
        .withUniqueOnKey("testBoolean")
        .build();

    assertEquals(boolOne.iterator().next().getTestBoolean(), !boolTwo.iterator().next().getTestBoolean());
    assertThrows(UniqueValueException.class, () -> FakerPojo.Builder.fromPojo(ClassTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build());
  }

  @Test
  void build_record_domain_returns_expected_types() throws Exception {
    Set<RecordTestDomain> test = FakerPojo.Builder.fromPojo(RecordTestDomain.class)
        .toSet()
        .build();

    assertEquals(Integer.class, test.iterator().next().testInteger().getClass());
    assertEquals(Boolean.class, test.iterator().next().testBoolean().getClass());
    assertEquals(String.class, test.iterator().next().testString().getClass());
    assertEquals(Date.class, test.iterator().next().testDate().getClass());
    assertEquals(LocalDate.class, test.iterator().next().testLocalDate().getClass());
    assertEquals(LocalDateTime.class, test.iterator().next().testLocalDateTime().getClass());
    assertEquals(Float.class, test.iterator().next().testFloat().getClass());
    assertEquals(Double.class, test.iterator().next().testDouble().getClass());
    assertEquals(Long.class, test.iterator().next().testLong().getClass());
    assertEquals(Instant.class, test.iterator().next().testInstant().getClass());
    assertEquals(NanoPrefix.class, test.iterator().next().testEnum().getClass());
    assertEquals(UUID.class, test.iterator().next().testUuid().getClass());
    assertEquals(Duration.class, test.iterator().next().testDuration().getClass());
  }

  @Test
  void build_record_domain_with_overrides_returns_expected_domain() throws Exception {
    String overrideTestString = "this is an overridden test string";
    Set<RecordTestDomain> test = FakerPojo.Builder.fromPojo(RecordTestDomain.class)
        .toSet()
        .withOverrides(Map.of("testString", overrideTestString))
        .build();

    assertEquals(test.iterator().next().testString(), overrideTestString);
  }

  @Test
  void build_record_domain_with_unique_on_key_returns_expected_unique_domain() throws Exception {
    RecordTestDomain boolOne = FakerPojo.Builder.fromPojo(RecordTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build();

    RecordTestDomain boolTwo = FakerPojo.Builder.fromPojo(RecordTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build();

    assertEquals(boolOne.testBoolean(), !boolTwo.testBoolean());
    assertThrows(UniqueValueException.class, () -> FakerPojo.Builder.fromPojo(RecordTestDomain.class)
        .getFirst()
        .withUniqueOnKey("testBoolean")
        .build());
  }

  @Test
  void build_list_domain_domain_returns_expected_list() throws Exception {
    Set<ListDomainTestDomain> test = FakerPojo.Builder.fromPojo(ListDomainTestDomain.class).toSet().build();

    assertTrue(test.iterator().next().getClassTestDomainList().size() == 1
        && test.iterator().next().getClassTestDomainList().iterator().next() != null);
  }

  @Test
  void build_list_record_domain_returns_expected_list() throws Exception {
    Set<ListRecordTestDomain> test = FakerPojo.Builder.fromPojo(ListRecordTestDomain.class).toSet().build();

    assertTrue(
        test.iterator().next().getRecordTestDomainList().size() == 1
            && test.iterator().next().getRecordTestDomainList().iterator().next() != null);
  }

  @Test
  void build_list_map_domain_returns_expected_list() throws Exception {
    Set<ListMapTestDomain> test = FakerPojo.Builder.fromPojo(ListMapTestDomain.class).toSet().build();

    assertTrue(test.iterator().next().getMapStringDomainList().size() == 1
        && test.iterator().next().getMapStringDomainList().iterator().next() != null);
  }

  @Test
  void build_custom_field_annotation_class_domain_returns_expected_domain() throws Exception {
    Set<FieldAnnotationTestDomain> test = FakerPojo.Builder.fromPojo(FieldAnnotationTestDomain.class).toSet().build();

    assertEquals(10, test.iterator().next().getTestParagraph().split("\\n").length);
    assertEquals(2, test.iterator().next().getTestFullName().split(" ").length);
    assertTrue(test.iterator().next().getTestLastName().length() > 1);
    assertTrue(test.iterator().next().getTestFirstName().length() > 1);
    assertTrue(test.iterator().next().getTestUrl().contains("www."));
    assertTrue(
        test.iterator().next().getTestPhoneNumber()
            .matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"));
    assertTrue(test.iterator().next().getTestEmail().contains("@"));
  }

  @Test
  void build_custom_value_generator_returns_expected_values() throws Exception {
    Set<CustomValueGeneratorTestDomain> test = FakerPojo.Builder.fromPojo(CustomValueGeneratorTestDomain.class).toSet().build();

    assertTrue(test.iterator().next().getTestEmployeeNumber() >= 20
        && test.iterator().next().getTestEmployeeNumber() <= 1200000);
    assertTrue(Integer.parseInt(test.iterator().next().getTestEmployeeNumberString()) >= 20
        && test.iterator().next().getTestEmployeeNumber() <= 1200000);
    assertEquals(NanoPrefix.type,
        NanoIdService.getPrefixFromValue(test.iterator().next().getTestNanoId().substring(0, 3)));
    assertEquals(NanoPrefix.project,
        NanoIdService.getPrefixFromValue(test.iterator().next().getTestNanoIdList().iterator().next().substring(0, 3)));
  }
}
