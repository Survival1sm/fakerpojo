package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class SetClassTestDomain {

  private Set<Integer> testInteger;
  private Set<Boolean> testBoolean;
  private Set<Double> testDouble;
  private Set<String> testString;
  private Set<Date> testDate;
  private Set<LocalDate> testLocalDate;
  private Set<LocalDateTime> testLocalDateTime;
  private Set<Float> testFloat;
  private Set<Long> testLong;
  private Set<Instant> testInstant;
  private Set<NanoPrefix> testEnum;
  private Set<UUID> testUuid;
  private Set<Duration> testDuration;

  public SetClassTestDomain() {}

  public Set<Integer> getTestInteger() {
    return testInteger;
  }

  public Set<Boolean> getTestBoolean() {
    return testBoolean;
  }

  public Set<Double> getTestDouble() {
    return testDouble;
  }

  public Set<String> getTestString() {
    return testString;
  }

  public Set<Date> getTestDate() {
    return testDate;
  }

  public Set<LocalDate> getTestLocalDate() {
    return testLocalDate;
  }

  public Set<LocalDateTime> getTestLocalDateTime() {
    return testLocalDateTime;
  }

  public Set<Float> getTestFloat() {
    return testFloat;
  }

  public Set<Long> getTestLong() {
    return testLong;
  }

  public Set<Instant> getTestInstant() {
    return testInstant;
  }

  public Set<NanoPrefix> getTestEnum() {
    return testEnum;
  }

  public Set<UUID> getTestUuid() {
    return testUuid;
  }

  public Set<Duration> getTestDuration() {
    return testDuration;
  }
}
