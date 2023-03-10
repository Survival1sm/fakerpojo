package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.annotations.FakerField;
import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ExpressionTestDomain {

  private Integer testInteger;

  @FakerField(type = Type.BOOLEAN, defaultValueExpression = "true")
  private Boolean testBoolean;

  private Double testDouble;
  private String testString;
  private Date testDate;
  private LocalDate testLocalDate;
  private LocalDateTime testLocalDateTime;
  private Float testFloat;
  private Long testLong;

  @FakerField(type = Type.INSTANT, defaultValueExpression = "T(java.time.Instant).now()")
  private Instant testInstant;

  @FakerField(
      type = Type.ENUM,
      defaultValueExpression = "T(io.github.survival1sm.fakerpojo.enums.NanoPrefix).project")
  private NanoPrefix testEnum;

  private UUID testUuid;
  private Duration testDuration;

  public ExpressionTestDomain() {}

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
