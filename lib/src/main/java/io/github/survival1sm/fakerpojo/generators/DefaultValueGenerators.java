package io.github.survival1sm.fakerpojo.generators;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.UUID;
import net.datafaker.Faker;
import org.apache.commons.lang3.time.DateUtils;
import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;

public class DefaultValueGenerators {

  private static final Faker faker = new Faker();
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


  public static FieldValueGenerator stringGenerator = (FakerFieldProps fieldProps) -> faker.lorem()
      .fixedString(fieldProps.getLength());

  public static FieldValueGenerator booleanGenerator = (FakerFieldProps fieldProps) -> faker.bool().bool();

  public static FieldValueGenerator dateGenerator = (FakerFieldProps fieldProps) ->
      DateUtils.truncate(
          faker.date().between(dateFormat.parse(fieldProps.getFrom()), dateFormat.parse(fieldProps.getTo())),
          Calendar.DATE);

  public static FieldValueGenerator localDateGenerator = (FakerFieldProps fieldProps) -> faker
      .date()
      .between(dateFormat.parse(fieldProps.getFrom()), dateFormat.parse(fieldProps.getTo()))
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();

  public static FieldValueGenerator localDateTimeGenerator = (FakerFieldProps fieldProps) -> faker
      .date()
      .between(dateFormat.parse(fieldProps.getFrom()), dateFormat.parse(fieldProps.getTo()))
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();

  public static FieldValueGenerator instantGenerator = (FakerFieldProps fieldProps) -> faker
      .date()
      .between(dateFormat.parse(fieldProps.getFrom()), dateFormat.parse(fieldProps.getTo()))
      .toInstant();

  public static FieldValueGenerator durationGenerator = (FakerFieldProps fieldProps) -> Duration.of(
      fieldProps.getLength(), fieldProps.getChronoUnit());

  public static FieldValueGenerator paragraphGenerator = (FakerFieldProps fieldProps) -> String.join(
      System.lineSeparator(), faker.lorem().paragraphs(fieldProps.getLength()));

  public static FieldValueGenerator fullNameGenerator =
      (FakerFieldProps fieldProps) -> "%s %s".formatted(faker.name().firstName(), faker.name().lastName());

  public static FieldValueGenerator lastNameGenerator = (FakerFieldProps fieldProps) -> faker.name().lastName();

  public static FieldValueGenerator firstNameGenerator = (FakerFieldProps fieldProps) -> faker.name().firstName();

  public static FieldValueGenerator urlGenerator = (FakerFieldProps fieldProps) -> faker.internet().url();

  public static FieldValueGenerator phoneNumberGenerator = (FakerFieldProps fieldProps) -> faker.phoneNumber()
      .cellPhone();

  public static FieldValueGenerator emailGenerator = (FakerFieldProps fieldProps) -> faker.internet().emailAddress();

  public static FieldValueGenerator uuidGenerator = (FakerFieldProps fieldProps) -> UUID.randomUUID();

  public static FieldValueGenerator doubleGenerator = (FakerFieldProps fieldProps) -> faker.number()
      .randomDouble(fieldProps.getDecimals(), fieldProps.getMin(), fieldProps.getMax());

  public static FieldValueGenerator floatGenerator = (FakerFieldProps fieldProps) -> (float) faker.number()
      .randomDouble(fieldProps.getDecimals(), fieldProps.getMin(),
          fieldProps.getMax());

  public static FieldValueGenerator longGenerator = (FakerFieldProps fieldProps) -> faker.number()
      .randomNumber(7, true);

  public static FieldValueGenerator integerGenerator = (FakerFieldProps fieldProps) -> faker.number()
      .numberBetween(fieldProps.getMin(), fieldProps.getMax());

  public static FieldValueGenerator cityGenerator = (FakerFieldProps fieldProps) -> faker.address().city();

  public static FieldValueGenerator stateGenerator = (FakerFieldProps fieldProps) -> faker.address().state();
}
