package survival1sm.fakerpojo.service;

import io.leangen.geantyref.AnnotationFormatException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import survival1sm.fakerpojo.domain.FakerFieldProps;
import survival1sm.fakerpojo.exceptions.FieldGeneratorNotImplementedException;
import survival1sm.fakerpojo.generators.FieldValueGenerator;

public class FieldValueService {

  public static final Map<String, FieldValueGenerator> fieldValuesGeneratorMap = new HashMap<>();

  public static Object createFieldValue(FakerFieldProps fieldProps)
      throws AnnotationFormatException, ParseException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    if (fieldValuesGeneratorMap.containsKey(fieldProps.getType())) {
      return fieldValuesGeneratorMap.get(fieldProps.getType()).createFakeData(fieldProps);
    }

    throw new FieldGeneratorNotImplementedException(
        "A field generator for type %s has not been provided!".formatted(fieldProps.getType()));
  }
}
