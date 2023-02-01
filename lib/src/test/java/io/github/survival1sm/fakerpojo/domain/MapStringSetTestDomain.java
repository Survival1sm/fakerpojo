package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.enums.NanoPrefix;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MapStringSetTestDomain {

	public MapStringSetTestDomain() {
	}


	private Map<String, Set<Integer>> testInteger;
	private Map<String, Set<Boolean>> testBoolean;
	private Map<String, Set<Double>> testDouble;
	private Map<String, Set<String>> testString;
	private Map<String, Set<Date>> testDate;
	private Map<String, Set<LocalDate>> testLocalDate;
	private Map<String, Set<LocalDateTime>> testLocalDateTime;
	private Map<String, Set<Float>> testFloat;
	private Map<String, Set<Long>> testLong;
	private Map<String, Set<Instant>> testInstant;
	private Map<String, Set<NanoPrefix>> testEnum;
	private Map<String, Set<UUID>> testUuid;
	private Map<String, Set<Duration>> testDuration;

	public Map<String, Set<Integer>> getTestInteger() {
		return testInteger;
	}

	public Map<String, Set<Boolean>> getTestBoolean() {
		return testBoolean;
	}

	public Map<String, Set<Double>> getTestDouble() {
		return testDouble;
	}

	public Map<String, Set<String>> getTestString() {
		return testString;
	}

	public Map<String, Set<Date>> getTestDate() {
		return testDate;
	}

	public Map<String, Set<LocalDate>> getTestLocalDate() {
		return testLocalDate;
	}

	public Map<String, Set<LocalDateTime>> getTestLocalDateTime() {
		return testLocalDateTime;
	}

	public Map<String, Set<Float>> getTestFloat() {
		return testFloat;
	}

	public Map<String, Set<Long>> getTestLong() {
		return testLong;
	}

	public Map<String, Set<Instant>> getTestInstant() {
		return testInstant;
	}

	public Map<String, Set<NanoPrefix>> getTestEnum() {
		return testEnum;
	}

	public Map<String, Set<UUID>> getTestUuid() {
		return testUuid;
	}

	public Map<String, Set<Duration>> getTestDuration() {
		return testDuration;
	}
}
