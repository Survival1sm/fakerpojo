package survival1sm.fakerpojo.service;

import static survival1sm.fakerpojo.domain.Type.CLASS;
import static survival1sm.fakerpojo.domain.Type.ENUM;
import static survival1sm.fakerpojo.domain.Type.LIST;
import static survival1sm.fakerpojo.domain.Type.MAP;
import static survival1sm.fakerpojo.domain.Type.SET;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.datafaker.Faker;
import org.apache.commons.lang3.tuple.Pair;
import survival1sm.fakerpojo.FakerPojo.Builder;
import survival1sm.fakerpojo.annotations.FakerField;
import survival1sm.fakerpojo.domain.FakerFieldProps;
import survival1sm.fakerpojo.domain.Type;
import survival1sm.fakerpojo.exceptions.NoAllArgsConstructorException;
import survival1sm.fakerpojo.util.Utilities;

public class PojoDataService {

  private static Faker faker = new Faker();

  public static void setFaker(Faker faker) {
    PojoDataService.faker = faker;
  }

  public static <T> T createFakePojo(Class<T> baseClass, Map<String, Object> overrides)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException, ClassNotFoundException, AnnotationFormatException {
    final List<Field> allFields = Utilities.getAllFieldsFromClassAndSuperclass(baseClass);
    final List<Class<?>> fieldClassList = new ArrayList<>();
    final List<Object> fieldDataList = new ArrayList<>();

    for (Field field : allFields) {
      fieldClassList.add(field.getType());

      FakerField fakerAnnotation = Optional.ofNullable(field.getAnnotation(FakerField.class))
          .orElse(Utilities.generateDefaultAnnotationIfNeeded(baseClass, field));

      if (fakerAnnotation != null) {
        field.trySetAccessible();

        if (overrides.containsKey(field.getName())) {
          fieldDataList.add(overrides.get(field.getName()));
        } else {
          FakerFieldProps fieldProps = Utilities.copyAnnotationToFieldProps(fakerAnnotation);
          fieldDataList.add(createFakeField(fieldProps, baseClass, field));
        }
      }
    }

    try {
      baseClass.getDeclaredConstructor(fieldClassList.toArray(new Class[allFields.size()])).trySetAccessible();
    } catch (NoSuchMethodException exception) {
      throw new NoAllArgsConstructorException(
          "Pojo %s does not have an All Args Constructor!".formatted(baseClass.getName()));
    }

    return baseClass.getDeclaredConstructor(fieldClassList.toArray(new Class[allFields.size()]))
        .newInstance(fieldDataList.toArray());
  }

