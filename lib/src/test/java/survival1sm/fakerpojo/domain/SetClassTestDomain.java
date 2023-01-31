package survival1sm.fakerpojo.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import survival1sm.fakerpojo.enums.NanoPrefix;

public class SetClassTestDomain {

  public SetClassTestDomain(Set<Integer> testInteger, Set<Boolean> testBoolean,
      Set<Double> testDouble, Set<String> testString, Set<Date> testDate,
      Set<LocalDate> testLocalDate, Set<LocalDateTime> testLocalDateTime, Set<Float> testFloat,
      Set<Long> testLong, Set<Instant> testInstant,
      Set<NanoPrefix> testEnum, Set<UUID> testUuid, Set<Duration> testDuration) {
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

  private final Set<Integer> testInteger;
  private final Set<Boolean> testBoolean;
  private final Set<Double> testDouble;
  private final Set<String> testString;
  private final Set<Date> testDate;
  private final Set<LocalDate> testLocalDate;
  private final Set<LocalDateTime> testLocalDateTime;
  private final Set<Float> testFloat;
  private final Set<Long> testLong;
  private final Set<Instant> testInstant;
  private final Set<NanoPrefix> testEnum;
  private final Set<UUID> testUuid;
  private final Set<Duration> testDuration;

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

//  public Set<ObjectId> getTestObjectId() {
//    return testObjectId;
//  }

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
