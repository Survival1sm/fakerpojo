package io.github.survival1sm.fakerpojo.generators;

import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;

import java.text.ParseException;

/**
 * Implement this interface and tie it to a new {@link io.github.survival1sm.fakerpojo.domain.Type} to create custom data generators
 */
@FunctionalInterface
public interface FieldValueGenerator {

	/**
	 * Create an instance of fake data
	 *
	 * @param fieldProps {@link FakerFieldProps} used to configure the data generated
	 * @return An instance of fake data
	 * @throws ParseException If the {@link FakerFieldProps#getFrom()} or {@link FakerFieldProps#getTo() } are malformed
	 */
	Object createFakeData(FakerFieldProps fieldProps) throws ParseException;
}
