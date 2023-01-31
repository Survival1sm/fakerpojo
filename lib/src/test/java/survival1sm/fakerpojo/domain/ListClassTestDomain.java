package survival1sm.fakerpojo.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import survival1sm.fakerpojo.enums.NanoPrefix;

public class ListClassTestDomain {

  public ListClassTestDomain(List<Integer> testInteger, List<Boolean> testBoolean,
      List<Double> testDouble, List<String> testString, List<Date> testDate,
      List<LocalDate> testLocalDate, List<LocalDateTime> testLocalDateTime, List<Float> testFloat,
      List<Long> testLong, List<Instant> testInstant,
      List<NanoPrefix> testEnum, List<UUID> testUuid, List<Duration> testDuration) {
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

  private final List<Integer> testInteger;
  private final List<Boolean> testBoolean;
  private final List<Double> testDouble;
  private final List<String> testString;
  private final List<Date> testDate;
  private final List<LocalDate> testLocalDate;
  private final List<LocalDateTime> testLocalDateTime;
  private final List<Float> testFloat;
  private final List<Long> testLong;
  private final List<Instant> testInstant;
  private final List<NanoPrefix> testEnum;
  private final List<UUID> testUuid;
  private final List<Duration> testDuration;

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
