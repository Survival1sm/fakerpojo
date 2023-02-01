package io.github.survival1sm.fakerpojo.builders;

import java.util.List;
import java.util.Map;

/**
 * Provides access to the underlying builders for {}
 *
 * @param <T> The {@link java.lang.reflect.Type} of the return data
 */
public interface PojoBuilder<T> {

	/**
	 * Return a {@link java.util.Set} of fake Pojo's
	 */
	SetBuilder<T> toSet();

	/**
	 * Return a {@link java.util.List} of fake Pojo's
	 */
	ListBuilder<T> toList();

	/**
	 * Use a list of maps to apply overrides to each iteration of the generated data.
	 * <br><br>
	 * Useful for generating a list of Managers first, later using that same list to generate a list of Employees reporting to them, etc
	 *
	 * @param valueMapList A list of maps used to override any number of attributes per Pojo per iteration
	 */
	ListBuilder<T> usingValueMapList(List<Map<String, Object>> valueMapList);

	/**
	 * Return a single fake Pojo
	 */
	DataBuilder<T> getFirst();

	/**
	 * * Build a map with a Pojo's field as the key and the Pojo as the value
	 * * <br><br>
	 * * Useful for easily finding matching employees by using the employee id as the key
	 *
	 * @param key      The field name to retrieve the value for the key
	 * @param keyClass The class of the field being used as the key
	 * @param <K>      The class of the field being used as the key
	 */
	<K> MapBuilder<K, T> toMapOnKey(String key, Class<K> keyClass);
}