  public static <T> Object createFakeField(FakerFieldProps fieldProps, Class<T> baseClass, Field field)
      throws AnnotationFormatException, ParseException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    switch (fieldProps.getType()) {
      case CLASS:
        return Builder
            .fromPojo(baseClass)
            .getFirst()
            .build();
      case LIST:
        return PojoDataService.createFakeList(fieldProps, baseClass, field);
      case SET:
        return PojoDataService.createFakeSet(fieldProps, baseClass, field);
      case MAP:
        return PojoDataService.createFakeMap(fieldProps, field);
      case ENUM:
        if (field != null) {
          final List<Enum<?>> enumValues = new ArrayList<>(EnumSet.allOf((Class<Enum>) field.getType()));

          return enumValues.get(faker.number().numberBetween(0, enumValues.size()));
        }
        final List<Enum<?>> enumValues = new ArrayList<>(EnumSet.allOf((Class<Enum>) baseClass));

        return enumValues.get(faker.number().numberBetween(0, enumValues.size()));
      default:
        return FieldValueService.createFieldValue(fieldProps);
    }
  }

  private static <T> Object createFakeList(FakerFieldProps fieldProps, Class<T> subClass, Field field)
      throws AnnotationFormatException, ParseException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    if (field.getGenericType() instanceof ParameterizedType nestedType) {
      if (nestedType.getActualTypeArguments()[0] instanceof ParameterizedType nestedMap) {
        if (nestedMap.getRawType().equals(Map.class)) {
          List<Object> mapList = new ArrayList<>();

          int i = 0;
          while (i < fieldProps.getRecords()) {
            mapList.add(PojoDataService.createFakeMap(fieldProps, field));
            i++;
          }
          return mapList;
        }
      }
      if (!nestedType.getRawType().equals(List.class)) {
        if (nestedType.getActualTypeArguments().length > 1
            && nestedType.getActualTypeArguments()[1] instanceof ParameterizedType nestedGeneric) {
          String fakerType =
              Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(subClass)).orElse(
                  Utilities.determineFakerValueTypeFromClass(
                      Class.forName(nestedGeneric.getActualTypeArguments()[0].getTypeName())));
          if (fakerType != null) {
            return Builder
                .fromClass(Class.forName(nestedGeneric.getActualTypeArguments()[0].getTypeName()))
                .toList()
                .withRecords(fieldProps.getRecords())
                .build();
          }
        }
      }
      String fakerType =
          Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(subClass)).orElse(
              Utilities.determineFakerValueTypeFromClass(
                  Class.forName(nestedType.getActualTypeArguments()[0].getTypeName())));
      if (fakerType != null) {
        return Builder
            .fromClass(Class.forName(nestedType.getActualTypeArguments()[0].getTypeName()))
            .toList()
            .withRecords(fieldProps.getRecords())
            .build();
      }

      return Builder
          .fromPojo(Class.forName(nestedType.getActualTypeArguments()[0].getTypeName()))
          .toList()
          .withRecords(fieldProps.getRecords())
          .build();
    }
    return Builder
        .fromClass(subClass)
        .toList()
        .withRecords(fieldProps.getRecords())
        .build();
  }

  private static <T> Object createFakeSet(FakerFieldProps fieldProps, Class<T> subClass, Field field)
      throws AnnotationFormatException, ParseException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    if (field.getGenericType() instanceof ParameterizedType nestedType) {
      if (nestedType.getActualTypeArguments()[0] instanceof ParameterizedType nestedMap) {
        if (nestedMap.getRawType().equals(Map.class)) {
          Set<Object> mapList = new HashSet<>();

          int i = 0;
          while (i < fieldProps.getRecords()) {
            mapList.add(PojoDataService.createFakeMap(fieldProps, field));
            i++;
          }
          return mapList;
        }
      }
      if (!nestedType.getRawType().equals(Set.class)) {
        if (nestedType.getActualTypeArguments().length > 1
            && nestedType.getActualTypeArguments()[1] instanceof ParameterizedType nestedGeneric) {
          String fakerType =
              Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(subClass)).orElse(
                  Utilities.determineFakerValueTypeFromClass(
                      Class.forName(nestedGeneric.getActualTypeArguments()[0].getTypeName())));
          if (fakerType != null) {
            return Builder
                .fromClass(Class.forName(nestedGeneric.getActualTypeArguments()[0].getTypeName()))
                .toSet()
                .withRecords(fieldProps.getRecords())
                .build();
          }
        }
      }
      String fakerType =
          Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(subClass)).orElse(
              Utilities.determineFakerValueTypeFromClass(
                  Class.forName(nestedType.getActualTypeArguments()[0].getTypeName())));
      if (fakerType != null) {
        return Builder
            .fromClass(Class.forName(nestedType.getActualTypeArguments()[0].getTypeName()))
            .toSet()
            .withRecords(fieldProps.getRecords())
            .build();
      }

      return Builder
          .fromPojo(Class.forName(nestedType.getActualTypeArguments()[0].getTypeName()))
          .toSet()
          .withRecords(fieldProps.getRecords())
          .build();
    }
    return Builder
        .fromClass(subClass)
        .toSet()
        .withRecords(fieldProps.getRecords())
        .build();
  }

  private static Map<Object, Object> createFakeMap(FakerFieldProps fieldProps, Field field)
      throws NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ParseException, AnnotationFormatException {
    final Map<Object, Object> newMap = new HashMap<>();

    List<Object> keyList = new ArrayList<>();
    FakerField keyAnnotation = TypeFactory.annotation(FakerField.class, Map.of("type", Type.STRING));
    FakerFieldProps keyFieldProps = Utilities.copyAnnotationToFieldProps(keyAnnotation);

    int i = 0;
    while (i < fieldProps.getRecords()) {
      Object keyValue = FieldValueService.createFieldValue(keyFieldProps);

      if (!keyList.contains(keyValue)) {
        keyList.add(keyValue);
        i++;
      }
    }

    Pair<String, java.lang.reflect.Type> fakerValueType = Utilities.determineFakerValueTypeFromField(field);

    FakerField valueAnnotation = TypeFactory.annotation(FakerField.class, Map.of("type", fakerValueType.getKey()));
    FakerFieldProps valueFieldProps = Utilities.copyAnnotationToFieldProps(valueAnnotation);

    i = 0;
    while (i < fieldProps.getRecords()) {
      newMap.put(keyList.get(i), createFakeField(valueFieldProps, (Class<?>) fakerValueType.getValue(), field));
      i++;
    }

    return newMap;
  }
}
