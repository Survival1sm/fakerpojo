package survival1sm.fakerpojo.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import survival1sm.fakerpojo.enums.NanoPrefix;

public class ClassTestDomain {

  public ClassTestDomain(Integer testInteger, Boolean testBoolean, Double testDouble, String testString,
      Date testDate, LocalDate testLocalDate, LocalDateTime testLocalDateTime, Float testFloat, Long testLong,
      Instant testInstant, NanoPrefix testEnum, UUID testUuid, Duration testDuration) {
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

  private final Integer testInteger;
  private final Boolean testBoolean;
  private final Double testDouble;
  private final String testString;
  private final Date testDate;
  private final LocalDate testLocalDate;
  private final LocalDateTime testLocalDateTime;
  private final Float testFloat;
  private final Long testLong;
  private final Instant testInstant;
  private final NanoPrefix testEnum;
  private final UUID testUuid;
  private final Duration testDuration;

  public Integer getTestInteger() {
    return testInteger;
  }

  public Boolean getTestBoolean() {
    return testBoolean;
  }

  public Double getTestDouble() {
    return testDouble;
  }

  public String getTestString() {
    return testString;
  }

  public Date getTestDate() {
    return testDate;
  }

  public LocalDate getTestLocalDate() {
    return testLocalDate;
  }

  public LocalDateTime getTestLocalDateTime() {
    return testLocalDateTime;
  }

  public Float getTestFloat() {
    return testFloat;
  }

  public Long getTestLong() {
    return testLong;
  }

  public Instant getTestInstant() {
    return testInstant;
  }

  public NanoPrefix getTestEnum() {
    return testEnum;
  }

  public UUID getTestUuid() {
    return testUuid;
  }

  public Duration getTestDuration() {
    return testDuration;
  }

}
