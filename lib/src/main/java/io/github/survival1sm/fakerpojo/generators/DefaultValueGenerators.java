package io.github.survival1sm.fakerpojo.generators;

import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.service.PojoDataService;
import io.github.survival1sm.fakerpojo.util.Utilities;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.UUID;

/**
 * {@link FieldValueGenerator} implementations for the default {@link
 * io.github.survival1sm.fakerpojo.domain.Type}.
 */
public class DefaultValueGenerators {

  public static final FieldValueGenerator stringGenerator =
      (FakerFieldProps fieldProps) ->
          PojoDataService.getFaker().lorem().fixedString(fieldProps.getLength());
  public static final FieldValueGenerator booleanGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().bool().bool();
  public static final FieldValueGenerator durationGenerator =
      (FakerFieldProps fieldProps) ->
          Duration.of(fieldProps.getLength(), fieldProps.getChronoUnit());
  public static final FieldValueGenerator paragraphGenerator =
      (FakerFieldProps fieldProps) ->
          String.join(
              System.lineSeparator(),
              PojoDataService.getFaker().lorem().paragraphs(fieldProps.getLength()));
  public static final FieldValueGenerator fullNameGenerator =
      (FakerFieldProps fieldProps) ->
          "%s %s"
              .formatted(
                  PojoDataService.getFaker().name().firstName(),
                  PojoDataService.getFaker().name().lastName());
  public static final FieldValueGenerator lastNameGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().name().lastName();
  public static final FieldValueGenerator firstNameGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().name().firstName();
  public static final FieldValueGenerator urlGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().internet().url();
  public static final FieldValueGenerator phoneNumberGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().phoneNumber().cellPhone();
  public static final FieldValueGenerator emailGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().internet().emailAddress();
  public static final FieldValueGenerator uuidGenerator =
      (FakerFieldProps fieldProps) -> UUID.randomUUID();
  public static final FieldValueGenerator doubleGenerator =
      (FakerFieldProps fieldProps) ->
          PojoDataService.getFaker()
              .number()
              .randomDouble(fieldProps.getDecimals(), fieldProps.getMin(), fieldProps.getMax());
  public static final FieldValueGenerator floatGenerator =
      (FakerFieldProps fieldProps) ->
          (float)
              PojoDataService.getFaker()
                  .number()
                  .randomDouble(fieldProps.getDecimals(), fieldProps.getMin(), fieldProps.getMax());
  public static final FieldValueGenerator longGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().number().randomNumber(7, true);
  public static final FieldValueGenerator integerGenerator =
      (FakerFieldProps fieldProps) ->
          PojoDataService.getFaker()
              .number()
              .numberBetween(fieldProps.getMin(), fieldProps.getMax());
  public static final FieldValueGenerator cityGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().address().city();
  public static final FieldValueGenerator stateGenerator =
      (FakerFieldProps fieldProps) -> PojoDataService.getFaker().address().state();
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final FieldValueGenerator dateGenerator =
      (FakerFieldProps fieldProps) ->
          Utilities.truncateDate(
              PojoDataService.getFaker()
                  .date()
                  .between(
                      new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getFrom()),
                      new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getTo())));
  public static final FieldValueGenerator localDateGenerator =
      (FakerFieldProps fieldProps) ->
          PojoDataService.getFaker()
              .date()
              .between(
                  new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getFrom()),
                  new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getTo()))
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDate();
  public static final FieldValueGenerator localDateTimeGenerator =
      (FakerFieldProps fieldProps) ->
          PojoDataService.getFaker()
              .date()
              .between(
                  new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getFrom()),
                  new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getTo()))
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDateTime();
  public static final FieldValueGenerator instantGenerator =
      (FakerFieldProps fieldProps) ->
          PojoDataService.getFaker()
              .date()
              .between(
                  new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getFrom()),
                  new SimpleDateFormat(DATE_FORMAT).parse(fieldProps.getTo()))
              .toInstant();

  private DefaultValueGenerators() {
    throw new UnsupportedOperationException("Utility class should not be instantiated");
  }
}
