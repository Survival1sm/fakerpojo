package io.github.survival1sm.fakerpojo.service;

import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.exceptions.FieldGeneratorNotImplementedException;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class FieldValueService {

	public static final Map<String, FieldValueGenerator> fieldValuesGeneratorMap = new HashMap<>();

	/**
	 * Retrieve a {@link FieldValueGenerator} from the map and use it to generate an instance of fake data
	 *
	 * @param fieldProps The {@link io.github.survival1sm.fakerpojo.annotations.FakerField} props or {@link io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps}
	 * @return The object created by the {@link FieldValueGenerator}
	 * @throws ParseException                        If the {@link FakerFieldProps#getFrom()} or {@link FakerFieldProps#getTo() } are malformed
	 * @throws FieldGeneratorNotImplementedException If a {@link io.github.survival1sm.fakerpojo.domain.Type} is found without a matching {@link FieldValueGenerator}
	 */
	public static Object createFieldValue(FakerFieldProps fieldProps) throws ParseException, FieldGeneratorNotImplementedException {
		if (fieldValuesGeneratorMap.containsKey(fieldProps.getType())) {
			return fieldValuesGeneratorMap.get(fieldProps.getType()).createFakeData(fieldProps);
		}

		throw new FieldGeneratorNotImplementedException(
				"A field generator for type %s has not been provided!".formatted(fieldProps.getType()));
	}
}
