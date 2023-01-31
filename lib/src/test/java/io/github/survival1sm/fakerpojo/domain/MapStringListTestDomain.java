package io.github.survival1sm.fakerpojo.domain;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import io.github.survival1sm.fakerpojo.enums.NanoPrefix;

public class MapStringListTestDomain {

  public MapStringListTestDomain(Map<String, List<Integer>> testInteger,
      Map<String, List<Boolean>> testBoolean,
      Map<String, List<Double>> testDouble, Map<String, List<String>> testString,
      Map<String, List<Date>> testDate,
      Map<String, List<LocalDate>> testLocalDate,
      Map<String, List<LocalDateTime>> testLocalDateTime,
      Map<String, List<Float>> testFloat, Map<String, List<Long>> testLong,
      Map<String, List<Instant>> testInstant,
      Map<String, List<NanoPrefix>> testEnum, Map<String, List<UUID>> testUuid,
      Map<String, List<Duration>> testDuration) {
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

  private final Map<String, List<Integer>> testInteger;
  private final Map<String, List<Boolean>> testBoolean;
  private final Map<String, List<Double>> testDouble;
  private final Map<String, List<String>> testString;
  private final Map<String, List<Date>> testDate;
  private final Map<String, List<LocalDate>> testLocalDate;
  private final Map<String, List<LocalDateTime>> testLocalDateTime;
  private final Map<String, List<Float>> testFloat;
  private final Map<String, List<Long>> testLong;
  private final Map<String, List<Instant>> testInstant;
  private final Map<String, List<NanoPrefix>> testEnum;
  private final Map<String, List<UUID>> testUuid;
  private final Map<String, List<Duration>> testDuration;

  public Map<String, List<Integer>> getTestInteger() {
    return testInteger;
  }

  public Map<String, List<Boolean>> getTestBoolean() {
    return testBoolean;
  }

  public Map<String, List<Double>> getTestDouble() {
    return testDouble;
  }

  public Map<String, List<String>> getTestString() {
    return testString;
  }

  public Map<String, List<Date>> getTestDate() {
    return testDate;
  }

  public Map<String, List<LocalDate>> getTestLocalDate() {
    return testLocalDate;
  }

  public Map<String, List<LocalDateTime>> getTestLocalDateTime() {
    return testLocalDateTime;
  }

  public Map<String, List<Float>> getTestFloat() {
    return testFloat;
  }

  public Map<String, List<Long>> getTestLong() {
    return testLong;
  }

  public Map<String, List<Instant>> getTestInstant() {
    return testInstant;
  }

  public Map<String, List<NanoPrefix>> getTestEnum() {
    return testEnum;
  }

  public Map<String, List<UUID>> getTestUuid() {
    return testUuid;
  }

  public Map<String, List<Duration>> getTestDuration() {
    return testDuration;
  }
}
