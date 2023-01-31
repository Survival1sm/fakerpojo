package io.github.survival1sm.fakerpojo.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import io.github.survival1sm.fakerpojo.enums.NanoPrefix;

public record RecordTestDomain(

    Integer testInteger,
    Boolean testBoolean,
    Double testDouble,
    String testString,
    Date testDate,
    LocalDate testLocalDate,
    LocalDateTime testLocalDateTime,
    Float testFloat,
    Long testLong,
    Instant testInstant,
    NanoPrefix testEnum,
    UUID testUuid,
    Duration testDuration
) {

}
