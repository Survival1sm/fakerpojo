package io.github.survival1sm.fakerpojo.builders;

import io.github.survival1sm.fakerpojo.annotations.FakerField;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

/**
 * Build a single instance of a Fake Pojo.
 *
 * @param <T> The {@link java.lang.reflect.Type} of the return data
 */
public interface DataBuilder<T> {

  /**
   * Use the provided key to find the matching {@link java.lang.reflect.Field} by name and override
   * it's generated value.
   *
   * @param overrides A String key matching the field name and an Object value to use as an override
   */
  DataBuilder<T> withOverrides(Map<String, Object> overrides);

  /**
   * Ensure the data generated is globally unique on the provided key, useful for inserting into
   * databases.
   *
   * @param key A String key matching a {@link java.lang.reflect.Field} name
   */
  DataBuilder<T> withUniqueOnKey(String key);

  /**
   * Apply the builder arguments and return a single fake Pojo.
   *
   * @return A single fake Pojo with type {@link T}
   * @throws NoSuchFieldException When a field cannot be retrieved from a Pojo Class
   * @throws InstantiationException When a {@link io.github.survival1sm.fakerpojo.domain.Type}
   *     cannot be determined or the generated data is malformed for the given class
   * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class
   *     throws an exception
   * @throws NoSuchMethodException When a provided Pojo is missing their All Args Constructor
   * @throws IllegalAccessException When a field being set is inaccessible
   * @throws ParseException When the {@link FakerField#from()} or {@link FakerField#to()} are
   *     malformed
   * @throws ClassNotFoundException When we resolve the type argument of a List, Map, or Set
   *     incorrectly
   */
  T build()
      throws NoSuchFieldException, InstantiationException, InvocationTargetException,
          NoSuchMethodException, IllegalAccessException, ParseException, ClassNotFoundException;
}
