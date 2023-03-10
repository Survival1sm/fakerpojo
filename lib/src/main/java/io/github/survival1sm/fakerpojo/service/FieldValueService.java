package io.github.survival1sm.fakerpojo.service;

import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.Type;
import io.github.survival1sm.fakerpojo.exceptions.FieldGeneratorNotImplementedException;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Use the {@link Type} on the {@link FakerFieldProps} to generate the associated fake data from the
 * {@link FieldValueService#fieldValuesGeneratorMap}.
 */
public class FieldValueService {

  private FieldValueService() {
    throw new UnsupportedOperationException("Field Value Service should not be instantiated");
  }

  private static final Map<String, FieldValueGenerator> fieldValuesGeneratorMap = new HashMap<>();

  public static Map<String, FieldValueGenerator> getFieldValuesGeneratorMap() {
    return FieldValueService.fieldValuesGeneratorMap;
  }

  /**
   * Retrieve a {@link FieldValueGenerator} from the map and use it to generate an instance of fake
   * data.
   *
   * @param fieldProps The {@link io.github.survival1sm.fakerpojo.annotations.FakerField} props or
   *     {@link io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps}
   * @return The object created by the {@link FieldValueGenerator}
   * @throws ParseException If the {@link FakerFieldProps#getFrom()} or {@link
   *     FakerFieldProps#getTo() } are malformed
   * @throws FieldGeneratorNotImplementedException If a {@link
   *     io.github.survival1sm.fakerpojo.domain.Type} is found without a matching {@link
   *     FieldValueGenerator}
   */
  public static Object createFieldValue(FakerFieldProps fieldProps)
      throws ParseException, FieldGeneratorNotImplementedException {
    if (fieldValuesGeneratorMap.containsKey(fieldProps.getType())) {
      return fieldValuesGeneratorMap.get(fieldProps.getType()).createFakeData(fieldProps);
    }

    if (Type.OBJECT.equals(fieldProps.getType())) {
      throw new FieldGeneratorNotImplementedException(
          "Please provide a default field value generator to handle generic Object fields!");
    }

    throw new FieldGeneratorNotImplementedException(
        "A field value generator for type %s has not been provided!"
            .formatted(fieldProps.getType()));
  }
}
