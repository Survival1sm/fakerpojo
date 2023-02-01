package io.github.survival1sm.fakerpojo.builders;

import io.github.survival1sm.fakerpojo.annotations.FakerField;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Build a list of Fake Pojo's
 *
 * @param <T> The {@link java.lang.reflect.Type} of the return data
 */
public interface ListBuilder<T> {

	/**
	 * Use the provided key to find the matching {@link java.lang.reflect.Field} by name and override it's generated value
	 *
	 * @param overrides A String key matching the field name and an Object value to use as an override
	 */
	ListBuilder<T> withOverrides(Map<String, Object> overrides);

	/**
	 * Determines the length of the returned List
	 *
	 * @param records The number of fake Pojo's to be returned to the List
	 */
	ListBuilder<T> withRecords(Integer records);

	/**
	 * Ensure the data generated is globally unique on the provided key, useful for inserting into databases.
	 *
	 * @param key A String key matching a {@link java.lang.reflect.Field} name
	 */
	ListBuilder<T> withUniqueOnKey(String key);

	/**
	 * Apply the builder arguments and build a {@link List} of fake Pojo's
	 *
	 * @return A {@link List} of fake Pojo's with type {@link T}
	 * @throws NoSuchFieldException      When a field cannot be retrieved from a Pojo Class
	 * @throws InstantiationException    When a {@link io.github.survival1sm.fakerpojo.domain.Type} cannot be determined or the generated data is malformed for the given class
	 * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class throws an exception
	 * @throws NoSuchMethodException     When a provided Pojo is missing their All Args Constructor
	 * @throws IllegalAccessException    When a field being set is inaccessible
	 * @throws ParseException            When the {@link FakerField#from()} or {@link FakerField#to()} are malformed
	 * @throws ClassNotFoundException    When we resolve the type argument of a List, Map, or Set incorrectly
	 */
	List<T> build() throws InstantiationException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, ParseException, ClassNotFoundException;

}
