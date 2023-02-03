package io.github.survival1sm.fakerpojo.builders;

import io.github.survival1sm.fakerpojo.FakerPojo;
import io.github.survival1sm.fakerpojo.domain.*;
import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import io.github.survival1sm.fakerpojo.exceptions.FieldGeneratorNotImplementedException;
import io.github.survival1sm.fakerpojo.exceptions.UniqueValueException;
import io.github.survival1sm.fakerpojo.service.NanoIdService;
import io.github.survival1sm.fakerpojo.service.TestValueGenerators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DataBuilderTest {

	@BeforeAll
	static void addTestValueGenerator() {
		FakerPojo.addFieldValuesGenerator(TestTypes.EMPLOYEE_NUMBER, TestValueGenerators.employeeNumberValueGenerator);
		FakerPojo.addFieldValuesGenerator(TestTypes.EMPLOYEE_NUMBER_STRING,
				TestValueGenerators.employeeNumberStringValueGenerator);
		FakerPojo.addFieldValuesGenerator(TestTypes.TYPE_NANOID, TestValueGenerators.typeNanoIdValueGenerator);
		FakerPojo.addFieldValuesGenerator(TestTypes.LIST_PROJECT_NANOID,
				TestValueGenerators.listProjectNanoIdValueGenerator);
	}

	@BeforeAll
	static void setFakerLocale() {
		FakerPojo.setLocale(new Locale("en", "US"));
	}

	@BeforeEach
	void setUp() {
		FakerPojo.Builder.resetUniqueOnKeyList();
	}

	@Test
	void build_class_domain_returns_expected_types() throws Exception {
		ClassTestDomain test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
				.getFirst()
				.build();

		assertEquals(Integer.class, test.getTestInteger().getClass());
		assertEquals(Boolean.class, test.getTestBoolean().getClass());
		assertEquals(String.class, test.getTestString().getClass());
		assertEquals(Date.class, test.getTestDate().getClass());
		assertEquals(LocalDate.class, test.getTestLocalDate().getClass());
		assertEquals(LocalDateTime.class, test.getTestLocalDateTime().getClass());
		assertEquals(Float.class, test.getTestFloat().getClass());
		assertEquals(Double.class, test.getTestDouble().getClass());
		assertEquals(Long.class, test.getTestLong().getClass());
		assertEquals(Instant.class, test.getTestInstant().getClass());
		assertEquals(NanoPrefix.class, test.getTestEnum().getClass());
		assertEquals(UUID.class, test.getTestUuid().getClass());
		assertEquals(Duration.class, test.getTestDuration().getClass());
	}

	@Test
	void build_class_domain_returns_expected_formats() throws Exception {
		ClassTestDomain test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
				.getFirst()
				.build();

		DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();
		assertTrue(test.getTestInteger() >= fieldAnnotation.getMin() && test.getTestInteger() <= fieldAnnotation.getMax());
		assertTrue(Stream.of(true, false).anyMatch(bool -> bool.equals(test.getTestBoolean())));
		assertEquals(10, test.getTestString().length());
		assertTrue(test.getTestDate().getHours() == 0 && test.getTestDate().getMinutes() == 0
				&& test.getTestDate().getSeconds() == 0);
		assertEquals(10, test.getTestLocalDate().toString().length());
		assertTrue(test.getTestLocalDateTime().getHour() > 0 || test.getTestLocalDateTime().getMinute() > 0
				|| test.getTestLocalDateTime().getSecond() > 0);
		assertEquals(7, test.getTestLong().toString().length());
	}

	@Test
	void build_list_class_domain_returns_expected_lists() throws Exception {
		ListClassTestDomain test = FakerPojo.Builder
				.fromPojo(ListClassTestDomain.class)
				.getFirst()
				.build();
		DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

		assertTrue(test.getTestInteger().size() == fieldAnnotation.getRecords() && test.getTestInteger().get(0) != null);
		assertTrue(test.getTestBoolean().size() == fieldAnnotation.getRecords() && test.getTestBoolean().get(0) != null);
		assertTrue(test.getTestDouble().size() == fieldAnnotation.getRecords() && test.getTestDouble().get(0) != null);
		assertTrue(test.getTestString().size() == fieldAnnotation.getRecords() && test.getTestString().get(0) != null);
		assertTrue(test.getTestDate().size() == fieldAnnotation.getRecords() && test.getTestDate().get(0) != null);
		assertTrue(test.getTestLocalDate().size() == fieldAnnotation.getRecords() && test.getTestLocalDate().get(0) != null);
		assertTrue(
				test.getTestLocalDateTime().size() == fieldAnnotation.getRecords() && test.getTestLocalDateTime().get(0) != null);
		assertTrue(test.getTestFloat().size() == fieldAnnotation.getRecords() && test.getTestFloat().get(0) != null);
		assertTrue(test.getTestLong().size() == fieldAnnotation.getRecords() && test.getTestLong().get(0) != null);
		assertTrue(test.getTestInstant().size() == fieldAnnotation.getRecords() && test.getTestInstant().get(0) != null);
		assertTrue(test.getTestEnum().size() == fieldAnnotation.getRecords() && test.getTestEnum().get(0) != null);
		assertTrue(test.getTestUuid().size() == fieldAnnotation.getRecords() && test.getTestUuid().get(0) != null);
		assertTrue(test.getTestDuration().size() == fieldAnnotation.getRecords() && test.getTestDuration().get(0) != null);
	}

	@Test
	void build_set_class_domain_returns_expected_sets() throws Exception {
		SetClassTestDomain test = FakerPojo.Builder
				.fromPojo(SetClassTestDomain.class)
				.getFirst()
				.build();
		DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

		assertTrue(
				test.getTestInteger().size() == fieldAnnotation.getRecords() && test.getTestInteger().iterator().next() != null);
		assertTrue(
				test.getTestBoolean().size() == fieldAnnotation.getRecords() && test.getTestBoolean().iterator().next() != null);
		assertTrue(
				test.getTestDouble().size() == fieldAnnotation.getRecords() && test.getTestDouble().iterator().next() != null);
		assertTrue(
				test.getTestString().size() == fieldAnnotation.getRecords() && test.getTestString().iterator().next() != null);
		assertTrue(test.getTestDate().size() == fieldAnnotation.getRecords() && test.getTestDate().iterator().next() != null);
		assertTrue(test.getTestLocalDate().size() == fieldAnnotation.getRecords()
				&& test.getTestLocalDate().iterator().next() != null);
		assertTrue(
				test.getTestLocalDateTime().size() == fieldAnnotation.getRecords()
						&& test.getTestLocalDateTime().iterator().next() != null);
		assertTrue(
				test.getTestFloat().size() == fieldAnnotation.getRecords() && test.getTestFloat().iterator().next() != null);
		assertTrue(test.getTestLong().size() == fieldAnnotation.getRecords() && test.getTestLong().iterator().next() != null);
		assertTrue(
				test.getTestInstant().size() == fieldAnnotation.getRecords() && test.getTestInstant().iterator().next() != null);
		assertTrue(test.getTestEnum().size() == fieldAnnotation.getRecords() && test.getTestEnum().iterator().next() != null);
		assertTrue(test.getTestUuid().size() == fieldAnnotation.getRecords() && test.getTestUuid().iterator().next() != null);
		assertTrue(
				test.getTestDuration().size() == fieldAnnotation.getRecords() && test.getTestDuration().iterator().next() != null);
	}

	@Test
	void build_string_set_map_class_domain_returns_expected_maps() throws Exception {
		MapStringSetTestDomain test = FakerPojo.Builder
				.fromPojo(MapStringSetTestDomain.class)
				.getFirst()
				.build();
		DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

		assertTrue(test.getTestInteger().size() == fieldAnnotation.getRecords()
				&& test.getTestInteger().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestBoolean().size() == fieldAnnotation.getRecords()
				&& test.getTestBoolean().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestDouble().size() == fieldAnnotation.getRecords()
				&& test.getTestDouble().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestString().size() == fieldAnnotation.getRecords()
				&& test.getTestString().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestDate().size() == fieldAnnotation.getRecords()
				&& test.getTestDate().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestLocalDate().size() == fieldAnnotation.getRecords()
				&& test.getTestLocalDate().values().iterator().next().iterator().next() != null);
		assertTrue(
				test.getTestLocalDateTime().size() == fieldAnnotation.getRecords()
						&& test.getTestLocalDateTime().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestFloat().size() == fieldAnnotation.getRecords()
				&& test.getTestFloat().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestLong().size() == fieldAnnotation.getRecords()
				&& test.getTestLong().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestInstant().size() == fieldAnnotation.getRecords()
				&& test.getTestInstant().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestEnum().size() == fieldAnnotation.getRecords()
				&& test.getTestEnum().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestUuid().size() == fieldAnnotation.getRecords()
				&& test.getTestUuid().values().iterator().next().iterator().next() != null);
		assertTrue(test.getTestDuration().size() == fieldAnnotation.getRecords()
				&& test.getTestDuration().values().iterator().next().iterator().next() != null);
	}

	@Test
	void build_string_list_map_class_domain_returns_expected_maps() throws Exception {
		MapStringListTestDomain test = FakerPojo.Builder
				.fromPojo(MapStringListTestDomain.class)
				.getFirst()
				.build();
		DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

		assertTrue(test.getTestInteger().size() == fieldAnnotation.getRecords()
				&& test.getTestInteger().values().iterator().next().get(0) != null);
		assertTrue(test.getTestBoolean().size() == fieldAnnotation.getRecords()
				&& test.getTestBoolean().values().iterator().next().get(0) != null);
		assertTrue(test.getTestDouble().size() == fieldAnnotation.getRecords()
				&& test.getTestDouble().values().iterator().next().get(0) != null);
		assertTrue(test.getTestString().size() == fieldAnnotation.getRecords()
				&& test.getTestString().values().iterator().next().get(0) != null);
		assertTrue(test.getTestDate().size() == fieldAnnotation.getRecords()
				&& test.getTestDate().values().iterator().next().get(0) != null);
		assertTrue(test.getTestLocalDate().size() == fieldAnnotation.getRecords()
				&& test.getTestLocalDate().values().iterator().next().get(0) != null);
		assertTrue(
				test.getTestLocalDateTime().size() == fieldAnnotation.getRecords()
						&& test.getTestLocalDateTime().values().iterator().next().get(0) != null);
		assertTrue(test.getTestFloat().size() == fieldAnnotation.getRecords()
				&& test.getTestFloat().values().iterator().next().get(0) != null);
		assertTrue(test.getTestLong().size() == fieldAnnotation.getRecords()
				&& test.getTestLong().values().iterator().next().get(0) != null);
		assertTrue(test.getTestInstant().size() == fieldAnnotation.getRecords()
				&& test.getTestInstant().values().iterator().next().get(0) != null);
		assertTrue(test.getTestEnum().size() == fieldAnnotation.getRecords()
				&& test.getTestEnum().values().iterator().next().get(0) != null);
		assertTrue(test.getTestUuid().size() == fieldAnnotation.getRecords()
				&& test.getTestUuid().values().iterator().next().get(0) != null);
		assertTrue(test.getTestDuration().size() == fieldAnnotation.getRecords()
				&& test.getTestDuration().values().iterator().next().get(0) != null);
	}

	@Test
	void build_string_domain_map_class_domain_returns_expected_map() throws Exception {
		MapStringDomainTestDomain test = FakerPojo.Builder
				.fromPojo(MapStringDomainTestDomain.class)
				.getFirst()
				.build();
		DefaultFakerFieldProps fieldAnnotation = new DefaultFakerFieldProps();

		assertTrue(test.getTestDomainMap().size() == fieldAnnotation.getRecords()
				&& test.getTestDomainMap().values().iterator().next() != null);
	}

	@Test
	void build_class_domain_with_overrides_returns_expected_domain() throws Exception {
		String overrideTestString = "this is an overridden test string";
		ClassTestDomain test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
				.getFirst()
				.withOverrides(Map.of("testString", overrideTestString))
				.build();

		assertEquals(test.getTestString(), overrideTestString);
	}

	@Test
	void build_class_domain_with_unique_on_key_returns_expected_unique_domain() throws Exception {
		ClassTestDomain boolOne = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
				.getFirst()
				.withUniqueOnKey("testBoolean")
				.build();

		ClassTestDomain boolTwo = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
				.getFirst()
				.withUniqueOnKey("testBoolean")
				.build();

		assertEquals(boolOne.getTestBoolean(), !boolTwo.getTestBoolean());
		assertThrows(UniqueValueException.class, () -> FakerPojo.Builder.fromPojo(ClassTestDomain.class)
				.getFirst()
				.withUniqueOnKey("testBoolean")
				.build());
	}

	@Test
	void build_record_domain_returns_expected_types() throws Exception {
		RecordTestDomain test = FakerPojo.Builder.fromPojo(RecordTestDomain.class)
				.getFirst()
				.build();

		assertEquals(Integer.class, test.testInteger().getClass());
		assertEquals(Boolean.class, test.testBoolean().getClass());
		assertEquals(String.class, test.testString().getClass());
		assertEquals(Date.class, test.testDate().getClass());
		assertEquals(LocalDate.class, test.testLocalDate().getClass());
		assertEquals(LocalDateTime.class, test.testLocalDateTime().getClass());
		assertEquals(Float.class, test.testFloat().getClass());
		assertEquals(Double.class, test.testDouble().getClass());
		assertEquals(Long.class, test.testLong().getClass());
		assertEquals(Instant.class, test.testInstant().getClass());
		assertEquals(NanoPrefix.class, test.testEnum().getClass());
		assertEquals(UUID.class, test.testUuid().getClass());
		assertEquals(Duration.class, test.testDuration().getClass());
	}

	@Test
	void build_record_domain_with_overrides_returns_expected_domain() throws Exception {
		String overrideTestString = "this is an overridden test string";
		RecordTestDomain test = FakerPojo.Builder.fromPojo(RecordTestDomain.class)
				.getFirst()
				.withOverrides(Map.of("testString", overrideTestString))
				.build();

		assertEquals(test.testString(), overrideTestString);
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
		ListDomainTestDomain test = FakerPojo.Builder.fromPojo(ListDomainTestDomain.class).getFirst().build();

		assertTrue(test.getClassTestDomainList().size() == 1 && test.getClassTestDomainList().get(0) != null);
	}

	@Test
	void build_list_record_domain_returns_expected_list() throws Exception {
		ListRecordTestDomain test = FakerPojo.Builder.fromPojo(ListRecordTestDomain.class).getFirst().build();

		assertTrue(test.getRecordTestDomainList().size() == 1 && test.getRecordTestDomainList().get(0) != null);
	}

	@Test
	void build_list_map_domain_returns_expected_list() throws Exception {
		ListMapTestDomain test = FakerPojo.Builder.fromPojo(ListMapTestDomain.class).getFirst().build();

		assertTrue(test.getMapStringDomainList().size() == 1 && test.getMapStringDomainList().get(0) != null);
	}

	@Test
	void build_custom_field_annotation_class_domain_returns_expected_domain() throws Exception {
		FieldAnnotationTestDomain test = FakerPojo.Builder.fromPojo(FieldAnnotationTestDomain.class).getFirst().build();

		assertEquals(10, test.getTestParagraph().split("\\n").length);
		assertEquals(2, test.getTestFullName().split(" ").length);
		assertTrue(test.getTestLastName().length() > 1);
		assertTrue(test.getTestFirstName().length() > 1);
		assertTrue(test.getTestUrl().contains("www."));
		assertTrue(test.getTestPhoneNumber()
				.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"));
		assertTrue(test.getTestEmail().contains("@"));
	}

	@Test
	void build_custom_value_generator_returns_expected_values() throws Exception {
		CustomValueGeneratorTestDomain test = FakerPojo.Builder.fromPojo(CustomValueGeneratorTestDomain.class).getFirst().build();

		assertTrue(test.getTestEmployeeNumber() >= 20 && test.getTestEmployeeNumber() <= 1200000);
		assertTrue(Integer.parseInt(test.getTestEmployeeNumberString()) >= 20 && test.getTestEmployeeNumber() <= 1200000);
		Assertions.assertEquals(NanoPrefix.type, NanoIdService.getPrefixFromValue(test.getTestNanoId().substring(0, 3)));
		Assertions.assertEquals(NanoPrefix.project, NanoIdService.getPrefixFromValue(test.getTestNanoIdList().get(0).substring(0, 3)));
	}

	@Test
	void build_unimplemented_field_class_throws_FieldGeneratorNotImplementedException() {
		assertThrows(FieldGeneratorNotImplementedException.class, () ->
				FakerPojo.Builder.fromPojo(UninimplementedFieldAnnotationTestDomain.class).getFirst().build());
	}

	@Test
	void build_pojo_with_object_field_throws_FieldGeneratorNotImplementedException() {
		assertThrows(FieldGeneratorNotImplementedException.class, () ->
				FakerPojo.Builder.fromPojo(ObjectTestDomain.class).getFirst().build());
	}

	@Test
	void build_pojo_with_object_field_and_provided_value_generator_returns_expected_value() throws Exception {

		FakerPojo.addFieldValuesGenerator(Type.OBJECT, (FakerFieldProps fieldProps) -> "stringForObject");

		ObjectTestDomain objectTestDomain = FakerPojo.Builder
				.fromPojo(ObjectTestDomain.class)
				.getFirst()
				.build();


		assertEquals(objectTestDomain.getTestObject(), "stringForObject");
	}
}