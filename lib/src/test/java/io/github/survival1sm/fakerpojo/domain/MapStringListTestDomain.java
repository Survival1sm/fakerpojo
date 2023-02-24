package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MapStringListTestDomain {

  private Map<String, List<Integer>> testInteger;
  private Map<String, List<Boolean>> testBoolean;
  private Map<String, List<Double>> testDouble;
  private Map<String, List<String>> testString;
  private Map<String, List<Date>> testDate;
  private Map<String, List<LocalDate>> testLocalDate;
  private Map<String, List<LocalDateTime>> testLocalDateTime;
  private Map<String, List<Float>> testFloat;
  private Map<String, List<Long>> testLong;
  private Map<String, List<Instant>> testInstant;
  private Map<String, List<NanoPrefix>> testEnum;
  private Map<String, List<UUID>> testUuid;
  private Map<String, List<Duration>> testDuration;

  public MapStringListTestDomain() {}

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
