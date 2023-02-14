package io.github.survival1sm.fakerpojo.builders;

import io.github.survival1sm.fakerpojo.FakerPojo;
import io.github.survival1sm.fakerpojo.domain.*;
import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import io.github.survival1sm.fakerpojo.service.TestValueGenerators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PojoBuilderTest {

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

		List<ClassTestDomain> test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
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

		List<ClassTestDomain> test = FakerPojo.Builder.fromPojo(ClassTestDomain.class)
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

	@Test
	void build_with_extended_class_creates_correct_data() throws Exception {
		ExtendedClassTestDomain test = FakerPojo.Builder
				.fromPojo(ExtendedClassTestDomain.class)
				.getFirst()
				.build();

		assertEquals(1, test.getExtendedFieldList().size());
		assertEquals(String.class, test.getExtendedFieldString().getClass());
		assertEquals(SetClassTestDomain.class, test.getExtendedNestedField().getClass());
	}

	@Test
	void build_with_extended_class_and_unique_key_creates_correct_data() throws Exception {
		List<ExtendedClassTestDomain> test = FakerPojo.Builder
				.fromPojo(ExtendedClassTestDomain.class)
				.toList()
				.withRecords(1)
				.withUniqueOnKey("testString")
				.build();

		assertEquals(1, test.get(0).getExtendedFieldList().size());
		assertEquals(String.class, test.get(0).getExtendedFieldString().getClass());
		assertEquals(SetClassTestDomain.class, test.get(0).getExtendedNestedField().getClass());
	}

	@Test
	void build_with_recursive_class_generates_data_to_expected_depth() throws Exception {
		RecursiveTestDomain recursiveTestDomain = FakerPojo
				.Builder
				.fromPojo(RecursiveTestDomain.class)
				.getFirst()
				.build();

		assertEquals(1, recursiveTestDomain.getRecursiveTestDomainList().size());
		assertEquals(1, recursiveTestDomain.getRecursiveTestDomainList().get(0).getRecursiveTestDomainList().size());
		assertNull(recursiveTestDomain.getRecursiveTestDomainList().get(0).getRecursiveTestDomainList().get(0).getRecursiveTestDomainList());
	}

	@Test
	void build_with_expression_generates_expected_expression_data() throws Exception {
		Instant start = Instant.now();
		ExpressionTestDomain expressionTestDomain = FakerPojo.Builder
				.fromPojo(ExpressionTestDomain.class)
				.getFirst()
				.build();

		assertTrue(expressionTestDomain.getTestBoolean());
		assertTrue(expressionTestDomain.getTestInstant().isAfter(start));
		assertEquals(NanoPrefix.project, expressionTestDomain.getTestEnum());
	}
}
