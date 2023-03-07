package io.github.survival1sm.fakerpojo.service;

import static io.github.survival1sm.fakerpojo.domain.Type.CLASS;
import static io.github.survival1sm.fakerpojo.domain.Type.ENUM;
import static io.github.survival1sm.fakerpojo.domain.Type.LIST;
import static io.github.survival1sm.fakerpojo.domain.Type.MAP;
import static io.github.survival1sm.fakerpojo.domain.Type.SET;
import static io.github.survival1sm.fakerpojo.domain.Type.STRING;

import io.github.survival1sm.fakerpojo.FakerPojo.Builder;
import io.github.survival1sm.fakerpojo.annotations.FakerField;
import io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.util.Utilities;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.datafaker.Faker;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Service for determining the correct {@link FakerFieldProps} if no {@link FakerField} annotation
 * is present, and, calling the correct builders to populate the data.
 */
public class PojoDataService {

  private static final ExpressionParser expressionParser = new SpelExpressionParser();
  private static DefaultFakerFieldProps defaultFakerFieldProps = new DefaultFakerFieldProps();
  private static Faker faker = new Faker();

  private PojoDataService() {
    throw new UnsupportedOperationException("Data service should not be instantiated");
  }

  private static final Map<Integer, Integer> recursiveFieldMap = new LinkedHashMap<>();

  public static Map<Integer, Integer> getRecursiveFieldMap() {
    return PojoDataService.recursiveFieldMap;
  }

  public static DefaultFakerFieldProps getDefaultFakerFieldProps() {
    return PojoDataService.defaultFakerFieldProps;
  }

  public static void setDefaultFakerFieldProps(DefaultFakerFieldProps defaultFakerFieldProps) {
    PojoDataService.defaultFakerFieldProps = defaultFakerFieldProps;
  }

  public static Faker getFaker() {
    return PojoDataService.faker;
  }

  /**
   * Used internally to set the {@link Locale}.
   *
   * @param faker A new {@link Faker} with the {@link Locale} set
   */
  public static void setFaker(Faker faker) {
    PojoDataService.faker = faker;
  }

