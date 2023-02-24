package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListClassTestDomain {

  private List<Integer> testInteger;
  private List<Boolean> testBoolean;
  private List<Double> testDouble;
  private List<String> testString;
  private List<Date> testDate;
  private List<LocalDate> testLocalDate;
  private List<LocalDateTime> testLocalDateTime;
  private List<Float> testFloat;
  private List<Long> testLong;
  private List<Instant> testInstant;
  private List<NanoPrefix> testEnum;
  private List<UUID> testUuid;
  private List<Duration> testDuration;

  public ListClassTestDomain() {}

  public List<Integer> getTestInteger() {
    return testInteger;
  }

  public List<Boolean> getTestBoolean() {
    return testBoolean;
  }

  public List<Double> getTestDouble() {
    return testDouble;
  }

  public List<String> getTestString() {
    return testString;
  }

  public List<Date> getTestDate() {
    return testDate;
  }

  public List<LocalDate> getTestLocalDate() {
    return testLocalDate;
  }

  public List<LocalDateTime> getTestLocalDateTime() {
    return testLocalDateTime;
  }

  public List<Float> getTestFloat() {
    return testFloat;
  }

  public List<Long> getTestLong() {
    return testLong;
  }

  public List<Instant> getTestInstant() {
    return testInstant;
  }

  public List<NanoPrefix> getTestEnum() {
    return testEnum;
  }

  public List<UUID> getTestUuid() {
    return testUuid;
  }

  public List<Duration> getTestDuration() {
    return testDuration;
  }
}
