package survival1sm.fakerpojo.util;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import survival1sm.fakerpojo.annotations.FakerField;
import survival1sm.fakerpojo.domain.FakerFieldProps;
import survival1sm.fakerpojo.domain.Type;

public class Utilities {

  public static FakerFieldProps copyAnnotationToFieldProps(FakerField fakerFieldAnnotation) {
    final FakerFieldProps fakerFieldProps = new FakerFieldProps();

    fakerFieldProps.setType(fakerFieldAnnotation.type());
    fakerFieldProps.setLength(fakerFieldAnnotation.length());
    fakerFieldProps.setDecimals(fakerFieldAnnotation.decimals());
    fakerFieldProps.setMin(fakerFieldAnnotation.min());
    fakerFieldProps.setMax(fakerFieldAnnotation.max());
    fakerFieldProps.setRecords(fakerFieldAnnotation.records());
    fakerFieldProps.setFrom(fakerFieldAnnotation.from());
    fakerFieldProps.setTo(fakerFieldAnnotation.to());
    fakerFieldProps.setChronoUnit(fakerFieldAnnotation.chronoUnit());

    return fakerFieldProps;
  }

  public static String determineFakerValueTypeFromClass(Class<?> baseClass) {

    if (baseClass.isEnum()) {
      return Type.ENUM;
    }

    try {
      String[] paramTypePackageArray = baseClass.getTypeName().split("\\.");

      if (Type.defaultTypeList.contains(paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase())) {
        return paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase();
      }
    } catch (IllegalArgumentException ignored) {
    }

    return null;
  }

  public static Pair<String, java.lang.reflect.Type> determineFakerValueTypeFromField(Field field) {
    ParameterizedType pt = (ParameterizedType) field.getGenericType();

    if (pt.getActualTypeArguments().length > 1
        && pt.getActualTypeArguments()[1] instanceof ParameterizedType nestedType) {
      String[] paramTypePackageArray = nestedType.getRawType().getTypeName().split("\\.");

      if (Type.defaultTypeList.contains(paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase())) {
        return new ImmutablePair<>(paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase(),
            nestedType.getRawType());
      }
    }
    if (pt.getActualTypeArguments()[0] instanceof ParameterizedType nestedType) {
      if (nestedType.getRawType().equals(Map.class)) {
        String[] paramTypePackageArray = nestedType.getActualTypeArguments()[1].getTypeName().split("\\.");

        if (Type.defaultTypeList.contains(paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase())) {
          return new ImmutablePair<>(paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase(),
              nestedType.getActualTypeArguments()[1]);
        } else {
          return new ImmutablePair<>(Type.CLASS, nestedType.getActualTypeArguments()[1]);
        }
      }
    }

    return new ImmutablePair<>(Type.CLASS, pt.getActualTypeArguments()[1]);
  }

  public static <T> Integer getHashForUniqueField(T fakeData, Class<T> baseClass, String uniqueOnKey)
      throws NoSuchFieldException, IllegalAccessException {

    final Field fieldFromClass = fakeData.getClass().getDeclaredField(uniqueOnKey);
    fieldFromClass.trySetAccessible();

    return
        "%s%s%s".formatted(baseClass.getName(), uniqueOnKey, fieldFromClass.get(fakeData)).hashCode();
  }

  public static List<Field> getAllFieldsFromClassAndSuperclass(Class<?> baseClass) {
    List<Field> allFields = new ArrayList<>();

    do {
      try {
        allFields.addAll(Arrays.asList(baseClass.getDeclaredFields()));
      } catch (Exception ignored) {
      }
    } while ((baseClass = baseClass.getSuperclass()) != null);

    return allFields;
  }

  public static <T> FakerField generateDefaultAnnotationIfNeeded(Class<T> baseClass, Field field)
      throws ClassNotFoundException, AnnotationFormatException, InstantiationException {
    if (field.getGenericType() instanceof ParameterizedType parameterizedType) {
      if (parameterizedType.getActualTypeArguments().length > 1) {
        return TypeFactory.annotation(FakerField.class, Map.of("type", Type.MAP));
      }

      if (parameterizedType.getActualTypeArguments().length == 1) {
        String[] paramTypePackageArray = parameterizedType.getRawType().getTypeName().split("\\.");

        String type = paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase();
        return TypeFactory.annotation(FakerField.class, Map.of("type", type));
      }
      String fakerType =
          Optional.ofNullable(Utilities.determineFakerValueTypeFromClass(baseClass))
              .orElse(
                  Utilities.determineFakerValueTypeFromClass(
                      Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName()))
              );
      if (fakerType == null) {
        throw new InstantiationException("Unable to automatically determine faker type for field %s".formatted(
            field.getName()));
      }

      return TypeFactory.annotation(FakerField.class, Map.of("type",
          fakerType));
    }
    if (field.getType().isEnum()) {
      return TypeFactory.annotation(FakerField.class, Map.of("type",
          Type.ENUM));
    }
    String fakerType = Utilities.determineFakerValueTypeFromClass(field.getType());
    if (fakerType == null) {
      throw new InstantiationException("Unable to automatically determine faker type for field %s".formatted(
          field.getName()));
    }

    return TypeFactory.annotation(FakerField.class, Map.of("type",
        fakerType));
  }
}
