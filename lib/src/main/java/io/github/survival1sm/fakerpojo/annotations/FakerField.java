package io.github.survival1sm.fakerpojo.annotations;

import io.github.survival1sm.fakerpojo.domain.Type;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

/** Use this annotation to provide field level overrides for default value generation. */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakerField {

  /** The {@link Type} used to look up the associated {@link FieldValueGenerator}. */
  String type() default Type.STRING;

  /** The length of the generated {@link Type#STRING}. */
  int length() default 10;

  /** The number of decimals for {@link Type#FLOAT} or {@link Type#DOUBLE}. */
  int decimals() default 2;

  /**
   * The minimum number used for {@link Type#DOUBLE}, {@link Type#FLOAT} and {@link Type#INTEGER}.
   */
  int min() default 0;

  /**
   * The maximum number used for {@link Type#DOUBLE}, {@link Type#FLOAT} and {@link Type#INTEGER}.
   */
  int max() default 1000000;

  /**
   * The number of records to generate in a {@link Type#LIST}, {@link Type#SET} or {@link Type#MAP}.
   */
  int records() default 1;

  /** The maximum depth a recursive field can go. */
  int maxDepth() default 2;

  /** A Spring {@link org.springframework.expression.Expression} used to generate a value. */
  String defaultValueExpression() default "";

  /**
   * The minimum date for {@link Type#DATE}, {@link Type#LOCALDATE}, {@link Type#LOCALDATETIME} and
   * {@link Type#INSTANT}.
   */
  String from() default "2000-01-01 00:00:00";

  /**
   * The maximum date for {@link Type#DATE}, {@link Type#LOCALDATE}, {@link Type#LOCALDATETIME} and
   * {@link Type#INSTANT}.
   */
  String to() default "2100-01-01 00:00:00";

  /** The {@link ChronoUnit} to use for {@link Type#DURATION}. */
  ChronoUnit chronoUnit() default ChronoUnit.MINUTES;
}
