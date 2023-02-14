package io.github.survival1sm.fakerpojo.annotations;

import io.github.survival1sm.fakerpojo.domain.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

/**
 * Use this annotation to provide field level overrides for default value generation
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakerField {

	String type() default Type.STRING;

	int length() default 10;

	int decimals() default 2;

	int min() default 0;

	int max() default 1000000;

	int records() default 1;

	int maxDepth() default 2;

	String defaultValueExpression() default "";

	String from() default "2000-01-01 00:00:00";

	String to() default "2100-01-01 00:00:00";

	ChronoUnit chronoUnit() default ChronoUnit.MINUTES;
}

