package io.github.survival1sm.fakerpojo.util;

import io.github.survival1sm.fakerpojo.annotations.FakerField;
import io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.Type;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class Utilities {

	/**
	 * Convert a {@link FakerField} into {@link FakerFieldProps}
	 *
	 * @param fakerFieldAnnotation The {@link FakerField} annotated to the field
	 * @return A {@link FakerFieldProps} containing the property values of the annotation
	 */
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

	/**
	 * Parse the class to determine its {@link Type}
	 *
	 * @param baseClass The class to parse
	 * @return The {@link Type} or null
	 */
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

	/**
	 * Parse a {@link Field} to determine what the underlying {@link Type} and {@link java.lang.reflect.Type} are
	 *
	 * @param field The {@link Field} to parse
	 * @return A {@link Pair} of {@link Type} and {@link java.lang.reflect.Type}
	 */
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

	/**
	 * Create a hash of the class name, field name, and field value to determine uniqueness
	 *
	 * @param fakeData    The generated fake data field
	 * @param baseClass   The class containing the fake data field
	 * @param uniqueOnKey The field name key to be used for uniqueness
	 * @param <T>         The type of the class containing the field
	 * @return A hash of the className, fieldName, and fakeFieldValue
	 * @throws NoSuchFieldException   If the key provided does not resolve to a field within the class
	 * @throws IllegalAccessException If the field within the class in inaccessible
	 */
	public static <T> Integer getHashForUniqueField(T fakeData, Class<T> baseClass, String uniqueOnKey) throws NoSuchFieldException, IllegalAccessException {

		final Field fieldFromClass = fakeData.getClass().getDeclaredField(uniqueOnKey);
		fieldFromClass.trySetAccessible();

		return
				"%s%s%s".formatted(baseClass.getName(), uniqueOnKey, fieldFromClass.get(fakeData)).hashCode();
	}

	/**
	 * Recursively travel a class and superclass to retrieve all fields
	 *
	 * @param baseClass The starting class to travel
	 * @return A List of {@link Field} for the class and all superclasses
	 */
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

	/**
	 * Create {@link DefaultFakerFieldProps} for a field after determining its type
	 *
	 * @param baseClass The class containing the field
	 * @param field     The {@link Field} to create props for
	 * @param <T>       The type of the class containing the field
	 * @return A {@link DefaultFakerFieldProps} with a type matching the field
	 * @throws ClassNotFoundException If we made an error determining the {@link Type} for the {@link Field}
	 * @throws InstantiationException If we are unable to determine the {@link Type} from the {@link Field}
	 */
	public static <T> FakerFieldProps generateDefaultFieldProps(Class<T> baseClass, Field field) throws ClassNotFoundException, InstantiationException {
		if (field.getGenericType() instanceof ParameterizedType parameterizedType) {
			if (parameterizedType.getActualTypeArguments().length > 1) {
				return new DefaultFakerFieldProps().withType(Type.MAP);
			}

			if (parameterizedType.getActualTypeArguments().length == 1) {
				String[] paramTypePackageArray = parameterizedType.getRawType().getTypeName().split("\\.");

				String type = paramTypePackageArray[paramTypePackageArray.length - 1].toUpperCase();
				return new DefaultFakerFieldProps().withType(type);
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

			return new DefaultFakerFieldProps().withType(fakerType);
		}
		if (field.getType().isEnum()) {
			return new DefaultFakerFieldProps().withType(Type.ENUM);
		}
		String fakerType = Utilities.determineFakerValueTypeFromClass(field.getType());
		if (fakerType == null) {
			throw new InstantiationException("Unable to automatically determine faker type for field %s".formatted(
					field.getName()));
		}

		return new DefaultFakerFieldProps().withType(fakerType);
	}
}
