package io.github.survival1sm.fakerpojo.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.github.survival1sm.fakerpojo.enums.NanoPrefix;

public class MapStringSetTestDomain {

  public MapStringSetTestDomain(Map<String, Set<Integer>> testInteger,
                                Map<String, Set<Boolean>> testBoolean, Map<String, Set<Double>> testDouble,
                                Map<String, Set<String>> testString, Map<String, Set<Date>> testDate,
                                Map<String, Set<LocalDate>> testLocalDate,
                                Map<String, Set<LocalDateTime>> testLocalDateTime,
                                Map<String, Set<Float>> testFloat, Map<String, Set<Long>> testLong,
                                Map<String, Set<Instant>> testInstant,
                                Map<String, Set<NanoPrefix>> testEnum, Map<String, Set<UUID>> testUuid,
                                Map<String, Set<Duration>> testDuration) {
    this.testInteger = testInteger;
    this.testBoolean = testBoolean;
    this.testDouble = testDouble;
    this.testString = testString;
    this.testDate = testDate;
    this.testLocalDate = testLocalDate;
    this.testLocalDateTime = testLocalDateTime;
    this.testFloat = testFloat;
    this.testLong = testLong;
    this.testInstant = testInstant;
    this.testEnum = testEnum;
    this.testUuid = testUuid;
    this.testDuration = testDuration;
  }


  private final Map<String, Set<Integer>> testInteger;
  private final Map<String, Set<Boolean>> testBoolean;
  private final Map<String, Set<Double>> testDouble;
  private final Map<String, Set<String>> testString;
  private final Map<String, Set<Date>> testDate;
  private final Map<String, Set<LocalDate>> testLocalDate;
  private final Map<String, Set<LocalDateTime>> testLocalDateTime;
  private final Map<String, Set<Float>> testFloat;
  private final Map<String, Set<Long>> testLong;
  private final Map<String, Set<Instant>> testInstant;
  private final Map<String, Set<NanoPrefix>> testEnum;
  private final Map<String, Set<UUID>> testUuid;
  private final Map<String, Set<Duration>> testDuration;

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
