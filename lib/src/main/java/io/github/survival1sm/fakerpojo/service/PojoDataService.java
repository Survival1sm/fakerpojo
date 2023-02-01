package io.github.survival1sm.fakerpojo.service;

import io.github.survival1sm.fakerpojo.FakerPojo.Builder;
import io.github.survival1sm.fakerpojo.annotations.FakerField;
import io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.exceptions.NoAllArgsConstructorException;
import io.github.survival1sm.fakerpojo.util.Utilities;
import net.datafaker.Faker;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.*;

import static io.github.survival1sm.fakerpojo.domain.Type.*;

public class PojoDataService {

	public static Faker faker = new Faker();

	/**
	 * Used internally to set the {@link Locale}
	 *
	 * @param faker A new {@link Faker} with the {@link Locale} set
	 */
	public static void setFaker(Faker faker) {
		PojoDataService.faker = faker;
	}

	/**
	 * Iterate through a Pojo's fields and generate fake data using the {@link FakerField} props first falling back to {@link DefaultFakerFieldProps}
	 *
	 * @param baseClass The class of data to be returned
	 * @param overrides A {@link Map} of overrides to apply during the data generation
	 * @param <T>       The class of data to be returned
	 * @return An instance of fake data with the type {@link T}
	 * @throws NoSuchFieldException      When a field cannot be retrieved from a Pojo Class
	 * @throws InstantiationException    When a {@link io.github.survival1sm.fakerpojo.domain.Type} cannot be determined or the generated data is malformed for the given class
	 * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class throws an exception
	 * @throws NoSuchMethodException     When a provided Pojo is missing their All Args Constructor
	 * @throws IllegalAccessException    When a field being set is inaccessible
	 * @throws ParseException            When the {@link FakerField#from()} or {@link FakerField#to()} are malformed
	 * @throws ClassNotFoundException    When we resolve the type argument of a List, Map, or Set incorrectly
	 */
	public static <T> T createFakePojo(Class<T> baseClass, Map<String, Object> overrides) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException, ClassNotFoundException {
		final List<Field> allFields = Utilities.getAllFieldsFromClassAndSuperclass(baseClass);
		final List<Class<?>> fieldClassList = new ArrayList<>();
		final List<Object> fieldDataList = new ArrayList<>();

		for (Field field : allFields) {
			fieldClassList.add(field.getType());

			FakerFieldProps fieldProps = Optional.ofNullable(field.getAnnotation(FakerField.class))
					.map(Utilities::copyAnnotationToFieldProps)
					.orElse(Utilities.generateDefaultFieldProps(baseClass, field));

			field.trySetAccessible();

			if (overrides.containsKey(field.getName())) {
				fieldDataList.add(overrides.get(field.getName()));
			} else {
				fieldDataList.add(createFakeField(fieldProps, baseClass, field));
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

	/**
	 * Generate fake data for an individual field on a Pojo
	 *
	 * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
	 * @param baseClass  The class of data to be returned
	 * @param field      The {@link Field} for which we need to generate fake data
	 * @param <T>        The class of data to be returned
	 * @return An instance of fake data for the field
	 * @throws NoSuchFieldException      When a field cannot be retrieved from a Pojo Class
	 * @throws InstantiationException    When a {@link io.github.survival1sm.fakerpojo.domain.Type} cannot be determined or the generated data is malformed for the given class
	 * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class throws an exception
	 * @throws NoSuchMethodException     When a provided Pojo is missing their All Args Constructor
	 * @throws IllegalAccessException    When a field being set is inaccessible
	 * @throws ParseException            When the {@link FakerField#from()} or {@link FakerField#to()} are malformed
	 * @throws ClassNotFoundException    When we resolve the type argument of a List, Map, or Set incorrectly
	 */
	public static <T> Object createFakeField(FakerFieldProps fieldProps, Class<T> baseClass, Field field) throws NoSuchFieldException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException, ParseException, ClassNotFoundException {
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

	/**
	 * Create a List of fake data for the provided {@link Field}
	 *
	 * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
	 * @param subClass   The class of data holding the list
	 * @param field      The {@link Field} for which we need to generate a fake list
	 * @param <T>        The class of data holding the list
	 * @return A list of objects for the field
	 * @throws NoSuchFieldException      When a field cannot be retrieved from a Pojo Class
	 * @throws InstantiationException    When a {@link io.github.survival1sm.fakerpojo.domain.Type} cannot be determined or the generated data is malformed for the given class
	 * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class throws an exception
	 * @throws NoSuchMethodException     When a provided Pojo is missing their All Args Constructor
	 * @throws IllegalAccessException    When a field being set is inaccessible
	 * @throws ParseException            When the {@link FakerField#from()} or {@link FakerField#to()} are malformed
	 * @throws ClassNotFoundException    When we resolve the type argument of a List, Map, or Set incorrectly
	 */
	private static <T> Object createFakeList(FakerFieldProps fieldProps, Class<T> subClass, Field field) throws ClassNotFoundException, ParseException, NoSuchFieldException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
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

	/**
	 * Create a Set of fake data for the provided {@link Field}
	 *
	 * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
	 * @param subClass   The class of data holding the set
	 * @param field      The {@link Field} for which we need to generate a fake set
	 * @param <T>        The class of data holding the set
	 * @return A set of objects for the field
	 * @throws NoSuchFieldException      When a field cannot be retrieved from a Pojo Class
	 * @throws InstantiationException    When a {@link io.github.survival1sm.fakerpojo.domain.Type} cannot be determined or the generated data is malformed for the given class
	 * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class throws an exception
	 * @throws NoSuchMethodException     When a provided Pojo is missing their All Args Constructor
	 * @throws IllegalAccessException    When a field being set is inaccessible
	 * @throws ParseException            When the {@link FakerField#from()} or {@link FakerField#to()} are malformed
	 * @throws ClassNotFoundException    When we resolve the type argument of a List, Map, or Set incorrectly
	 */
	private static <T> Object createFakeSet(FakerFieldProps fieldProps, Class<T> subClass, Field field) throws ClassNotFoundException, ParseException, NoSuchFieldException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
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

	/**
	 * Create a {@link Map} of fake data for the provided {@link Field}
	 *
	 * @param fieldProps The {@link FakerField} props or the {@link DefaultFakerFieldProps}
	 * @param field      The {@link Field} for which we need to generate a fake map
	 * @return A Map of objects for the field
	 * @throws NoSuchFieldException      When a field cannot be retrieved from a Pojo Class
	 * @throws InstantiationException    When a {@link io.github.survival1sm.fakerpojo.domain.Type} cannot be determined or the generated data is malformed for the given class
	 * @throws InvocationTargetException When an underlying constructor for a provided Pojo Class throws an exception
	 * @throws NoSuchMethodException     When a provided Pojo is missing their All Args Constructor
	 * @throws IllegalAccessException    When a field being set is inaccessible
	 * @throws ParseException            When the {@link FakerField#from()} or {@link FakerField#to()} are malformed
	 * @throws ClassNotFoundException    When we resolve the type argument of a List, Map, or Set incorrectly
	 */
	private static Map<Object, Object> createFakeMap(FakerFieldProps fieldProps, Field field) throws ParseException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
		final Map<Object, Object> newMap = new HashMap<>();

		List<Object> keyList = new ArrayList<>();
		FakerFieldProps keyFieldProps = new DefaultFakerFieldProps().withType(STRING);

		int i = 0;
		while (i < fieldProps.getRecords()) {
			Object keyValue = FieldValueService.createFieldValue(keyFieldProps);

			if (!keyList.contains(keyValue)) {
				keyList.add(keyValue);
				i++;
			}
		}

		Pair<String, java.lang.reflect.Type> fakerValueType = Utilities.determineFakerValueTypeFromField(field);

		FakerFieldProps valueFieldProps = new DefaultFakerFieldProps().withType(fakerValueType.getKey());

		i = 0;
		while (i < fieldProps.getRecords()) {
			newMap.put(keyList.get(i), createFakeField(valueFieldProps, (Class<?>) fakerValueType.getValue(), field));
			i++;
		}

		return newMap;
	}
}