  /**
   * Iterate through a Pojo's fields and generate fake data using the {@link FakerField} props first
   * falling back to {@link DefaultFakerFieldProps}.
   *
   * @param baseClass The class of data to be returned
   * @param overrides A {@link Map} of overrides to apply during the data generation
   * @param <T> The class of data to be returned
   * @return An instance of fake data with the type {@link T}
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
  public static <T> T createFakePojo(Class<T> baseClass, Map<String, Object> overrides)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException,
          IllegalAccessException, NoSuchFieldException, ParseException, ClassNotFoundException {
    final List<Field> allFields = Utilities.getAllFieldsFromClassAndSuperclass(baseClass);
    final List<Class<?>> fieldClassList = new ArrayList<>();
    Utilities.identifyRecursiveFields(allFields, baseClass, recursiveFieldMap);
    final Map<Field, Object> fieldDataList = new LinkedHashMap<>();

    for (Field field : allFields) {
      fieldClassList.add(field.getType());

      Map.Entry<Class<?>, FakerFieldProps> fieldProps =
          Optional.ofNullable(field.getAnnotation(FakerField.class))
              .map(annotation -> Utilities.copyAnnotationToFieldProps(baseClass, annotation))
              .orElse(Utilities.generateDefaultFieldProps(baseClass, field));

      field.trySetAccessible();

      if (overrides.containsKey(field.getName())) {
        fieldDataList.put(field, overrides.get(field.getName()));
      } else if (fieldProps.getValue().getDefaultValueExpression() != null
          && !fieldProps.getValue().getDefaultValueExpression().equals("")) {
        fieldDataList.put(
            field,
            expressionParser
                .parseExpression(fieldProps.getValue().getDefaultValueExpression())
                .getValue(field.getType()));
      } else if (recursiveFieldMap.containsKey(field.hashCode())) {
        if (recursiveFieldMap.get(field.hashCode()) >= defaultFakerFieldProps.getMaxDepth()) {
          fieldDataList.put(field, null);
        } else {
          recursiveFieldMap.put(field.hashCode(), recursiveFieldMap.get(field.hashCode()) + 1);

          fieldDataList.put(
              field, createFakeField(fieldProps.getValue(), fieldProps.getKey(), field));
        }
      } else {
        fieldDataList.put(
            field, createFakeField(fieldProps.getValue(), fieldProps.getKey(), field));
      }
    }

    if (baseClass.isRecord()) {
      baseClass
          .getDeclaredConstructor(fieldClassList.toArray(new Class[allFields.size()]))
          .trySetAccessible();

      return baseClass
          .getDeclaredConstructor(fieldClassList.toArray(new Class[allFields.size()]))
          .newInstance(fieldDataList.values().toArray());
    }

    T fakeData = baseClass.getDeclaredConstructor().newInstance();

    for (Map.Entry<Field, Object> field : fieldDataList.entrySet()) {
      field.getKey().trySetAccessible();
      field.getKey().set(fakeData, field.getValue());
    }

    return fakeData;
  }

  /**
   * Generate fake data for an individual field on a Pojo.
   *
   * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
   * @param baseClass The class of data to be returned
   * @param field The {@link Field} for which we need to generate fake data
   * @param <T> The class of data to be returned
   * @return An instance of fake data for the field
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
  public static <T, E extends Enum<E>> Object createFakeField(
      FakerFieldProps fieldProps, Class<T> baseClass, Field field)
      throws NoSuchFieldException, InvocationTargetException, InstantiationException,
          NoSuchMethodException, IllegalAccessException, ParseException, ClassNotFoundException {
    switch (fieldProps.getType()) {
      case CLASS:
        return Builder.fromPojo(baseClass).getFirst().build();
      case LIST:
        return PojoDataService.createFakeList(fieldProps, baseClass, field);
      case SET:
        return PojoDataService.createFakeSet(fieldProps, baseClass, field);
      case MAP:
        return PojoDataService.createFakeMap(fieldProps, field);
      case ENUM:
        if (field != null) {
          final List<Enum<?>> enumValues =
              new ArrayList<>(EnumSet.allOf((Class<E>) field.getType()));

          return enumValues.get(faker.number().numberBetween(0, enumValues.size()));
        }
        final List<Enum<?>> enumValues = new ArrayList<>(EnumSet.allOf((Class<E>) baseClass));

        return enumValues.get(faker.number().numberBetween(0, enumValues.size()));
      default:
        return FieldValueService.createFieldValue(fieldProps);
    }
  }

  private static <T> Object createFakeList(
      FakerFieldProps fieldProps, Class<T> subClass, Field field)
      throws ClassNotFoundException, ParseException, NoSuchFieldException,
          InvocationTargetException, InstantiationException, NoSuchMethodException,
          IllegalAccessException {

    ParameterizedType nestedType = (ParameterizedType) field.getGenericType();
    Class<?> parameterizedTypeClassValue = Utilities.getClassFromParameterizedType(nestedType);

    if (nestedType.getActualTypeArguments()[0] instanceof ParameterizedType nestedMap
        && nestedMap.getRawType().equals(Map.class)) {
      List<Object> mapList = new ArrayList<>();

      int i = 0;
      while (i < fieldProps.getRecords()) {
        mapList.add(PojoDataService.createFakeMap(fieldProps, field));
        i++;
      }
      return mapList;
    }

    String fakerType =
        Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(subClass))
            .orElse(Utilities.determineFakerValueTypeFromClass(parameterizedTypeClassValue));

    if (fakerType != null) {
      return Builder.fromClass(parameterizedTypeClassValue)
          .toList()
          .withRecords(fieldProps.getRecords())
          .build();
    }

    return Builder.fromPojo(parameterizedTypeClassValue)
        .toList()
        .withRecords(fieldProps.getRecords())
        .build();
  }

  /**
   * Create a Set of fake data for the provided {@link Field}.
   *
   * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
   * @param subClass The class of data holding the set
   * @param field The {@link Field} for which we need to generate a fake set
   * @param <T> The class of data holding the set
   * @return A set of objects for the field
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
  private static <T> Object createFakeSet(
      FakerFieldProps fieldProps, Class<T> subClass, Field field)
      throws ClassNotFoundException, ParseException, NoSuchFieldException,
          InvocationTargetException, InstantiationException, NoSuchMethodException,
          IllegalAccessException {
    ParameterizedType nestedType = (ParameterizedType) field.getGenericType();
    Class<?> parameterizedTypeClassValue = Utilities.getClassFromParameterizedType(nestedType);

    if (nestedType.getActualTypeArguments()[0] instanceof ParameterizedType nestedMap
        && nestedMap.getRawType().equals(Map.class)) {
      Set<Object> mapList = new HashSet<>();

      int i = 0;
      while (i < fieldProps.getRecords()) {
        mapList.add(PojoDataService.createFakeMap(fieldProps, field));
        i++;
      }
      return mapList;
    }

    String fakerType =
        Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(subClass))
            .orElse(Utilities.determineFakerValueTypeFromClass(parameterizedTypeClassValue));
    if (fakerType != null) {
      return Builder.fromClass(parameterizedTypeClassValue)
          .toSet()
          .withRecords(fieldProps.getRecords())
          .build();
    }

    return Builder.fromPojo(parameterizedTypeClassValue)
        .toSet()
        .withRecords(fieldProps.getRecords())
        .build();
  }

  /**
   * Create a {@link Map} of fake data for the provided {@link Field}.
   *
   * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
   * @param field The {@link Field} for which we need to generate a fake map
   * @return A Map of objects for the field
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
  private static Map<Object, Object> createFakeMap(FakerFieldProps fieldProps, Field field)
      throws ParseException, NoSuchFieldException, ClassNotFoundException,
          InvocationTargetException, InstantiationException, NoSuchMethodException,
          IllegalAccessException {
    final Map<Object, Object> newMap = new HashMap<>();

    List<Object> keyList = new ArrayList<>();
    FakerFieldProps keyFieldProps = PojoDataService.getDefaultFakerFieldProps().withType(STRING);

    int i = 0;
    while (i < fieldProps.getRecords()) {
      Object keyValue = FieldValueService.createFieldValue(keyFieldProps);

      if (!keyList.contains(keyValue)) {
        keyList.add(keyValue);
        i++;
      }
    }

    Map.Entry<String, java.lang.reflect.Type> fakerValueType =
        Utilities.determineFakerValueTypeFromField(field);

    FakerFieldProps valueFieldProps =
        PojoDataService.getDefaultFakerFieldProps().withType(fakerValueType.getKey());

    i = 0;
    while (i < fieldProps.getRecords()) {
      newMap.put(
          keyList.get(i),
          createFakeField(valueFieldProps, (Class<?>) fakerValueType.getValue(), field));
      i++;
    }

    return newMap;
  }
}
